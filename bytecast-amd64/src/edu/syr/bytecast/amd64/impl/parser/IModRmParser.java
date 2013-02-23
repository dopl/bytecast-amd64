package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import java.io.EOFException;

/**
 * An interface to parse the ModRM bytes. It will also parse SIB bytes if the
 * ModRM bytes need them.
 *
 * @author sheng
 */
public interface IModRmParser {

    public static enum RegType {

        NONE, REG8, REG16, REG32, REG64
    }

    public static enum RmType {

        NONE, REG_MEM8, REG_MEM16, REG_MEM32, REG_MEM64
    }

    /**
     * Return the number of mod bits. It is from 0 to 3.
     *
     * @return
     */
    int getMod();

    /**
     * Return the number of reg bits. It is from 0 to 7.
     *
     * @return
     */
    int getReg();

    /**
     * Return the number of r/m bits. It is from 0 to 7.
     *
     * @return
     */
    int getRm();

    /**
     * Return the extended number of reg bits. It includes REX.R, VEX.R or
     * XOP.R. It is from 0 to 15.
     *
     * @return
     */
    int getExtendedReg();

    /**
     * Return the extended number of reg bits. It includes REX.B, VEX.B or
     * XOP.B. It is from 0 to 15.
     *
     * @return
     */
    int getExtendedRm();

    /**
     * Return the operand of the reg parameter.
     *
     * @return
     */
    OperandRegister getRegOperand();

    /**
     * Return the operand of the rm parameter.
     *
     * @return
     */
    IOperand getRmOperand();

    /**
     * Parse the ModRm bytes in the input.
     *
     * @param context the instruction context.
     * @param input the input of instruction bytes.
     * @param reg_type the type of reg
     * @param rm_type the type of reg/mem
     * @throws EOFException if the end of the stream is reached.
     */
    void parse(IInstructionContext context, IInstructionByteInputStream input,
            RegType reg_type, RmType rm_type) throws EOFException;
}
