/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.lexer;

import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bytecast
 */
public class AMD64InstructionLexerTest {
    
     Long sectionStartMemeAddress;
    List<Byte> bytes;
    List<MemoryInstructionPair> expResult ;
    public AMD64InstructionLexerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        /*400494:	55                   	push   %rbp
        400495:	48 89 e5             	mov    %rsp,%rbp
        400498:	89 7d ec             	mov    %edi,-0x14(%rbp)
        40049b:	48 89 75 e0          	mov    %rsi,-0x20(%rbp)*/
        bytes = new ArrayList<Byte>();
        sectionStartMemeAddress = (long) 0x400494;
        bytes.add((byte)0x55);
        bytes.add((byte)0x48);
        bytes.add((byte)0x89);
        bytes.add((byte)0xe5);
        bytes.add((byte)0x89);
        bytes.add((byte)0x7d);
        bytes.add((byte)0xec);
        bytes.add((byte)0x48);
        bytes.add((byte)0x89);
        bytes.add((byte)0x75);
        bytes.add((byte)0xe0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of convertInstructionBytesToObjects method, of class AMD64InstructionLexer.
     */
    @Test
    public void testConvertInstructionBytesToObjects() {
        System.out.println("convertInstructionBytesToObjects");
        
        
        AMD64InstructionLexer instance = new AMD64InstructionLexer();
        
        List<MemoryInstructionPair> result = instance.convertInstructionBytesToObjects(sectionStartMemeAddress, bytes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
