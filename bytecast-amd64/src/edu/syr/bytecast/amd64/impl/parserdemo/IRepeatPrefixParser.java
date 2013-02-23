package edu.syr.bytecast.amd64.impl.parserdemo;

/**
 * An interface to parse "Repeat Prefix".
 *
 * @author sheng
 */
public interface IRepeatPrefixParser {

    /**
     * Parse the "Repeat Prefix" in the input and apply it to the context.
     *
     * @param context
     * @param input
     */
    void parse(InstructionContextImpl context, IByteInstructionInputStream input);
}
