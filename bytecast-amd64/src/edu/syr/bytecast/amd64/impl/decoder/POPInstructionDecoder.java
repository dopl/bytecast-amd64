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
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_32;
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_64;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.IRegisterInOpcodeParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

public class POPInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.POP);

        // Parse opcode. See AMD64, volume 3, page 246 (page 280 of pdf).
        if (b == (byte) 0x8F) {
            // Description: Pop the top of the stack into a 16-bit register or memory location.
            // Mnemonic:    POP reg/mem16
            // Opcode:      8F /0

            // Description: Pop the top of the stack into a 32-bit register or memory location.
            // Mnemonic:    POP reg/mem32
            // Opcode:      8F /0

            // Description: Pop the top of the stack into a 64-bit register or memory location.
            // Mnemonic:    POP reg/mem64
            // Opcode:      8F /0

            ret.setOpCode("8F");
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
            // Check "/0"
            if (rm_parser.getReg() != 0) {
                throw new RuntimeException("REX.Reg should be 0.");
            }
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b >= (byte) 0x58 && b<=(byte)0x5F) {
            // Description: Pop the top of the stack into a 16-bit register.
            // Mnemonic:    POP reg16
            // Opcode:      58 +rw

            // Description: Pop the top of the stack into a 32-bit register.
            //     (No prefix for encoding this in 64-bit mode.)
            // Mnemonic:    POP reg32
            // Opcode:      58 +rd

            // Description: Pop the top of the stack into a 64-bit register.
            // Mnemonic:    POP reg64
            // Opcode:      58 +rq

            ret.setOpCode("58");
            IRegisterInOpcodeParser reg_parser = ParserFactory.getRegisterInOpcodeParser();
            switch (context.getOperandSize()) {
                case SIZE_16:
                    reg_parser.parse(context, IRegisterInOpcodeParser.Type.RW, b - (byte) 0x58);
                    break;
                case SIZE_32:
                case SIZE_64:
                    reg_parser.parse(context, IRegisterInOpcodeParser.Type.RQ, b - (byte) 0x58);
                    break;
                default:
                    throw new RuntimeException("Unknown operand size!");
            }
            ret.addOperand(reg_parser.getRegisterOperand());
            return ret;
        } else if (b == (byte) 0x1F || b == (byte) 0x07 || b == (byte) 0x17) {
            // Description: Pop the top of the stack into the DS register.
            //     (Invalid in 64-bit mode.)
            // Mnemonic:    POP DS
            // Opcode:      1F

            // Description: Pop the top of the stack into the ES register.
            //     (Invalid in 64-bit mode.)
            // Mnemonic:    POP ES
            // Opcode:      07

            // Description: Pop the top of the stack into the SS register.
            //     (Invalid in 64-bit mode.)
            // Mnemonic:    POP SS
            // Opcode:      17

            RegisterType type;
            switch (b) {
                case (byte) 0x1F:
                    type = RegisterType.DS;
                    break;
                case (byte) 0x07:
                    type = RegisterType.ES;
                    break;
                case (byte) 0x17:
                    type = RegisterType.SS;
                    break;
                default:
                    throw new RuntimeException("The opcode did not handled.");
            }
            ret.setOpCode(String.format("%x", b));
            ret.addOperand(new OperandRegister(type));
            return ret;
        } else if (b == (byte) 0x0F) {
            b = input.read();
            if (b == (byte) 0xA1 || b == (byte) 0xA9) {
                // Description: Pop the top of the stack into the FS register.
                // Mnemonic:    POP FS
                // Opcode:      0F A1

                // Description: Pop the top of the stack into the GS register.
                // Mnemonic:    POP GS
                // Opcode:      0F A9

                ret.setOpCode(String.format("%x", b));
                if (b == (byte) 0xA1) {
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
