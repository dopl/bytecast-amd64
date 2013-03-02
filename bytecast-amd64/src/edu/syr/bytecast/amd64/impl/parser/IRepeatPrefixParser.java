package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 * An interface to parse "Repeat Prefix".
 *
 * @author sheng
 */
public interface IRepeatPrefixParser {

    /**
     * Parse the "Repeat Prefix" in the input and apply it to the context.
     *
     * @param context the instruction context.
     * @param input the input of instruction bytes.
     * @throws EOFException if the end of the stream is reached.
     */
    void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException;
}
