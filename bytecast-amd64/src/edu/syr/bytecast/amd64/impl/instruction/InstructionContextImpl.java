package edu.syr.bytecast.amd64.impl.instruction;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;

/**
 * A class to store instruction context such as operand size, address size and
 * prefix values.
 *
 * @author sheng
 */
public class InstructionContextImpl implements IInstructionContext {

    private OperandOrAddressSize m_operand_size;
    private OperandOrAddressSize m_address_size;
    private boolean m_rex_r;
    private boolean m_rex_x;
    private boolean m_rex_b;

    public InstructionContextImpl() {
        initFor64BitMode();
    }

    private void initFor64BitMode() {
        m_operand_size = OperandOrAddressSize.SIZE_32;
    }

    @Override
    public boolean isRexR() {
        return m_rex_r;
    }

    @Override
    public void setRexR(boolean rex_r) {
        m_rex_r = rex_r;
    }

    @Override
    public boolean isRexX() {
        return m_rex_x;
    }

    @Override
    public void setRexX(boolean rex_x) {
        m_rex_x = rex_x;
    }

    @Override
    public boolean isRexB() {
        return m_rex_b;
    }

    @Override
    public void setRexB(boolean rex_b) {
        m_rex_b = rex_b;
    }

    @Override
    public OperandOrAddressSize getOperandSize() {
        return m_operand_size;
    }

    @Override
    public void setOperandSize(OperandOrAddressSize operand_size) {
        this.m_operand_size = operand_size;
    }

    @Override
    public OperandOrAddressSize getAddressSize() {
        return m_address_size;
    }

    @Override
    public void setAddressSize(OperandOrAddressSize address_size) {
        this.m_address_size = address_size;
    }
}
