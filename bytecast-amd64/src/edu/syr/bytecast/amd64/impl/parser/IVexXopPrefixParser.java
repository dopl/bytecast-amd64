package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 * An interface to parse "VEX and XOP Prefix".
 *
 * @author sheng
 */
public interface IVexXopPrefixParser {

    /**
     * Parse the "VEX and XOP Prefix" in the input and apply it to the context.
     *
     * @param context the instruction context.
     * @param input the input of instruction bytes.
     * @throws EOFException if the end of the stream is reached.
     */
    void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException;
}
