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
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author sheng
 */
public class MOVInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instruction_bytes) {
        InstructionContext context = new InstructionContext();
        byte b;
        int bytesSize = instruction_bytes.size();
        int i = 0;
        for (; i < bytesSize; i++) {
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
        if (i == bytesSize) {
            // Reach the end. Throw an exception.
            throw new RuntimeException("Imcomplete instuction bytes.");
        }

        // Parse REX prefix
        b = instruction_bytes.get(i++);
        if ((b >> 4) == 0x4) {
            context.applyRexPrefix(b);
            b = instruction_bytes.get(i++);
        }
        
        // Create the ret
        AMD64Instruction ret=new AMD64Instruction(InstructionType.MOV);

        // Parse opcode. See AMD64, volume 3, page 213.
        if (b == 0x88) {
            // Description: Move the contents of an 8-bit register to an 8-bit
            // destination register or memory operand.
            // Mnemonic:    MOV reg/mem8, reg8
            // Opcode:      88 /r
            // TODO MOV reg/mem8, reg8      88 /r
        } else if (b == 0x89) {
            if (context.getOperandSize() == OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of a 16-bit register to a 16-bit
                // destination register or memory operand.
                // Mnemonic:    MOV reg/mem16, reg16
                // Opcode:      89 /r
                // TODO MOV reg/mem16, reg16    89 /r
            } else if (context.getOperandSize() == OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of a 32-bit register to a 32-bit
                // destination register or memory operand.
                // Mnemonic:    MOV reg/mem32, reg32
                // Opcode:      89 /r
                ret.setOpCode("89");
                i+=context.parseModrmFormat(instruction_bytes, i, RegisterCategory.REG32);
                ret.addOperand(context.getModrmRegValue());
                ret.addOperand(context.getModrmRmValue());
                return ret;
            } else if (context.getOperandSize() == OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of a 64-bit register to a 64-bit
                // destination register or memory operand.
                // Mnemonic:    MOV reg/mem64, reg64
                // Opcode:      89 /r
                ret.setOpCode("89");
                i+=context.parseModrmFormat(instruction_bytes, i, RegisterCategory.REG64);
                ret.addOperand(context.getModrmRegValue());
                ret.addOperand(context.getModrmRmValue());
                return ret;
                
            }

        } else if (b == 0X32) {
        }
        // TODO
        throw new UnsupportedOperationException("TODO");

    }

    public static class InstructionContext {

        private OperandOrAddressSize m_operand_size;
        private OperandOrAddressSize m_address_size;
        private boolean m_rex_r;
        private boolean m_rex_x;
        private boolean m_rex_b;
        private int m_modrm_mod;
        private int m_modrm_reg;
        private int m_modrm_rm;
        private int m_modrm_extended_reg;
        private int m_modrm_extended_rm;
        private OperandRegister m_modrm_reg_value;
        private IOperand m_modrm_rm_value;

        public InstructionContext() {
        }

        private void initFor64BitMode() {
            m_operand_size = OperandOrAddressSize.SIZE_32;
        }

        public void applyOperandSizeOverride() {
            // For 64-bit mode, change the operand size to 16. See AMD64,
            // volume 3, page 8.
            m_operand_size = OperandOrAddressSize.SIZE_16;
        }

        public void applyAddressSizeOverride() {
            // For 64-bit mode, change the address size to 32. See AMD64,
            // volume 3, page 9
            m_address_size = OperandOrAddressSize.SIZE_32;
        }

        public void applyRexPrefix(byte b) {
            // Check REX.W
            if ((b >> 3 & 1) == 1) {
                // For 64-bit mode, change the operand size to 64. See AMD64,
                // volume 3, page 8.
                m_operand_size = OperandOrAddressSize.SIZE_64;
            }

            // Update REX.R, REX.X, REX.B
            m_rex_r = (b >> 2 & 1) == 1;
            m_rex_x = (b >> 1 & 1) == 1;
            m_rex_b = (b & 1) == 1;
        }

        public int parseModrmFormat(List<Byte> instruction_bytes, int start, RegisterCategory register_category) {
            byte b = instruction_bytes.get(start);
            m_modrm_mod = b >> 6 & 3;
            m_modrm_reg = b >> 3 & 7;
            m_modrm_extended_reg = m_modrm_reg + (m_rex_r ? 0x8 : 0);
            m_modrm_rm = b & 7;
            m_modrm_extended_rm = m_modrm_rm + (m_rex_b ? 0x8 : 0);

            // Parse reg
            m_modrm_reg_value = getRegisterType(register_category);

            // Parse r/m
            if (m_modrm_mod == 3) {
                m_modrm_rm_value=getRegisterType(RegisterCategory.REG64);
            }else{
                throw new UnsupportedOperationException("TODO");
            }
            return 1;
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

        private OperandRegister getRegisterType(RegisterCategory category) {
            if (m_rex_r) {
                throw new UnsupportedOperationException("TODO");
            }
            switch (category) {
                case REG32:
                    return new OperandRegister(REG32_ARRAY[m_modrm_reg]);
                case REG64:
                    return new OperandRegister(REG64_ARRAY[m_modrm_reg]);
                default:
                    throw new UnsupportedOperationException("TODO");
            }
        }
        public OperandRegister getModrmRegValue(){
            return m_modrm_reg_value;
        }
        public IOperand getModrmRmValue(){
            return m_modrm_rm_value;
        }

        public OperandOrAddressSize getOperandSize() {
            return m_operand_size;
        }

        public void setOperandSize(OperandOrAddressSize operand_size) {
            this.m_operand_size = operand_size;
        }

        public OperandOrAddressSize getAddressSize() {
            return m_address_size;
        }

        public void setAddressSize(OperandOrAddressSize address_size) {
            this.m_address_size = address_size;
        }
    }

    public static enum OperandOrAddressSize {

        SIZE_16, SIZE_32, SIZE_64,}

    public static enum RegisterCategory {

        REG8, REG16, REG32, REG64
    }

    public static enum RegMemCategory {

        REG_MEM8, REG_MEM16, REG_MEM32, REG_MEM64
    }
}
