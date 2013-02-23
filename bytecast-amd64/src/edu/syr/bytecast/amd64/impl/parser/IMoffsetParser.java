package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import java.io.EOFException;

/**
 * An interface to parse "moffset8", "moffset16", "moffset32", and "moffset64"
 * parameter.
 *
 * @author sheng
 */
public interface IMoffsetParser {

    static enum Type {

        MOFFSET8, MOFFSET16, MOFFSET32, MOFFSET64
    }

    /**
     *
     * @param input
     * @param type
     * @return
     * @throws EOFException
     */
    void parse(IInstructionByteInputStream input, Type type) throws EOFException;
    
    IOperand getOperand();
}
