package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.io.EOFException;

/**
 * An interface to parse "Operand-Size Override Prefix".
 *
 * @author sheng
 */
public interface IOperandSizeOverridePrefixParser {

    /**
     * Parse the "Address-Size Override Prefix" in the input and apply it to the
     * context.
     *
     * @param context the instruction context.
     * @param input the input of instruction bytes.
     * @throws EOFException if the end of the stream is reached.
     */
    void parse(InstructionContextImpl context, IInstructionByteInputStream input) throws EOFException;
}
