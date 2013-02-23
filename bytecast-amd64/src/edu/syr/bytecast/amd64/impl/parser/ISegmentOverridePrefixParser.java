package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;

/**
 * An interface to parse "Segment Override Prefix".
 *
 * @author sheng
 */
public interface ISegmentOverridePrefixParser {

    /**
     * Parse the "Segment Override Prefix" in the input and apply it to the
     * context.
     *
     * @param context
     * @param input
     */
    void parse(InstructionContextImpl context, IInstructionByteInputStream input);
}
