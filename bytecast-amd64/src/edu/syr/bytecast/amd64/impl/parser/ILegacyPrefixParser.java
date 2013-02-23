package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.io.EOFException;

/**
 * An interface to parse legacy prefixes, which are "Operand-Size Override
 * Prefix", "Address-Size Override Prefix", "Segment Override Prefix", "Lock
 * Prefix", and "Repeat Prefix".
 *
 * @author sheng
 */
public interface ILegacyPrefixParser {

    /**
     * Parse the legacy prefixes in the input and apply it to the context.
     *
     * @param context the instruction context.
     * @param input the input of instruction bytes.
     * @throws EOFException if the end of the stream is reached.
     */
    void parse(InstructionContextImpl context, IInstructionByteInputStream input) throws EOFException;
}
