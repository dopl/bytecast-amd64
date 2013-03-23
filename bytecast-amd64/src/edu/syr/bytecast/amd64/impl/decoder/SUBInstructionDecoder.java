/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

/**
 *
 * @author bytecast
 */
public class SUBInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.SUB);

        // ADD AL,imm8
        if (b == (byte) 0x2C) {
            ret.setOpCode("2C");
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(new OperandRegister(RegisterType.AL));
            return ret;
        } else if (b == (byte) 0x2D) { //ADD 
            ret.setOpCode("2D");
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
            if (imr_parser.getReg() != 5) {
                throw new RuntimeException("This is not an SUB instruction");
            }
            
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            // add imm first and reg second
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(imr_parser.getRmOperand());
            return ret;

        } else if (b == (byte) 0x28) {
            ret.setOpCode("28");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.REG8, IModRmParser.RmType.REG_MEM8);
            ret.addOperand(rm_parser.getRmOperand());
            ret.addOperand(rm_parser.getRegOperand());
            return ret;
        } else if (b == (byte) 0x29) {
            ret.setOpCode("29");
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
        } else if (b == (byte) 0x2A) {
            ret.setOpCode("2A");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.REG8, IModRmParser.RmType.REG_MEM8);
            ret.addOperand(rm_parser.getRmOperand());
            ret.addOperand(rm_parser.getRegOperand());
            return ret;
        } else if (b == (byte) 0x2B) {
            ret.setOpCode("2B");
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
