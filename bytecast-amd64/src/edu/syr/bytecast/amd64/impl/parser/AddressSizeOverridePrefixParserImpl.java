package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class AddressSizeOverridePrefixParserImpl implements IAddressSizeOverridePrefixParser {

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        // If the next byte is not 0x67, throw an exception.
        if (input.peek() != (byte) 0x67) {
            throw new UnexceptedByteException();
        }
        input.skip(1);
        // The prefix changes the address size to 32 bits. See Section 1.2.3, Volume 3, AMD64 Manual.
        context.setAddressSize(IInstructionContext.OperandOrAddressSize.SIZE_32);
    }
}
