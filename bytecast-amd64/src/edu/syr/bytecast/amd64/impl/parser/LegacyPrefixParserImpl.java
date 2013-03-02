package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sheng
 */
public class LegacyPrefixParserImpl implements ILegacyPrefixParser {

    private static final List<Byte> SEGMENT_PREFIX_LIST = Arrays.asList((byte) 0x2e, (byte) 0x3Ee, (byte) 0x26, (byte) 0x64, (byte) 0x65, (byte) 0x36);

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        // See Section 1.2.1, Volumn 3, AMD64 Manual.
        byte b = input.peek();
        if (b == (byte) 0x66) {
            ParserFactory.getOperandSizeOverridePrefixParser().parse(context, input);
        } else if (b == (byte) 0x67) {
            ParserFactory.getAddressSizeOverridePrefixParser().parse(context, input);
        } else if (SEGMENT_PREFIX_LIST.contains(b)) {
            ParserFactory.getSegmentOverridePrefixParser().parse(context, input);
        } else if (b == (byte) 0xf0) {
            ParserFactory.getLockPrefixParser().parse(context, input);
        } else if (b == (byte) 0xf3 || b == (byte) 0xf2) {
            ParserFactory.getRepeatPrefixParser().parse(context, input);
        }
    }
}
