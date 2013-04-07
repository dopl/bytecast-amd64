/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class RegisterInOpcodeParserImplTest {

    public RegisterInOpcodeParserImplTest() {
    }

    /**
     * Test of parse method, of class RegisterInOpcodeParserImpl.
     */
    @Test
    public void testParse() {
        RegisterInOpcodeParserImpl instance = new RegisterInOpcodeParserImpl();
        try {
            instance.parse(new InstructionContextImpl(), null, 0);
            fail();
        } catch (RuntimeException ex) {
        }
    }
}