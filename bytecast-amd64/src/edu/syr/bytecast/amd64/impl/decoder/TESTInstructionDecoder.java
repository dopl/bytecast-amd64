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
public class TESTInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.TEST);

        // Parse opcode. See AMD64, volume 3, page 307 (page 341 of pdf).
        if (b == (byte) 0xA8) {
            // Description: and an immediate 8-bit value with the contents of the AL
            //     register and set rFLAGS to reflect the result.
            // Mnemonic:    TEST AL, imm8
            // Opcode:      A8 ib

            ret.setOpCode("A8");
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(new OperandRegister(RegisterType.AL));
            return ret;
        } else if (b == (byte) 0xA9) {
            // Description: and an immediate 16-bit value with the contents of the AX
            //     register and set rFLAGS to reflect the result.
            // Mnemonic:    TEST AX, imm16
            // Opcode:      A9 iw

            // Description: and an immediate 32-bit value with the contents of the EAX
            //     register and set rFLAGS to reflect the result.
            // Mnemonic:    TEST EAX, imm32
            // Opcode:      A9 id

            // Description: and a sign-extended immediate 32-bit value with the contents of the RAX
            //     register and set rFLAGS to reflect the result.
            // Mnemonic:    TEST RAX, imm32
            // Opcode:      A9 id

            ret.setOpCode("A9");
            IImmParser imm_parser = ParserFactory.getImmParser();
            RegisterType reg_type;
            IImmParser.Type imm_type;
            switch (context.getOperandSize()) {
                case SIZE_16:
                    reg_type = RegisterType.AX;
                    imm_type = IImmParser.Type.IMM16;
                    break;
                case SIZE_32:
                    reg_type = RegisterType.EAX;
                    imm_type = IImmParser.Type.IMM32;
                    break;
                case SIZE_64:
                    reg_type = RegisterType.RAX;
                    imm_type = IImmParser.Type.IMM32;
                    break;
                default:
                    throw new RuntimeException("Unsupported operand size.");
            }
            imm_parser.parse(input, imm_type);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(new OperandRegister(reg_type));
            return ret;
        } else if (b == (byte) 0xF6) {
            // Description: and an immediate 8-bit value with the contents of an 8-bit
            //     register or memory operand and set rFLAGS to reflect the result.
            // Mnemonic:    TEST reg/mem8, imm8
            // Opcode:      F6 /0 ib

            ret.setOpCode("F6");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM8);
            if (rm_parser.getReg() != 0) {
                throw new RuntimeException("Except REX.Reg=0.");
            }
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0xF7) {
            // Description: and an immediate 16-bit value with the contents of an 16-bit
            //     register or memory operand and set rFLAGS to reflect the result.
            // Mnemonic:    TEST reg/mem16, imm16
            // Opcode:      F7 /0 iw

            // Description: and an immediate 32-bit value with the contents of an 32-bit
            //     register or memory operand and set rFLAGS to reflect the result.
            // Mnemonic:    TEST reg/mem32, imm32
            // Opcode:      F7 /0 id

            // Description: and a sign-extended immediate 32-bit value with the contents of
            //     a 64-bit register or memory operand and set rFLAGS to reflect
            //     the result.
            // Mnemonic:    TEST reg/mem64, imm32
            // Opcode:      F7 /0 id

            ret.setOpCode("F7");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            IModRmParser.RmType rm_type;
            IImmParser.Type imm_type;
            switch (context.getOperandSize()) {
                case SIZE_16:
                    rm_type = IModRmParser.RmType.REG_MEM16;
                    imm_type = IImmParser.Type.IMM16;
                    break;
                case SIZE_32:
                    rm_type = IModRmParser.RmType.REG_MEM32;
                    imm_type = IImmParser.Type.IMM32;
                    break;
                case SIZE_64:
                    rm_type = IModRmParser.RmType.REG_MEM64;
                    imm_type = IImmParser.Type.IMM32;
                    break;
                default:
                    throw new RuntimeException("Unsupported operand size.");
            }
            rm_parser.parse(context, input, IModRmParser.RegType.NONE, rm_type);
            if (rm_parser.getReg() != 0) {
                throw new RuntimeException("Except REX.Reg=0.");
            }
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, imm_type);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0x84) {
            // Description: and the contents of an 8-bit register with the contents of an 8-bit
            //     register or memory operand and set rFLAGS to reflect the result.
            // Mnemonic:    TEST reg/mem8, reg8
            // Opcode:      84 /r

            ret.setOpCode("84");
            IModRmParser parser = ParserFactory.getModRmParser();
            parser.parse(context, input, IModRmParser.RegType.REG8, IModRmParser.RmType.REG_MEM8);
            ret.addOperand(parser.getRegOperand());
            ret.addOperand(parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0x85) {
            // Description: and the contents of a 16-bit register with the contents of a 16-bit
            //     register or memory operand and set rFLAGS to reflect the result.
            // Mnemonic:    TEST reg/mem16, reg16
            // Opcode:      85 /r

            // Description: and the contents of a 32-bit register with the contents of a 32-bit
            //     register or memory operand and set rFLAGS to reflect the result.
            // Mnemonic:    TEST reg/mem32, reg32
            // Opcode:      85 /r

            // Description: and the contents of a 64-bit register with the contents of a 64-bit
            //     register or memory operand and set rFLAGS to reflect the result.
            // Mnemonic:    TEST reg/mem64, reg64
            // Opcode:      85 /r

            ret.setOpCode("85");
            IModRmParser parser = ParserFactory.getModRmParser();
            switch (context.getOperandSize()) {
                case SIZE_16:
                    parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
                    break;
                case SIZE_32:
                    parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                    break;
                case SIZE_64:
                    parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
                    break;
                default:
                    throw new RuntimeException("Unknown operand size.");
            }
            ret.addOperand(parser.getRegOperand());
            ret.addOperand(parser.getRmOperand());
            return ret;
        } else {
            throw new RuntimeException("Unknown opcode!");
        }
    }
}
