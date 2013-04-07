/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class SibParserImplTest {

    public SibParserImplTest() {
    }

    /**
     * Test of parse method, of class SibParserImpl.
     */
    @Test
    public void testParse() throws Exception {
        {
            // Test 400619:	41 ff 14 dc          	callq  *(%r12,%rbx,8)
            InstructionByteListInputStream input = new InstructionByteListInputStream(ParserTestUtils.stringToByteList("dc"), 0x4004b4L);
            SibParserImpl instance = new SibParserImpl();
            InstructionContextImpl cnt = new InstructionContextImpl();
            cnt.setRexB(true);
            instance.parse(cnt, input, 0);
            assertEquals(3, instance.getScale());
            assertEquals(4, instance.getBase());
            assertEquals(3, instance.getIndex());
        }

        // for special tests
        {
            SibParserImpl instance = new SibParserImpl();
            Class<?> clazz = instance.getClass();
            Field field = clazz.getDeclaredField("scare");
            field.setAccessible(true);
            field.set(instance, 4);
            try {
                instance.getScaleFactor();
                fail();
            } catch (RuntimeException ex) {
            }
        }
    }
}