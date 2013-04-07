/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class OperandSizeOverridePrefixParserImplTest {

    public OperandSizeOverridePrefixParserImplTest() {
    }

    /**
     * Test of parse method, of class OperandSizeOverridePrefixParserImpl.
     */
    @Test
    public void testParse() throws Exception {
        // special tests
        OperandSizeOverridePrefixParserImpl instance = new OperandSizeOverridePrefixParserImpl();
        try {
            instance.parse(new InstructionContextImpl(), new InstructionByteListInputStream(Arrays.asList((byte) 0x11), 0));
            fail();
        } catch (UnexceptedByteException ex) {
        }
    }
}