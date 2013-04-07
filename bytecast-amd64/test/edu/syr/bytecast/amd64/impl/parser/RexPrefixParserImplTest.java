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
public class RexPrefixParserImplTest {

    public RexPrefixParserImplTest() {
    }

    /**
     * Test of parse method, of class RexPrefixParserImpl.
     */
    @Test
    public void testParse() throws Exception {
        // special tests
        RexPrefixParserImpl instance = new RexPrefixParserImpl();
        try {
            instance.parse(new InstructionContextImpl(), new InstructionByteListInputStream(Arrays.asList((byte) 0x00), 0));
            fail();
        } catch (UnexceptedByteException ex) {
        }
    }
}