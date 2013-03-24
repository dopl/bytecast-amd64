/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_16;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IImmParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.IRegisterInOpcodeParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

public class PUSHInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.PUSH);

        // Parse opcode. See AMD64, volume 3, page 258 (page 292 of pdf).
        if (b == (byte) 0xFF) {
            // Description: Push the contents of a 16-bit register or memory operand onto the stack.
            // Mnemonic:    PUSH reg/mem16
            // Opcode:      FF /6
            //
            // Description: Push the contents of a 16-bit register or memory operand onto the stack.
            // Mnemonic:    PUSH reg/mem16
            // Opcode:      FF /6
            //
            // Description: Push the contents of a 16-bit register or memory operand onto the stack.
            // Mnemonic:    PUSH reg/mem16
            // Opcode:      FF /6

            ret.setOpCode("FF");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            switch (context.getOperandSize()) {
                case SIZE_16:
                    rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM16);
                    break;
                case SIZE_32:
                    rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM32);
                    break;
                case SIZE_64:
                    rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM64);
                    break;
                default:
                    throw new RuntimeException("Unknown operand size!");
            }
            // Check "/6".
            if (rm_parser.getReg() != 6) {
                throw new RuntimeException("REX.Reg should be 6!");
            }
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b >= (byte) 0x50 && b<=(byte) 0x57) {
            // Description: Push the contents of a 16-bit register onto the stack.
            // Mnemonic:    PUSH reg16
            // Opcode:      50 +rw
            //
            // Description: Push the contents of a 32-bit register onto the stack.
            // Mnemonic:    PUSH reg32
            // Opcode:      50 +rd
            //
            // Description: Push the contents of a 64-bit register onto the stack.
            // Mnemonic:    PUSH reg64
            // Opcode:      50 +rq
            //
            ret.setOpCode(String.format("%x", b));
            IRegisterInOpcodeParser reg_parser = ParserFactory.getRegisterInOpcodeParser();
            switch (context.getOperandSize()) {
                case SIZE_16:
                    reg_parser.parse(IRegisterInOpcodeParser.Type.RW, b - (byte) 0x50);
                    break;
                case SIZE_32:
                case SIZE_64:
                    reg_parser.parse(IRegisterInOpcodeParser.Type.RQ, b - (byte) 0x50);
                    break;
                default:
                    throw new RuntimeException("Unknown operand size!");
            }
            ret.addOperand(reg_parser.getRegisterOperand());
            return ret;
        } else if (b == (byte) 0x6A) {
            // Description: Push an 8-bit immediate value (sign-extended to 16, 32, or 64 bits) onto the stack.
            // Mnemonic:    PUSH imm8
            // Opcode:      6A ib
            ret.setOpCode("6A");
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            return ret;
        } else if (b == (byte) 0x68) {
            // Description: Push an 16-bit immediate value onto the stack.
            // Mnemonic:    PUSH imm16
            // Opcode:      68 ib

            // Description: Push an 32-bit immediate value onto the stack. (No prefix
            //     for encoding this in 64-bit mode.)
            // Mnemonic:    PUSH imm32
            // Opcode:      68 id

            // Description: Push a sign-extended 32-bit immediate value onto the stack.
            // Mnemonic:    PUSH imm64
            // Opcode:      68 id

            // Note that the third one is still 32-bit imm value.

            ret.setOpCode("68");
            IImmParser imm_parser = ParserFactory.getImmParser();
            switch (context.getOperandSize()) {
                case SIZE_16:
                    imm_parser.parse(input, IImmParser.Type.IMM16);
                    break;
                case SIZE_32:
                case SIZE_64:
                    imm_parser.parse(input, IImmParser.Type.IMM32);
                default:
                    throw new RuntimeException("Unknown operand size!");
            }
            ret.addOperand(imm_parser.getOperand());
            return ret;
        } else if (b == (byte) 0x0E || b == (byte) 0x16 || b == (byte) 0x1E || b == (byte) 0x06) {
            // Description: Push the CS selector onto the stack. (Invalid in 64-bit mode.)
            // Mnemonic:    PUSH CS
            // Opcode:      0E

            // Description: Push the SS selector onto the stack. (Invalid in 64-bit mode.)
            // Mnemonic:    PUSH SS
            // Opcode:      16

            // Description: Push the DS selector onto the stack. (Invalid in 64-bit mode.)
            // Mnemonic:    PUSH DS
            // Opcode:      1E

            // Description: Push the ES selector onto the stack. (Invalid in 64-bit mode.)
            // Mnemonic:    PUSH ES
            // Opcode:      06

            RegisterType type;
            switch (b) {
                case (byte) 0x0E:
                    type = RegisterType.CS;
                    break;
                case (byte) 0x16:
                    type = RegisterType.SS;
                    break;
                case (byte) 0x1E:
                    type = RegisterType.DS;
                    break;
                case (byte) 0x06:
                    type = RegisterType.ES;
                    break;
                default:
                    throw new RuntimeException("The opcode did not handled.");
            }
            ret.setOpCode(String.format("%x", b));
            ret.addOperand(new OperandRegister(type));
            return ret;
        } else if (b == (byte) 0x0F) {
            b = input.read();
            if (b == (byte) 0xA0 || b == (byte) 0xA8) {
                // Description: Push the FS selector onto the stack.
                // Mnemonic:    PUSH FS
                // Opcode:      0F A0

                // Description: Push the GS selector onto the stack.
                // Mnemonic:    PUSH GS
                // Opcode:      0F A8

                ret.setOpCode(String.format("%x", b));
                if (b == (byte) 0xA0) {
                    ret.addOperand(new OperandRegister(RegisterType.FS));
                } else {
                    ret.addOperand(new OperandRegister(RegisterType.GS));
                }
                return ret;
            } else {
                throw new RuntimeException("Unknown opcode!");
            }
        } else {
            throw new RuntimeException("Unknown opcode!");
        }
    }
}
