package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class RexPrefixParserImpl implements IRexPrefixParser {

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();
        if (b < (byte) 0x40 || b > (byte) 0x4f) {
            throw new UnexceptedByteException();
        }
        // Check REX.W
        if ((b >> 3 & 1) == 1) {
            // For 64-bit mode, change the operand size to 64. See AMD64,
            // volume 3, page 8.
            context.setOperandSize(IInstructionContext.OperandOrAddressSize.SIZE_64);
        }
        // Update REX.R, REX.X, REX.B
        context.setRexR((b >> 2 & 1) == 1);
        context.setRexX((b >> 1 & 1) == 1);
        context.setRexB((b & 1) == 1);
    }
}
