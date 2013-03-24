package edu.syr.bytecast.amd64.impl.instruction;

import edu.syr.bytecast.amd64.api.constants.RegisterType;

/**
 * An interface to store context information of a current parsed instruction.
 *
 * @author sheng
 */
public interface IInstructionContext {

    /**
     * An enum which defines operand sizes or address sizes.
     */
    static enum OperandOrAddressSize {

        SIZE_16, SIZE_32, SIZE_64
    }

    /**
     * Return the address size.
     *
     * @return
     */
    OperandOrAddressSize getAddressSize();

    /**
     * Return the operand size.
     *
     * @return
     */
    OperandOrAddressSize getOperandSize();

    void setAddressSize(OperandOrAddressSize address_size);

    void setOperandSize(OperandOrAddressSize operand_size);

    /**
     * Return true if the REX.B bit is 1.
     *
     * @return
     */
    boolean isRexB();

    /**
     * Return true if the REX.R bit is 1.
     *
     * @return
     */
    boolean isRexR();

    /**
     * Return true if the REX.X bit is 1.
     *
     * @return
     */
    boolean isRexX();

    void setRexB(boolean rex_b);

    void setRexR(boolean rex_r);

    void setRexX(boolean rex_x);

    /**
     * Return the segment register which is defined by Segment-Override
     * prefixes.
     *
     * @return
     */
    RegisterType getSegmentRegister();

    /**
     * Set the segment register which is defined by Segment-Override prefixes.
     *
     * @param type
     */
    void setSegmentRegister(RegisterType type);
    
    
    
      /**
     * An enum which defines which opcode map to lookup
     */
    static enum OpcodeMap {

        OCM_PRIMARY,
        OCM_SECONDARY,
        OCM_38H,
        OCM_3AH
                
    }
    /**
     * Gets the enum describing which opcode map should be looked up for finding
     * the instruction opcode.
     * @return 
     */
    OpcodeMap getOpcodeMapForInstruction();
    
    /**
     * Sets the enum describing which opcode map should be looked up for finding
     * the instruction opcode.
     * @return 
     */
    void setOpcodeMapForInstruction(OpcodeMap map);
}
