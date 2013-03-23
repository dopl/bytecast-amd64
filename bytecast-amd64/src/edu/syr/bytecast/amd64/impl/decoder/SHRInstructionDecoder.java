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
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_16;
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_32;
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_64;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IImmParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class SHRInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.SHR);

        // Parse opcode. See AMD64, volume 3, page 293 (page 327 of pdf).
        if (b == (byte) 0xD0) {
            // Description: Shift an 8-bit register or memory operand right 1 bit. 
            // Mnemonic:    SHR reg/mem8, 1
            // Opcode:      D0 /5

            ret.setOpCode("D0");
            IModRmParser parser = ParserFactory.getModRmParser();
            parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM8);
            if (parser.getReg() != 5) {
                throw new RuntimeException("Except REX.Reg=5!");
            }
            ret.addOperand(parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0xD1) {

            // Description: Shift a 16-bit register or memory operand right 1 bit.
            // Mnemonic:    SHR reg/mem16, 1
            // Opcode:      D1 /5

            // Description: Shift a 32-bit register or memory operand right 1 bit.
            // Mnemonic:    SHR reg/mem32, 1
            // Opcode:      D1 /5

            // Description: Shift a 64-bit register or memory operand right 1 bit.
            // Mnemonic:    SHR reg/mem64, 1
            // Opcode:      D1 /5

            ret.setOpCode("D1");
            IModRmParser parser = ParserFactory.getModRmParser();
            switch (context.getOperandSize()) {
                case SIZE_16:
                    parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM16);
                    break;
                case SIZE_32:
                    parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM32);
                    break;
                case SIZE_64:
                    parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM64);
                    break;
                default:
                    throw new RuntimeException("Unsupported operand size.");
            }
            if (parser.getReg() != 5) {
                throw new RuntimeException("Except REX.Reg=5!");
            }
            ret.addOperand(parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0xD2) {
            // Description: Shift an 8-bit register or memory operand right the
            //     number of bits specified in the CL register.
            // Mnemonic:    SHR reg/mem8, CL
            // Opcode:      D2 /5

            ret.setOpCode("D2");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM8);
            if (rm_parser.getReg() != 5) {
                throw new RuntimeException("Except REX.Reg=5.");
            }
            ret.addOperand(new OperandRegister(RegisterType.CL));
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0xD3) {
            // Description: Shift an 16-bit register or memory operand right the
            //     number of bits specified in the CL register.
            // Mnemonic:    SHR reg/mem16, CL
            // Opcode:      D3 /5

            // Description: Shift an 32-bit register or memory operand right the
            //     number of bits specified in the CL register.
            // Mnemonic:    SHR reg/mem32, CL
            // Opcode:      D3 /5

            // Description: Shift an 64-bit register or memory operand right the
            //     number of bits specified in the CL register.
            // Mnemonic:    SHR reg/mem64, CL
            // Opcode:      D3 /5

            ret.setOpCode("D3");
            IModRmParser parser = ParserFactory.getModRmParser();
            switch (context.getOperandSize()) {
                case SIZE_16:
                    parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM16);
                    break;
                case SIZE_32:
                    parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM32);
                    break;
                case SIZE_64:
                    parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM64);
                    break;
                default:
                    throw new RuntimeException("Unsupported operand size.");
            }
            if (parser.getReg() != 5) {
                throw new RuntimeException("Except REX.Reg=5!");
            }
            ret.addOperand(new OperandRegister(RegisterType.CL));
            ret.addOperand(parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0xC0) {
            // Description: Shift an 8-bit register or memory operand right the
            //     number of bits specified by an 8-bit immediate value.
            // Mnemonic:    SHR reg/mem8, imm8
            // Opcode:      C0 /5 ib

            ret.setOpCode("C0");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM8);
            if (rm_parser.getReg() != 5) {
                throw new RuntimeException("Except REX.Reg=5!");
            }
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0xC1) {
            // Description: Shift an 16-bit register or memory operand right the
            //     number of bits specified by an 8-bit immediate value.
            // Mnemonic:    SHR reg/mem16, imm8
            // Opcode:      C1 /5 ib

            // Description: Shift an 32-bit register or memory operand right the
            //     number of bits specified by an 8-bit immediate value.
            // Mnemonic:    SHR reg/mem32, imm8
            // Opcode:      C1 /5 ib

            // Description: Shift an 64-bit register or memory operand right the
            //     number of bits specified by an 8-bit immediate value.
            // Mnemonic:    SHR reg/mem64, imm8
            // Opcode:      C1 /5 ib

            ret.setOpCode("C1");
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
                    throw new RuntimeException("Unsupported operand size.");
            }
            if (rm_parser.getReg() != 5) {
                throw new RuntimeException("Except REX.Reg=5!");
            }
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else {
            throw new RuntimeException("Unknown opcode!");
        }
    }
}
