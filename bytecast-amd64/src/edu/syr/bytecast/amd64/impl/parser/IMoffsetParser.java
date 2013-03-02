package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.instruction.IOperand;
import java.io.EOFException;

/**
 * An interface to parse "moffset8", "moffset16", "moffset32", and "moffset64"
 * parameter.
 *
 * @author sheng
 */
public interface IMoffsetParser {

    /**
     * Types of moffset.
     */
    static enum Type {

        MOFFSET8, MOFFSET16, MOFFSET32, MOFFSET64
    }

    /**
     * parse "moffset8", "moffset16", "moffset32", and "moffset64" parameter to
     * a moffset value.
     *
     * @param input the input of instruction bytes.
     * @param type the type of moffset.
     * @throws EOFException if the end of the stream is reached.
     */
    void parse(IInstructionByteInputStream input, Type type) throws EOFException;

    /**
     * Return the operand of the moffset value.
     *
     * @return
     */
    IOperand getOperand();

    /**
     * Return the parsed number.
     *
     * @return
     */
    long getNumber();
}
