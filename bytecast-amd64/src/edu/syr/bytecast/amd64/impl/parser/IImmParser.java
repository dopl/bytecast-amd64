package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import java.io.EOFException;

/**
 * An interface to parse "imm8", "imm16", "imm32", and "imm64" parameters.
 *
 * @author sheng
 */
public interface IImmParser {

    static enum Type {

        IMM8, IMM16, IMM32, IMM64
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
