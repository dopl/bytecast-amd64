/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64;

import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.test.IExecutableFileUtils;
import edu.syr.bytecast.amd64.test.IExecutableFileUtils.CompareResult;
import edu.syr.bytecast.amd64.test.DepcrecatedMock;
import edu.syr.bytecast.amd64.util.AMD64MockGenerator;
import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import edu.syr.bytecast.test.mockups.MockBytecastFsys;
import edu.syr.bytecast.test.mockups.MockBytecastFsysPoc3;
import edu.syr.bytecast.util.Paths;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
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
    public void testBuildInstructionObjects() throws FileNotFoundException, IOException, Exception {
       
        IBytecastFsys fsys = new MockBytecastFsys();
        Paths.v().setRoot("/home/bytecast/code/bytecast");                  
        try {
            Paths.v().parsePathsFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        BytecastAmd64 instance = new BytecastAmd64(fsys, "a.out");
         Set<String> exclusion = new HashSet<String>();
         exclusion.add("<_IO_printf>");
        IExecutableFile expResult = new AMD64MockGenerator(new MockBytecastFsys(), 
                "/home/bytecast/code/bytecast/bytecast-documents/AsciiManip01Prototype/a.out.static.objdump", "<main>", exclusion).generate().buildInstructionObjects();
        IExecutableFile result = instance.buildInstructionObjects();
        CompareResult compareSections = IExecutableFileUtils.compareSections(result, expResult);
        System.out.println("TOTAL:" + compareSections.getTotalInstructionCount());
        System.out.println("PASSED:" + compareSections.getPassedInstructionCount());
        System.out.println("FAILED:" + compareSections.getErrorInstructionCount());
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
     /**
     * Test of buildInstructionObjects method, of class BytecastAmd64.
     */
    @Test
    public void testBuildInstructionObjectsPOC3() throws FileNotFoundException, IOException, Exception {
        
        IBytecastFsys fsys = new MockBytecastFsysPoc3();
        Paths.v().setRoot("/home/bytecast/code/bytecast");                  
        try {
            Paths.v().parsePathsFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        BytecastAmd64 instance = new BytecastAmd64(fsys, "a.out");
         Set<String> exclusion = new HashSet<String>();
         exclusion.add("<_IO_printf>");
        IExecutableFile expResult = new AMD64MockGenerator(new MockBytecastFsysPoc3(), 
                "/home/bytecast/code/bytecast/bytecast-documents/AsciiManip02Prototype/a.out.static.objdump", "<main>", exclusion).generate().buildInstructionObjects();
        IExecutableFile result = instance.buildInstructionObjects();
        CompareResult compareSections = IExecutableFileUtils.compareSections(result, expResult);
        System.out.println("TOTAL:" + compareSections.getTotalInstructionCount());
        System.out.println("PASSED:" + compareSections.getPassedInstructionCount());
        System.out.println("FAILED:" + compareSections.getErrorInstructionCount());
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

  
}
