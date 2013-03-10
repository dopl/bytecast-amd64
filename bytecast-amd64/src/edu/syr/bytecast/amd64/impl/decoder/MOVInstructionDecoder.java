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
import edu.syr.bytecast.amd64.impl.parser.IRegisterInOpcodeParser;
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
            ret.setOpCode("88");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.REG8, IModRmParser.RmType.REG_MEM8);
            ret.addOperand(rm_parser.getRegOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0x89) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of a 16-bit register to a 16-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem16, reg16
                // Opcode:      89 /r
                ret.setOpCode("89");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
                ret.addOperand(rm_parser.getRegOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of a 32-bit register to a 32-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem32, reg32
                // Opcode:      89 /r
                ret.setOpCode("89");
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
            ret.setOpCode("8A");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.REG8, IModRmParser.RmType.REG_MEM8);
            ret.addOperand(rm_parser.getRmOperand());
            ret.addOperand(rm_parser.getRegOperand());
            return ret;
        } else if (b == (byte) 0x8B) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of an 16-bit register or memory
                //     operand to an 16-bit destination register.
                // Mnemonic:    MOV reg16, reg/mem16
                // Opcode:      8B /r
                ret.setOpCode("8B");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
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
            // Description: Move the contents of a segment register to a 16-bit,
            //     32-bit, or 64-bit destination register or to a 16-bit memory
            //     operand.
            // Mnemonic:    MOV reg16/32/64/mem16, segReg
            // Opcode:      8C /r
            ret.setOpCode("8C");
            IModRmParser.RmType rm_type;
            switch (context.getOperandSize()) {
                case SIZE_16:
                    rm_type = IModRmParser.RmType.REG_MEM16;
                    break;
                case SIZE_32:
                    rm_type = IModRmParser.RmType.REG_MEM32;
                    break;
                case SIZE_64:
                    rm_type = IModRmParser.RmType.REG_MEM64;
                    break;
                default:
                    throw new RuntimeException("Unknown operand size!");
            }
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.SEG_REG, rm_type);
            ret.addOperand(rm_parser.getRegOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0x8E) {
            // Description: Move the contents of a 16-bit register or memory 
            //     operand to a segment register.
            // Mnemonic:    MOV segReg, reg/mem16
            // Opcode:      8E /r
            ret.setOpCode("8E");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.SEG_REG, IModRmParser.RmType.REG_MEM16);
            ret.addOperand(rm_parser.getRmOperand());
            ret.addOperand(rm_parser.getRegOperand());
            return ret;
        } else if (b == (byte) 0xA0) {
            // Description: Move 8-bit data at a specified memory offset to the AL register.
            // Mnemonic:    MOV AL, moffset8
            // Opcode:      A0
            ret.setOpCode("A0");
            IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
            moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET8);
            ret.addOperand(moffset_parser.getOperand());
            ret.addOperand(new OperandRegister(RegisterType.AL));
            return ret;
        } else if (b == (byte) 0xA1) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move 16-bit data at a specified memory offset to the AX register.
                // Mnemonic:    MOV AX, moffset16
                // Opcode:      A1
                ret.setOpCode("A1");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET16);
                ret.addOperand(moffset_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.AX));
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move 32-bit data at a specified memory offset to the EAX register.
                // Mnemonic:    MOV EAX, moffset32
                // Opcode:      A1
                ret.setOpCode("A1");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET32);
                ret.addOperand(moffset_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move 64-bit data at a specified memory offset to the RAX register.
                // Mnemonic:    MOV RAX, moffset64
                // Opcode:      A1
                ret.setOpCode("A1");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET64);
                ret.addOperand(moffset_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xA2) {
            // Description: Move the contents of the AL register to an 8-bit memory offset.
            // Mnemonic:    MOV moffset8, AL
            // Opcode:      A2
            ret.setOpCode("A2");
            ret.addOperand(new OperandRegister(RegisterType.AL));
            IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
            moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET8);
            ret.addOperand(moffset_parser.getOperand());
            return ret;
        } else if (b == (byte) 0xA3) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of the AX register to an 16-bit memory offset.
                // Mnemonic:    MOV moffset16, AX
                // Opcode:      A3
                ret.setOpCode("A3");
                ret.addOperand(new OperandRegister(RegisterType.AX));
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET16);
                ret.addOperand(moffset_parser.getOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of the EAX register to an 32-bit memory offset.
                // Mnemonic:    MOV moffset32, EAX
                // Opcode:      A3
                ret.setOpCode("A3");
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET32);
                ret.addOperand(moffset_parser.getOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of the RAX register to an 64-bit memory offset.
                // Mnemonic:    MOV moffset64, RAX
                // Opcode:      A3
                ret.setOpCode("A3");
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET64);
                ret.addOperand(moffset_parser.getOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b >= (byte) 0xB0 && b <= (byte) 0xB7) {
            // Description: Move an 8-bit immediate value into an 8-bit register.
            // Mnemonic:    MOV reg8, imm8
            // Opcode:      B0 +rb ib
            ret.setOpCode(String.format("%x", b));
            IRegisterInOpcodeParser reg_parser = ParserFactory.getRegisterInOpcodeParser();
            reg_parser.parse(IRegisterInOpcodeParser.Type.RB, b - (byte) 0xB0);
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(reg_parser.getRegisterOperand());
            return ret;
        } else if (b >= (byte) 0xB8 && b <= (byte) 0xBF) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move an 16-bit immediate value into an 16-bit register.
                // Mnemonic:    MOV reg16, imm16
                // Opcode:      B0 +rw iw
                ret.setOpCode(String.format("%x", b));
                IRegisterInOpcodeParser reg_parser = ParserFactory.getRegisterInOpcodeParser();
                reg_parser.parse(IRegisterInOpcodeParser.Type.RW, b - (byte) 0xB0);
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM16);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(reg_parser.getRegisterOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move an 32-bit immediate value into an 32-bit register.
                // Mnemonic:    MOV reg32, imm32
                // Opcode:      B0 +rd id
                ret.setOpCode(String.format("%x", b));
                IRegisterInOpcodeParser reg_parser = ParserFactory.getRegisterInOpcodeParser();
                reg_parser.parse(IRegisterInOpcodeParser.Type.RD, b - (byte) 0xB0);
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM32);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(reg_parser.getRegisterOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move an 64-bit immediate value into an 64-bit register.
                // Mnemonic:    MOV reg64, imm64
                // Opcode:      B0 +rq iq
                ret.setOpCode(String.format("%x", b));
                IRegisterInOpcodeParser reg_parser = ParserFactory.getRegisterInOpcodeParser();
                reg_parser.parse(IRegisterInOpcodeParser.Type.RQ, b - (byte) 0xB0);
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM64);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(reg_parser.getRegisterOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xC6) {
            // Description: Move an 8-bit immediate value to an 8-bit register or memory operand.
            // Mnemonic:    MOV reg/mem8, imm8
            // Opcode:      C6 /0 ib
            ret.setOpCode("C6");
            IModRmParser rm_parser = ParserFactory.getModRmParser();
            rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM8);
            if (rm_parser.getReg() != 0) {
                throw new RuntimeException("REX.Reg should be 0.");
            }
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(rm_parser.getRmOperand());
            return ret;
        } else if (b == (byte) 0xC7) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move an 16-bit immediate value to an 16-bit register or memory operand.
                // Mnemonic:    MOV reg/mem16, imm16
                // Opcode:      C7 /0 iw
                ret.setOpCode("C7");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM16);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM16);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move an 32-bit immediate value to an 32-bit register or memory operand.
                // Mnemonic:    MOV reg/mem32, imm32
                // Opcode:      C7 /0 id
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
                // Description: Move an 32-bit immediate value to an 64-bit register or memory operand.
                // Mnemonic:    MOV reg/mem64, imm32
                // Opcode:      C7 /0 id
                ret.setOpCode("C7");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM64);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM32);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else {
            throw new RuntimeException("Unknown opcode!");
        }
    }
}
