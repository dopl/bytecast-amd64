package edu.syr.bytecast.amd64.impl.parserdemo;

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
     * @param context
     * @param input
     */
    void parse(InstructionContextImpl context, IByteInstructionInputStream input);
}
