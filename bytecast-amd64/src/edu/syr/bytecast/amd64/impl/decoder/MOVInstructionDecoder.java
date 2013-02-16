/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 * It is a demo version of both parser and decoder for MOV instruction.
 *
 * @author sheng
 */
public class MOVInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instruction_bytes) {
        MOVInstructionDecoder.InstructionContext context = new MOVInstructionDecoder.InstructionContext();
        byte b;
        int bytes_size = instruction_bytes.size();
        int i = 0;
        for (; i < bytes_size; i++) {
            // Parse legacy prefix
            b = instruction_bytes.get(i);
            if (b == 0x66) {
                context.applyOperandSizeOverride();
                continue;
            } else if (b == 0x67) {
                context.applyAddressSizeOverride();
                continue;
            } else {
                break;
            }
        }
        if (i == bytes_size) {
            // Reach the end. Throw an exception.
            throw new RuntimeException("Imcomplete instuction bytes.");
        }

        // Parse REX prefix
        b = instruction_bytes.get(i++);
        if ((b >> 4) == 0x4) {
            context.applyRexPrefix(b);
            b = instruction_bytes.get(i++);
        }
        //...

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
            if (context.getOperandSize() == MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of a 16-bit register to a 16-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem16, reg16
                // Opcode:      89 /r
                // TODO MOV reg/mem16, reg16    89 /r
            } else if (context.getOperandSize() == MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of a 32-bit register to a 32-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem32, reg32
                // Opcode:      89 /r
                ret.setOpCode("89");
                MOVInstructionDecoder.ModRmParser rm_parser = new MOVInstructionDecoder.ModRmParser(context);
                i += rm_parser.parse(instruction_bytes, i, MOVInstructionDecoder.ModRmParser.RegType.REG32, MOVInstructionDecoder.ModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRegOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of a 64-bit register to a 64-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem64, reg64
                // Opcode:      89 /r
                ret.setOpCode("89");
                MOVInstructionDecoder.ModRmParser rm_parser = new MOVInstructionDecoder.ModRmParser(context);
                i += rm_parser.parse(instruction_bytes, i, MOVInstructionDecoder.ModRmParser.RegType.REG64, MOVInstructionDecoder.ModRmParser.RmType.REG_MEM64);
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
            if (context.getOperandSize() == MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of an 16-bit register or memory
                //     operand to an 16-bit destination register.
                // Mnemonic:    MOV reg16, reg/mem16
                // Opcode:      8B /r
                // TODO MOV reg16, reg/mem16      8B /r
            } else if (context.getOperandSize() == MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of an 32-bit register or memory
                //     operand to an 32-bit destination register.
                // Mnemonic:    MOV reg32, reg/mem32
                // Opcode:      8B /r
                ret.setOpCode("8B");
                MOVInstructionDecoder.ModRmParser rm_parser = new MOVInstructionDecoder.ModRmParser(context);
                i += rm_parser.parse(instruction_bytes, i, MOVInstructionDecoder.ModRmParser.RegType.REG32, MOVInstructionDecoder.ModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (context.getOperandSize() == MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of an 64-bit register or memory
                //     operand to an 64-bit destination register.
                // Mnemonic:    MOV imm32
                // Opcode:      8B /r
                ret.setOpCode("8B");
                MOVInstructionDecoder.ModRmParser rm_parser = new MOVInstructionDecoder.ModRmParser(context);
                i += rm_parser.parse(instruction_bytes, i, MOVInstructionDecoder.ModRmParser.RegType.REG64, MOVInstructionDecoder.ModRmParser.RmType.REG_MEM64);
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
            if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("A1");
                NumberParser num_parser = new NumberParser();
                i += num_parser.parse(instruction_bytes, i, NumberParser.NumberType.SIZE32);
                ret.addOperand(num_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                return ret;
            } else if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("A1");
                NumberParser num_parser = new NumberParser();
                i += num_parser.parse(instruction_bytes, i, NumberParser.NumberType.SIZE64);
                ret.addOperand(num_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xA2) {
            // TODO
        } else if (b == (byte) 0xA3) {
            if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("A3");
                NumberParser num_parser = new NumberParser();
                i += num_parser.parse(instruction_bytes, i, NumberParser.NumberType.SIZE32);
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                ret.addOperand(num_parser.getOperand());
                return ret;
            } else if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("A3");
                NumberParser num_parser = new NumberParser();
                i += num_parser.parse(instruction_bytes, i, NumberParser.NumberType.SIZE64);
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                ret.addOperand(num_parser.getOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xC6) {
        } else if (b == (byte) 0xC7) {
            if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("C7");
                ModRmParser rm_parser = new ModRmParser(context);
                i += rm_parser.parse(instruction_bytes, i, ModRmParser.RegType.NONE, ModRmParser.RmType.REG_MEM32);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                NumberParser num_parser = new NumberParser();
                i += num_parser.parse(instruction_bytes, i, NumberParser.NumberType.SIZE32);
                ret.addOperand(num_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == InstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("C7");
                ModRmParser rm_parser = new ModRmParser(context);
                i += rm_parser.parse(instruction_bytes, i, ModRmParser.RegType.NONE, ModRmParser.RmType.REG_MEM64);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                NumberParser num_parser = new NumberParser();
                i += num_parser.parse(instruction_bytes, i, NumberParser.NumberType.SIZE32);
                ret.addOperand(num_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        }
        // TODO
        throw new UnsupportedOperationException("TODO");

    }

    public static class InstructionContext {

        public static enum OperandOrAddressSize {

            SIZE_16, SIZE_32, SIZE_64
        }
        private MOVInstructionDecoder.InstructionContext.OperandOrAddressSize m_operand_size;
        private MOVInstructionDecoder.InstructionContext.OperandOrAddressSize m_address_size;
        private boolean m_rex_r;
        private boolean m_rex_x;
        private boolean m_rex_b;

        public InstructionContext() {
            initFor64BitMode();
        }

        private void initFor64BitMode() {
            m_operand_size = MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_32;
        }

        public void applyOperandSizeOverride() {
            // For 64-bit mode, change the operand size to 16. See AMD64,
            // volume 3, page 8.
            m_operand_size = MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_16;
        }

        public void applyAddressSizeOverride() {
            // For 64-bit mode, change the address size to 32. See AMD64,
            // volume 3, page 9
            m_address_size = MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_32;
        }

        public void applyRexPrefix(byte b) {
            // Check REX.W
            if ((b >> 3 & 1) == 1) {
                // For 64-bit mode, change the operand size to 64. See AMD64,
                // volume 3, page 8.
                m_operand_size = MOVInstructionDecoder.InstructionContext.OperandOrAddressSize.SIZE_64;
            }

            // Update REX.R, REX.X, REX.B
            m_rex_r = (b >> 2 & 1) == 1;
            m_rex_x = (b >> 1 & 1) == 1;
            m_rex_b = (b & 1) == 1;
        }

        public boolean isRexR() {
            return m_rex_r;
        }

        public boolean isRexX() {
            return m_rex_x;
        }

        public boolean isRexB() {
            return m_rex_b;
        }

        public MOVInstructionDecoder.InstructionContext.OperandOrAddressSize getOperandSize() {
            return m_operand_size;
        }

        public void setOperandSize(MOVInstructionDecoder.InstructionContext.OperandOrAddressSize operand_size) {
            this.m_operand_size = operand_size;
        }

        public MOVInstructionDecoder.InstructionContext.OperandOrAddressSize getAddressSize() {
            return m_address_size;
        }

        public void setAddressSize(MOVInstructionDecoder.InstructionContext.OperandOrAddressSize address_size) {
            this.m_address_size = address_size;
        }
    }

    public static class NumberParser {

        public static enum NumberType {

            SIZE8, SIZE16, SIZE32, SIZE64
        }
        private long m_number;
        private OperandConstant m_constant;

        public int parse(List<Byte> instruction_bytes, int start, NumberType type) {
            int size;
            switch (type) {
                case SIZE8:
                    size = 1;
                    break;
                case SIZE16:
                    size = 2;
                    break;
                case SIZE32:
                    size = 4;
                    break;
                case SIZE64:
                    size = 8;
                    break;
                default:
                    throw new RuntimeException("Unknown type.");
            }
            for (int i = 0; i < size; i++) {
                m_number = m_number << 8;
                byte b = instruction_bytes.get(i);
                m_number += b & 0xFF;
            }
            m_constant = new OperandConstant(m_number);
            return size;
        }

        public OperandConstant getOperand() {
            return m_constant;
        }
    }

    public static class ModRmParser {

        public static enum RegType {

            NONE, REG8, REG16, REG32, REG64
        }

        public static enum RmType {

            NONE, REG_MEM8, REG_MEM16, REG_MEM32, REG_MEM64
        }
        private int m_mod;
        private int m_reg;
        private int m_rm;
        private int m_extended_reg;
        private int m_extended_rm;
        private OperandRegister m_reg_operand;
        private IOperand m_rm_operand;
        private MOVInstructionDecoder.InstructionContext m_context;

        public ModRmParser(MOVInstructionDecoder.InstructionContext context) {
            this.m_context = context;
        }

        public int parse(List<Byte> instruction_bytes, int start, MOVInstructionDecoder.ModRmParser.RegType reg_type, MOVInstructionDecoder.ModRmParser.RmType rm_type) {
            byte b = instruction_bytes.get(start);
            m_mod = b >> 6 & 3;
            m_reg = b >> 3 & 7;
            m_extended_reg = m_reg + (m_context.isRexR() ? 0x8 : 0);
            m_rm = b & 7;
            m_extended_rm = m_rm + (m_context.isRexB() ? 0x8 : 0);

            // Parse reg
            m_reg_operand = getRegOperand(reg_type);

            // Parse r/m
            if (m_mod == 3) {
                m_rm_operand = getRmOperandForRegisterType(rm_type);
            } else {
                throw new UnsupportedOperationException("TODO");
            }
            return 1;
        }

        public int getReg() {
            return m_reg;
        }

        public int getMod() {
            return m_mod;
        }

        public int getRm() {
            return m_rm;
        }

        public OperandRegister getRegOperand() {
            return m_reg_operand;
        }

        public IOperand getRmOperand() {
            return m_rm_operand;
        }
        private static final RegisterType[] REG32_ARRAY = new RegisterType[]{
            RegisterType.EAX, RegisterType.ECX, RegisterType.EDX,
            RegisterType.EBX, RegisterType.ESP, RegisterType.EBP,
            RegisterType.ESI, RegisterType.EDI
        };
        private static final RegisterType[] REG64_ARRAY = new RegisterType[]{
            RegisterType.RAX, RegisterType.RCX, RegisterType.RDX,
            RegisterType.RBX, RegisterType.RSP, RegisterType.RBP,
            RegisterType.RSI, RegisterType.RDI, RegisterType.R8,
            RegisterType.R9, RegisterType.R10, RegisterType.R11,
            RegisterType.R12, RegisterType.R13, RegisterType.R14,
            RegisterType.R15};

        private OperandRegister getRegOperand(MOVInstructionDecoder.ModRmParser.RegType type) {
            switch (type) {
                case REG32:
                    if (m_context.isRexR()) {
                        throw new UnsupportedOperationException("TODO");
                    }
                    return new OperandRegister(REG32_ARRAY[m_extended_reg]);
                case REG64:
                    return new OperandRegister(REG64_ARRAY[m_extended_reg]);
                case NONE:
                    return null;
                default:
                    throw new UnsupportedOperationException("TODO");
            }
        }

        private OperandRegister getRmOperandForRegisterType(MOVInstructionDecoder.ModRmParser.RmType type) {
            switch (type) {
                case REG_MEM32:
                case REG_MEM64:
                    return new OperandRegister(REG64_ARRAY[m_extended_rm]);
                case NONE:
                    return null;
                default:
                    throw new UnsupportedOperationException("TODO");
            }
        }
    }
}
