/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class NumberParserImplTest {

    public NumberParserImplTest() {
    }

    /**
     * Test of parse method, of class NumberParserImpl.
     */
    @Test
    public void testParse_IInstructionByteInputStream_int() throws Exception {
        // speical tests
        InstructionByteListInputStream input = new InstructionByteListInputStream(Arrays.asList((byte) 0x01, (byte) 0x02), 0);
        NumberParserImpl instance = new NumberParserImpl();
        instance.parse(input, 0);
        assertEquals(0, instance.getNumber());
    }

    /**
     * Test of parse method, of class NumberParserImpl.
     */
    @Test
    public void testParse_IInstructionByteInputStream_IImmParserType() throws Exception {
        // speical tests
        InstructionByteListInputStream input = new InstructionByteListInputStream(Arrays.asList((byte) 0x01, (byte) 0x02), 0);
        NumberParserImpl instance = new NumberParserImpl();
        try {
            instance.parse(input, (IImmParser.Type) null);
            fail();
        } catch (RuntimeException ex) {
        }
    }

    /**
     * Test of parse method, of class NumberParserImpl.
     */
    @Test
    public void testParse_IInstructionByteInputStream_IMoffsetParserType() throws Exception {
        // speical tests
        InstructionByteListInputStream input = new InstructionByteListInputStream(Arrays.asList((byte) 0x01, (byte) 0x02), 0);
        NumberParserImpl instance = new NumberParserImpl();
        try {
            instance.parse(input, (IMoffsetParser.Type) null);
            fail();
        } catch (RuntimeException ex) {
        }
    }

    /**
     * Test of parse method, of class NumberParserImpl.
     */
    @Test
    public void testParse_IInstructionByteInputStream_IDispParserType() throws Exception {
        // speical tests
        InstructionByteListInputStream input = new InstructionByteListInputStream(Arrays.asList((byte) 0x01, (byte) 0x02), 0);
        NumberParserImpl instance = new NumberParserImpl();
        try {
            instance.parse(input, (IDispParser.Type) null);
            fail();
        } catch (RuntimeException ex) {
        }
    }
}