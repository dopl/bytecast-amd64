package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class OperandSizeOverridePrefixParserImpl implements IOperandSizeOverridePrefixParser {

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        // If the next byte is not 0x66, throw an exception.
        if (input.peek() != (byte) 0x66) {
            throw new UnexceptedByteException();
        }
        input.skip(1);
        // The prefix changes the operand size to 16 bits. See Section 1.2.2, Volume 3, AMD64 Manual.
        context.setOperandSize(IInstructionContext.OperandOrAddressSize.SIZE_16);
    }
}
