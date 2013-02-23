package edu.syr.bytecast.amd64.impl.parserdemo;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import java.io.EOFException;

/**
 * A class to parse the ModRM bytes. It will also parse SIB bytes if the ModRM
 * bytes need them.
 *
 * @author sheng
 */
public class ModRmParserImpl implements IModRmParser {

    private int m_mod;
    private int m_reg;
    private int m_rm;
    private int m_extended_reg;
    private int m_extended_rm;
    private OperandRegister m_reg_operand;
    private IOperand m_rm_operand;
    private InstructionContextImpl m_context;

    public ModRmParserImpl(InstructionContextImpl context) {
        this.m_context = context;
    }

    @Override
    public void parse(InstructionContextImpl context, IByteInstructionInputStream input, RegType reg_type, RmType rm_type) throws EOFException {
        byte b = input.read();
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
    }

    @Override
    public int getReg() {
        return m_reg;
    }

    @Override
    public int getMod() {
        return m_mod;
    }

    @Override
    public int getRm() {
        return m_rm;
    }

    @Override
    public int getExtendedReg() {
        return m_extended_reg;
    }

    @Override
    public int getExtendedRm() {
        return m_extended_rm;
    }

    @Override
    public OperandRegister getRegOperand() {
        return m_reg_operand;
    }

    @Override
    public IOperand getRmOperand() {
        return m_rm_operand;
    }
    private static final RegisterType[] REG32_ARRAY = new RegisterType[]{RegisterType.EAX, RegisterType.ECX, RegisterType.EDX, RegisterType.EBX, RegisterType.ESP, RegisterType.EBP, RegisterType.ESI, RegisterType.EDI};
    private static final RegisterType[] REG64_ARRAY = new RegisterType[]{RegisterType.RAX, RegisterType.RCX, RegisterType.RDX, RegisterType.RBX, RegisterType.RSP, RegisterType.RBP, RegisterType.RSI, RegisterType.RDI, RegisterType.R8, RegisterType.R9, RegisterType.R10, RegisterType.R11, RegisterType.R12, RegisterType.R13, RegisterType.R14, RegisterType.R15};

    private OperandRegister getRegOperand(ModRmParserImpl.RegType type) {
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

    private OperandRegister getRmOperandForRegisterType(ModRmParserImpl.RmType type) {
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
