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
public class XORInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.XOR);

        // Parse opcode. See AMD64, volume 3, page 319 (page 353 of pdf).
        if (b == (byte) 0x34) {
            // Description: xor the contents of AL with an immediate 8-bit
            //     operand and store the result in AL.
            // Mnemonic:    XOR AL, imm8
            // Opcode:      34 ib

            ret.setOpCode("34");
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(new OperandRegister(RegisterType.AL));
            return ret;
        } else if (b == (byte) 0x35) {
            // Description: xor the contents of AX with an immediate 16-bit
            //     operand and store the result in AX.
            // Mnemonic:    XOR AX, imm16
            // Opcode:      35 iw

            // Description: xor the contents of EAX with an immediate 32-bit
            //     operand and store the result in EAX.
            // Mnemonic:    XOR EAX, imm32
            // Opcode:      35 id

            // Description: xor the contents of RAX with a sign-extended
            //     immediate 32-bit operand and store the result in RAX.
            // Mnemonic:    XOR RAX, imm32
            // Opcode:      35 id

            ret.setOpCode("35");
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
        } else if (b == (byte) 0x80) {
            // Description: xor the contents of an 8-bit destination register or
            //     memory operand with an 8-bit immediate value and 
            //     store the result in the destination.
            // Mnemonic:    XOR reg/mem8, imm8
            // Opcode:      80 /6 ib

            ret.setOpCode("80");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM8);
            if (rm_parser.getReg() != 6) {
                throw new RuntimeException("Except REX.Reg=6.");
            }
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0x81) {
            // Description: xor the contents of a 16-bit destination register or
            //     memory operand with a 16-bit immediate value and
            //     store the result in the destination.
            // Mnemonic:    XOR reg/mem16, imm16
            // Opcode:      81 /6 iw

            // Description: xor the contents of a 32-bit destination register or
            //     memory operand with a 32-bit immediate value and
            //     store the result in the destination.
            // Mnemonic:    XOR reg/mem32, imm32
            // Opcode:      81 /6 id

            // Description: xor the contents of a 64-bit destination register or
            //     memory operand with a sign-extended 32-bit immediate
            //     value and store the result in the destination.
            // Mnemonic:    XOR reg/mem64, imm32
            // Opcode:      81 /6 id

            ret.setOpCode("81");
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
            if (rm_parser.getReg() != 6) {
                throw new RuntimeException("Except REX.Reg=6.");
            }
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, imm_type);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0x83) {
            // Description: xor the contents of a 16-bit destination register or
            //     memory operand with a sign-extended 8-bit immediate
            //     value and store the result in the destination.
            // Mnemonic:    XOR reg/mem16, imm8
            // Opcode:      83 /6 ib

            // Description: xor the contents of a 32-bit destination register or
            //     memory operand with a sign-extended 8-bit immediate
            //     value and store the result in the destination.
            // Mnemonic:    XOR reg/mem32, imm8
            // Opcode:      83 /6 ib

            // Description: xor the contents of a 64-bit destination register or
            //     memory operand with a sign-extended 8-bit immediate
            //     value and store the result in the destination.
            // Mnemonic:    XOR reg/mem64, imm8
            // Opcode:      83 /6 ib

            ret.setOpCode("83");
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
                    throw new RuntimeException("Unknown operand size.");
            }
            if (rm_parser.getReg() != 6) {
                throw new RuntimeException("Except REX.Reg=6.");
            }
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0x30 || b == (byte) 0x31 || b == (byte) 0x32 || b == (byte) 0x33) {
            throw new RuntimeException("TODO");
        } else {
            throw new RuntimeException("Unknown opcode!");
        }
    }
}
