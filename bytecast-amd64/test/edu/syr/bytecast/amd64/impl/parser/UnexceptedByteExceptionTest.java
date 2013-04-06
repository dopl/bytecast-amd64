/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class UnexceptedByteExceptionTest {

    public UnexceptedByteExceptionTest() {
    }

    @Test
    public void testConstructor() {
        // Just for test coverage
        UnexceptedByteException instance = new UnexceptedByteException("For test");
        assertEquals("For test", instance.getMessage());
    }
}