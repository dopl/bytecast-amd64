package edu.syr.bytecast.amd64.impl.parserdemo;

import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import java.io.EOFException;

/**
 * A class to parse numbers such as "Immediate Bytes", "Displacement Bytes".
 *
 * @author sheng
 */
public interface INumberParser {

    public static enum NumberType {

        SIZE8, SIZE16, SIZE32, SIZE64
    }

    /**
     * Return an OperandConstant which contains the parsed number.
     *
     * @return
     */
    // TODO We should return different type of IOperand according to byte types
    // such as "Immediate Bytes", "Displacement Bytes".
    OperandConstant getOperand();

    /**
     *
     * @param input
     * @param type
     * @return
     * @throws EOFException
     */
    void parse(IByteInstructionInputStream input, NumberType type) throws EOFException;
}
