/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64;

import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import edu.syr.bytecast.test.mockups.MockBytecastFsys;
import edu.syr.bytecast.util.Paths;
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
public class BytecastAmd64Test {
    
    public BytecastAmd64Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of buildInstructionObjects method, of class BytecastAmd64.
     */
    @Test
    public void testBuildInstructionObjects() {
        System.out.println("buildInstructionObjects");
        IBytecastFsys fsys = new MockBytecastFsys();
        Paths.v().setRoot("/home/bytecast/code/bytecast");                  
        try {
            Paths.v().parsePathsFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        BytecastAmd64 instance = new BytecastAmd64(fsys, "a.out");
        IExecutableFile expResult = null;
        IExecutableFile result = instance.buildInstructionObjects();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

  
}
