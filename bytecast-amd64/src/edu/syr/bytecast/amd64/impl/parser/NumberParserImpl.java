package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import java.io.EOFException;

/**
 * A class to parse numbers such as "Immediate Bytes", "Displacement Bytes".
 *
 * @author sheng
 */
public class NumberParserImpl implements IImmParser, IMoffsetParser, IDispParser {

    private long number;

    /**
     * Convert the next bytes into the number in fields. Use inverted order.
     *
     * @param input the input stream to provide bytes.
     * @param size the size of the next bytes to be converted.
     * @throws EOFException if the end of the stream is reached.
     */
    public void parse(IInstructionByteInputStream input, int size) throws EOFException {
        number = 0;
        for (int i = 0; i < size; i++) {
            number = number << 8;
            byte b = input.read();
            number += b & 0xFF;
        }
    }

    @Override
    public void parse(IInstructionByteInputStream input, IImmParser.Type type) throws EOFException {
        int size;
        switch (type) {
            case IMM8:
                size = 1;
                break;
            case IMM16:
                size = 2;
                break;
            case IMM32:
                size = 4;
                break;
            case IMM64:
                size = 8;
                break;
            default:
                throw new RuntimeException("Unknown type!");
        }
        parse(input, size);
    }

    @Override
    public IOperand getOperand() {
        return new OperandConstant(number);
    }

    @Override
    public void parse(IInstructionByteInputStream input, IMoffsetParser.Type type) throws EOFException {
        int size;
        switch (type) {
            case MOFFSET8:
                size = 1;
                break;
            case MOFFSET16:
                size = 2;
                break;
            case MOFFSET32:
                size = 4;
                break;
            case MOFFSET64:
                size = 8;
                break;
            default:
                throw new RuntimeException("Unknown type!");
        }
        parse(input, size);
    }

    @Override
    public long getNumber() {
        return number;
    }

    @Override
    public void parse(IInstructionByteInputStream input, IDispParser.Type type) throws EOFException {
        int size;
        switch (type) {
            case DISP8:
                size = 1;
                break;
            case DISP16:
                size = 2;
                break;
            case DISP32:
                size = 4;
                break;
            default:
                throw new RuntimeException("Unknown type!");
        }
        parse(input, size);
    }
}
