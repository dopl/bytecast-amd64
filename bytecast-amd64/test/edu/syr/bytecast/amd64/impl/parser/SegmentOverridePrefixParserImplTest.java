/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class SegmentOverridePrefixParserImplTest {

    public SegmentOverridePrefixParserImplTest() {
    }

    /**
     * Test of parse method, of class SegmentOverridePrefixParserImpl.
     */
    @Test
    public void testParse() throws Exception {
        {
            InstructionContextImpl cnt = new InstructionContextImpl();
            SegmentOverridePrefixParserImpl instance = new SegmentOverridePrefixParserImpl();
            instance.parse(cnt, new InstructionByteListInputStream(Arrays.asList((byte) 0x3e), 0));
            assertEquals(RegisterType.DS, cnt.getSegmentRegister());
        }
        {
            InstructionContextImpl cnt = new InstructionContextImpl();
            SegmentOverridePrefixParserImpl instance = new SegmentOverridePrefixParserImpl();
            instance.parse(cnt, new InstructionByteListInputStream(Arrays.asList((byte) 0x26), 0));
            assertEquals(RegisterType.ES, cnt.getSegmentRegister());
        }
        {
            InstructionContextImpl cnt = new InstructionContextImpl();
            SegmentOverridePrefixParserImpl instance = new SegmentOverridePrefixParserImpl();
            instance.parse(cnt, new InstructionByteListInputStream(Arrays.asList((byte) 0x65), 0));
            assertEquals(RegisterType.GS, cnt.getSegmentRegister());
        }
        {
            InstructionContextImpl cnt = new InstructionContextImpl();
            SegmentOverridePrefixParserImpl instance = new SegmentOverridePrefixParserImpl();
            instance.parse(cnt, new InstructionByteListInputStream(Arrays.asList((byte) 0x36), 0));
            assertEquals(RegisterType.SS, cnt.getSegmentRegister());
        }
        {
            InstructionContextImpl cnt = new InstructionContextImpl();
            SegmentOverridePrefixParserImpl instance = new SegmentOverridePrefixParserImpl();
            try {
                instance.parse(cnt, new InstructionByteListInputStream(Arrays.asList((byte) 0x00), 0));
                fail();
            } catch (UnexceptedByteException ex) {
            }
        }

    }
}