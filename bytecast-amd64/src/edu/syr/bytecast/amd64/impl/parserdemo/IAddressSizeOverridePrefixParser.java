package edu.syr.bytecast.amd64.impl.parserdemo;

/**
 * An interface to parse "Address-Size Override Prefix".
 *
 * @author sheng
 */
public interface IAddressSizeOverridePrefixParser {

    /**
     * Parse the "Address-Size Override Prefix" in the input and apply it to the
     * context.
     *
     * @param context
     * @param input
     */
    void parse(InstructionContextImpl context, IByteInstructionInputStream input);
}
