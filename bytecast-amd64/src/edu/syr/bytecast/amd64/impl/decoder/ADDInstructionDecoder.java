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
/**
 *
 * @author Chen Qian
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IImmParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.IMoffsetParser;
import edu.syr.bytecast.amd64.impl.parser.IRegImmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

public class ADDInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.ADD);

        // ADD AL,imm8
        if (b == (byte) 0x04) {
            ret.setOpCode("04");
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(new OperandRegister(RegisterType.AL));
            return ret;
        } else if (b == (byte) 0x05) { //ADD 
            ret.setOpCode("05");
            IImmParser imm_parser = ParserFactory.getImmParser();
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                imm_parser.parse(input, IImmParser.Type.IMM32);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                return ret;
            } else if (context.getAddressSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                imm_parser.parse(input, IImmParser.Type.IMM64);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                return ret;
            } else if (context.getAddressSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                imm_parser.parse(input, IImmParser.Type.IMM16);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.AX));
                return ret;
            }
            throw new RuntimeException("Unknown operand size.");
        } else if (b == (byte) 0x80) {
            //TODO ADD reg/mem8, imm8
            ret.setOpCode("80");
            throw new UnsupportedOperationException("TODO");
        } else if (b == (byte) 0x81) {
            ret.setOpCode("81");
            throw new UnsupportedOperationException("TODO");
        } else if (b == (byte) 0x83) {
            ret.setOpCode("83");

            IModRmParser imr_parser = ParserFactory.getModRmParser();
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                
                imr_parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
                
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {

                imr_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);

            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {

                imr_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);

            } else {
                throw new RuntimeException("Unknown operand size.");
            }
            if (imr_parser.getReg() != 0) {
                throw new RuntimeException("This is not an ADD instruction");
            }
            
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            // add imm first and reg second
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(imr_parser.getRmOperand());
            return ret;

        } else if (b == (byte) 0x00) {
            ret.setOpCode("00");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.REG8, IModRmParser.RmType.REG_MEM8);
            ret.addOperand(rm_parser.getRmOperand());
            ret.addOperand(rm_parser.getRegOperand());
            return ret;
        } else if (b == (byte) 0x01) {
            ret.setOpCode("01");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                rm_parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                rm_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            }
            throw new RuntimeException("Unknown operand size.");
        } else if (b == (byte) 0x02) {
            ret.setOpCode("02");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.REG8, IModRmParser.RmType.REG_MEM8);
            ret.addOperand(rm_parser.getRmOperand());
            ret.addOperand(rm_parser.getRegOperand());
            return ret;
        } else if (b == (byte) 0x03) {
            ret.setOpCode("03");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                rm_parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                rm_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            }
            throw new RuntimeException("Unknown operand size.");
        }
        throw new RuntimeException("Invalid Add Instruction Opcode");
    }
}
