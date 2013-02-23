package edu.syr.bytecast.amd64.impl.parserdemo;

/**
 * An interface to parse "Lock Prefix".
 *
 * @author sheng
 */
public interface ILockPrefixParser {

    /**
     * Parse the "Lock Prefix" in the input and apply it to the
     * context.
     *
     * @param context
     * @param input
     */
    void parse(InstructionContextImpl context, IByteInstructionInputStream input);
}
