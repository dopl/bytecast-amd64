package edu.syr.bytecast.amd64.impl.parserdemo;

/**
 * An interface to parse "VEX and XOP Prefix".
 *
 * @author sheng
 */
public interface IVexXopPrefixParser {

    /**
     * Parse the "VEX and XOP Prefix" in the input and apply it to the context.
     *
     * @param context
     * @param input
     */
    void parse(InstructionContextImpl context, IByteInstructionInputStream input);
}
