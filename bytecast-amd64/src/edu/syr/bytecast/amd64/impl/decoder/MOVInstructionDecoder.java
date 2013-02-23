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
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class MOVInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
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
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of a 16-bit register to a 16-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem16, reg16
                // Opcode:      89 /r
                // TODO MOV reg/mem16, reg16    89 /r
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of a 32-bit register to a 32-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem32, reg32
                // Opcode:      89 /r
                ret.setOpCode("89");
                // TODO We should get a rm parser rather than creating an instance.
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRegOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of a 64-bit register to a 64-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem64, reg64
                // Opcode:      89 /r
                ret.setOpCode("89");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
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
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of an 16-bit register or memory
                //     operand to an 16-bit destination register.
                // Mnemonic:    MOV reg16, reg/mem16
                // Opcode:      8B /r
                // TODO MOV reg16, reg/mem16      8B /r
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of an 32-bit register or memory
                //     operand to an 32-bit destination register.
                // Mnemonic:    MOV reg32, reg/mem32
                // Opcode:      8B /r
                ret.setOpCode("8B");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of an 64-bit register or memory
                //     operand to an 64-bit destination register.
                // Mnemonic:    MOV imm32
                // Opcode:      8B /r
                ret.setOpCode("8B");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
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
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("A1");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET32);
                ret.addOperand(moffset_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("A1");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET64);
                ret.addOperand(moffset_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xA2) {
            // TODO
        } else if (b == (byte) 0xA3) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("A3");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET32);
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                ret.addOperand(moffset_parser.getOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("A3");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET64);
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                ret.addOperand(moffset_parser.getOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xC6) {
        } else if (b == (byte) 0xC7) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("C7");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM32);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM32);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("C7");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM64);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM64);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        }
        // TODO
        throw new UnsupportedOperationException("TODO");

    }
    //
//    public void applyOperandSizeOverride() {
//        // For 64-bit mode, change the operand size to 16. See AMD64,
//        // volume 3, page 8.
//        m_operand_size = InstructionContext.OperandOrAddressSize.SIZE_16;
//    }
//
//    public void applyAddressSizeOverride() {
//        // For 64-bit mode, change the address size to 32. See AMD64,
//        // volume 3, page 9
//        m_address_size = InstructionContext.OperandOrAddressSize.SIZE_32;
//    }
//
//    public void applyRexPrefix(byte b) {
//        // Check REX.W
//        if ((b >> 3 & 1) == 1) {
//            // For 64-bit mode, change the operand size to 64. See AMD64,
//            // volume 3, page 8.
//            m_operand_size = InstructionContext.OperandOrAddressSize.SIZE_64;
//        }
//        // Update REX.R, REX.X, REX.B
//        m_rex_r = (b >> 2 & 1) == 1;
//        m_rex_x = (b >> 1 & 1) == 1;
//        m_rex_b = (b & 1) == 1;
//    }
}
