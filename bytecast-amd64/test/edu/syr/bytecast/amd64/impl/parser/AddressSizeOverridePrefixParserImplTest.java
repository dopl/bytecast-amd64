package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class AddressSizeOverridePrefixParserImplTest {

    public AddressSizeOverridePrefixParserImplTest() {
    }

    /**
     * Test of parse method, of class AddressSizeOverridePrefixParserImpl.
     */
    @Test
    public void testParse() throws Exception {
        { // test abnormal 
            InstructionByteListInputStream input = new InstructionByteListInputStream(Arrays.asList((byte) 0x11), 0);
            AddressSizeOverridePrefixParserImpl instance = new AddressSizeOverridePrefixParserImpl();
            try {
                instance.parse(new InstructionContextImpl(), input);
                fail();
            } catch (UnexceptedByteException ex) {
            }
        }
        { // test normal
            InstructionByteListInputStream input = new InstructionByteListInputStream(Arrays.asList((byte) 0x67), 0);
            AddressSizeOverridePrefixParserImpl instance = new AddressSizeOverridePrefixParserImpl();
            InstructionContextImpl cnt = new InstructionContextImpl();
            instance.parse(cnt, input);
            assertEquals(IInstructionContext.OperandOrAddressSize.SIZE_32, cnt.getAddressSize());
        }
    }
}