package edu.syr.bytecast.amd64.impl.instruction;

import edu.syr.bytecast.amd64.api.constants.RegisterType;

/**
 * A class to store instruction context such as operand size, address size and
 * prefix values.
 *
 * @author sheng
 */
public class InstructionContextImpl implements IInstructionContext {
    private OpcodeMap m_opcodemap;
    private OperandOrAddressSize m_operand_size;
    private OperandOrAddressSize m_address_size;
    private boolean m_rex_r;
    private boolean m_rex_x;
    private boolean m_rex_b;
    private RegisterType m_segment_register;

    public InstructionContextImpl() {
        initFor64BitMode();
    }

    private void initFor64BitMode() {
        // The default operand size in 64-bit mode is 32 bits. See Section 1.2.2, Volume 3, AMD64 Manual.
        m_operand_size = OperandOrAddressSize.SIZE_32;
        // The default address size in 64-bit mode is 64 bits. See Section 1.2.3, Volume 3, AMD64 Manual.
        m_address_size = OperandOrAddressSize.SIZE_64;
        m_opcodemap = OpcodeMap.OCM_PRIMARY;
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

    @Override
    public RegisterType getSegmentRegister() {
        return m_segment_register;
    }

    @Override
    public void setSegmentRegister(RegisterType type) {
        m_segment_register = type;
    }

    @Override
    public OpcodeMap getOpcodeMapForInstruction() {
        return this.m_opcodemap;
    }

    @Override
    public void setOpcodeMapForInstruction(OpcodeMap map) {
        m_opcodemap = map;
    }
}
