package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class SegmentOverridePrefixParserImpl implements ISegmentOverridePrefixParser {

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        // See Section 1.2.4, Volumn 3, AMD64 Manual.
        byte b = input.peek();
        if (b == (byte) 0x2e) {
            // CS segment
            context.setSegmentRegister(RegisterType.CS);
        } else if (b == (byte) 0x3e) {
            // DS segment
            context.setSegmentRegister(RegisterType.DS);
        } else if (b == (byte) 0x26) {
            // ES segment
            context.setSegmentRegister(RegisterType.ES);
        } else if (b == (byte) 0x64) {
            // FS segment
            context.setSegmentRegister(RegisterType.FS);
        } else if (b == (byte) 0x65) {
            // GS segment
            context.setSegmentRegister(RegisterType.GS);
        } else if (b == (byte) 0x36) {
            // SS segment
            context.setSegmentRegister(RegisterType.SS);
        } else {
            throw new UnexceptedByteException();
        }
        input.skip(1);
    }
}
