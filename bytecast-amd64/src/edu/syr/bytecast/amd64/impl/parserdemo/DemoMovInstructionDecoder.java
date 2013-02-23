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

package edu.syr.bytecast.amd64.impl.parserdemo;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import java.io.EOFException;

/**
 * A demo version of MovInstructionDecoder. It uses a new function with
 * {@link IByteInstructionInputStream} to decode.
 *
 * @author sheng
 */
public class DemoMovInstructionDecoder {

    public IInstruction decode(InstructionContextImpl context, IByteInstructionInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.MOV);

        // Parse opcode. See AMD64, volume 3, page 213.
        if (b == (byte) 0x88) {
            // Description: Move the contents of an 8-bit register to an 8-bit
            //     destination register or memory operand.
            // Mnemonic:    MOV reg/mem8, reg8
            // Opcode:      88 /r
            // TODO MOV reg/mem8, reg8      88 /r
        } else if (b == (byte) 0x89) {
            if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of a 16-bit register to a 16-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem16, reg16
                // Opcode:      89 /r
                // TODO MOV reg/mem16, reg16    89 /r
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of a 32-bit register to a 32-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem32, reg32
                // Opcode:      89 /r
                ret.setOpCode("89");
                // TODO We should get a rm parser rather than creating an instance.
                ModRmParserImpl rm_parser = new ModRmParserImpl(context);
                rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRegOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of a 64-bit register to a 64-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem64, reg64
                // Opcode:      89 /r
                ret.setOpCode("89");
                ModRmParserImpl rm_parser = new ModRmParserImpl(context);
                rm_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
                ret.addOperand(rm_parser.getRegOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0x8A) {
            // Description: Move the contents of an 8-bit register or memory
            //     operand to an 8-bit destination register.
            // Mnemonic:    MOV reg8, reg/mem8
            // Opcode:      8A /r
            // TODO MOV reg8, reg/mem8      8A /r
        } else if (b == (byte) 0x8B) {
            if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of an 16-bit register or memory
                //     operand to an 16-bit destination register.
                // Mnemonic:    MOV reg16, reg/mem16
                // Opcode:      8B /r
                // TODO MOV reg16, reg/mem16      8B /r
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of an 32-bit register or memory
                //     operand to an 32-bit destination register.
                // Mnemonic:    MOV reg32, reg/mem32
                // Opcode:      8B /r
                ret.setOpCode("8B");
                ModRmParserImpl rm_parser = new ModRmParserImpl(context);
                rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of an 64-bit register or memory
                //     operand to an 64-bit destination register.
                // Mnemonic:    MOV imm32
                // Opcode:      8B /r
                ret.setOpCode("8B");
                ModRmParserImpl rm_parser = new ModRmParserImpl(context);
                rm_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0x8C) {
            // TODO
        } else if (b == (byte) 0x8E) {
            // TODO
        } else if (b == (byte) 0xA0) {
            // TODO
        } else if (b == (byte) 0xA1) {
            if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("A1");
                NumberParserImpl num_parser = new NumberParserImpl();
                num_parser.parse(input, INumberParser.NumberType.SIZE32);
                ret.addOperand(num_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                return ret;
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("A1");
                NumberParserImpl num_parser = new NumberParserImpl();
                num_parser.parse(input, INumberParser.NumberType.SIZE64);
                ret.addOperand(num_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xA2) {
            // TODO
        } else if (b == (byte) 0xA3) {
            if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("A3");
                NumberParserImpl num_parser = new NumberParserImpl();
                num_parser.parse(input, NumberParserImpl.NumberType.SIZE32);
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                ret.addOperand(num_parser.getOperand());
                return ret;
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("A3");
                NumberParserImpl num_parser = new NumberParserImpl();
                num_parser.parse(input, NumberParserImpl.NumberType.SIZE64);
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                ret.addOperand(num_parser.getOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xC6) {
        } else if (b == (byte) 0xC7) {
            if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("C7");
                ModRmParserImpl rm_parser = new ModRmParserImpl(context);
                rm_parser.parse(context, input, ModRmParserImpl.RegType.NONE, ModRmParserImpl.RmType.REG_MEM32);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                NumberParserImpl num_parser = new NumberParserImpl();
                num_parser.parse(input, NumberParserImpl.NumberType.SIZE32);
                ret.addOperand(num_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == InstructionContextImpl.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("C7");
                ModRmParserImpl rm_parser = new ModRmParserImpl(context);
                rm_parser.parse(context, input, ModRmParserImpl.RegType.NONE, ModRmParserImpl.RmType.REG_MEM64);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                NumberParserImpl num_parser = new NumberParserImpl();
                num_parser.parse(input, NumberParserImpl.NumberType.SIZE32);
                ret.addOperand(num_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        }
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
