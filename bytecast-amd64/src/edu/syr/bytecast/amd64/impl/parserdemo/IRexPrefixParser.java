package edu.syr.bytecast.amd64.impl.parserdemo;

/**
 * An interface to parse "REX Prefix".
 *
 * @author sheng
 */
public interface IRexPrefixParser {

    /**
     * Parse the "REX Prefix" in the input and apply it to the context.
     *
     * @param context
     * @param input
     */
    void parse(InstructionContextImpl context, IByteInstructionInputStream input);
}
