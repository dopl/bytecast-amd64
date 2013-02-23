package edu.syr.bytecast.amd64.impl.instruction;

/**
 * An interface to store context information of a current parsed instruction.
 *
 * @author sheng
 */
public interface IInstructionContext {

    public static enum OperandOrAddressSize {

        SIZE_16, SIZE_32, SIZE_64
    }

    OperandOrAddressSize getAddressSize();

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
}
