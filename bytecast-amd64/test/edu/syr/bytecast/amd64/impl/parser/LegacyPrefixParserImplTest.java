package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class LegacyPrefixParserImplTest {

    public LegacyPrefixParserImplTest() {
    }

    /**
     * Test of parse method, of class LegacyPrefixParserImpl.
     */
    @Test
    public void testParse() throws Exception {
        // for not covered
        {
            LegacyPrefixParserImpl instance = new LegacyPrefixParserImpl();
            try {
                instance.parse(new InstructionContextImpl(), new InstructionByteListInputStream(Arrays.asList((byte) 0xf0), 0));
                fail();
            } catch (UnsupportedOperationException ex) {
            }
        }
        {
            LegacyPrefixParserImpl instance = new LegacyPrefixParserImpl();
            try {
                instance.parse(new InstructionContextImpl(), new InstructionByteListInputStream(Arrays.asList((byte) 0xf3), 0));
                fail();
            } catch (UnsupportedOperationException ex) {
            }
        }
        {
            LegacyPrefixParserImpl instance = new LegacyPrefixParserImpl();
            try {
                instance.parse(new InstructionContextImpl(), new InstructionByteListInputStream(Arrays.asList((byte) 0xf2), 0));
                fail();
            } catch (UnsupportedOperationException ex) {
            }
        }
    }
}