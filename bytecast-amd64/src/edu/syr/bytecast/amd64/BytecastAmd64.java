/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.amd64;

import edu.syr.bytecast.amd64.api.constants.FileFormats;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.impl.dictionary.AMD64Dictionary;
import edu.syr.bytecast.amd64.impl.lexer.AMD64InstructionLexer;
import edu.syr.bytecast.amd64.impl.output.AMD64ExecutableFile;
import edu.syr.bytecast.amd64.impl.output.AMD64Section;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionLexer;
import edu.syr.bytecast.common.impl.exception.BytecastAMD64Exception;
import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.ExeObjFunction;
import edu.syr.bytecast.interfaces.fsys.ExeObjSegment;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import edu.syr.bytecast.jimple.api.IJimple;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class BytecastAmd64 {

    private IBytecastFsys m_fsys;
    private IJimple m_jimple;
    private String m_filepath;
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    if(args.length==0)
    {
        System.out.println("Error:No arguments specified");
        displayUsage();
    }
  }

  /**
   * Prints out the usage information of the system
   */
  public static void displayUsage()
  {
      System.out.println("");
  }
  
  
  /**
   * BytecastAMD64 constructor which is initialized with service interfaces from Fsys and Jimple
   * @param fsys
   * @param jimple 
   */
  public BytecastAmd64(IBytecastFsys fsys, IJimple jimple, String filePath)
  {
      this.m_fsys = fsys;
      this.m_jimple = jimple;
      this.m_filepath = filePath;
  }
    /**
     * This function runs the Bytecast system
     */
   public void run()
   {
       
       try{
           
            ExeObj fsysExec = doFsys();
            List<ISection> sections = parseAllSegments(fsysExec.getSegments(),fsysExec.getEntryPointIndex());
            IExecutableFile exec = new AMD64ExecutableFile(sections, m_filepath, FileFormats.FF_ELF, null);
            doJimple(exec);
            
       }catch(Exception ex)
       {
           System.out.println(ex.getMessage());
           return;
       }
       
   }
   public ExeObj doFsys() throws Exception
   {
        m_fsys.setFilepath(m_filepath);
        ExeObj exeObj = m_fsys.parse();
        List<ExeObjFunction> functions = exeObj.getFunctions();
        createFnSymbolTableInDict(functions);
        return exeObj;
   }

    private void createFnSymbolTableInDict(List<ExeObjFunction> functions) {
       Hashtable<Long,String> fnSymbolTable = new Hashtable<Long, String>();
       for(ExeObjFunction fn : functions)
       {
           fnSymbolTable.put(fn.getStartAddress(), fn.getName());
       }
       AMD64Dictionary.getInstance().setFunctionSymbolTable(fnSymbolTable);
    }

    private List<ISection> parseAllSegments(List<ExeObjSegment> segments, Long entryPointIndex) throws BytecastAMD64Exception {
        List<ISection> sections = new ArrayList<ISection>();  
        IInstructionLexer lexer = new AMD64InstructionLexer();
        int index=0;
           for(ExeObjSegment segment : segments)
           {
               Map<Long,IInstruction> instructions 
                     = lexer.convertInstructionBytesToObjects(segment.getStartAddress(), segment.getBytes());
               ISection section = new AMD64Section(instructions,segment.getStartAddress(),entryPointIndex==index);
               sections.add(section);
               ++index;
           }
        return sections;
    }
    

    private void doJimple(IExecutableFile exec) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
 
    
}
