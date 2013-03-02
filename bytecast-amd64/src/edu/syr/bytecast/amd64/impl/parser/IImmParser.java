package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.instruction.IOperand;
import java.io.EOFException;

/**
 * An interface to parse immediate bytes to an immediate value.
 *
 * @author sheng
 */
public interface IImmParser {

    /**
     * Types of immediate values.
     */
    static enum Type {

        IMM8, IMM16, IMM32, IMM64
    }

    /**
     * Parse immediate bytes to an immediate value.
     *
     * @param input the input of instruction bytes.
     * @param type the type of the immediate value.
     * @throws EOFException if the end of the stream is reached.
     */
    void parse(IInstructionByteInputStream input, Type type) throws EOFException;

    /**
     * Return the operand of the immediate value.
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
