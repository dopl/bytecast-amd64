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
import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class BytecastAmd64 implements IBytecastAMD64{

    private IBytecastFsys m_fsys;
    private String m_filepath;

    public BytecastAmd64(IBytecastFsys m_fsys, String m_filepath) {
        this.m_fsys = m_fsys;
        this.m_filepath = m_filepath;
    }
    
  

  
  @Override
    public IExecutableFile buildInstructionObjects() {
      
      IExecutableFile exec;
      try{
           
            ExeObj fsysExec = doFsys();
            List<ISection> sections = parseAllSegments(fsysExec.getSegments(),fsysExec.getEntryPointIndex());
            exec = new AMD64ExecutableFile(fsysExec.getSegments(),sections, m_filepath, FileFormats.FF_ELF, null);
            
       }catch(Exception ex)
       {
           System.out.println(ex.getMessage());
           return null;
       }
      return exec;
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
               List<MemoryInstructionPair> instructions 
                     = lexer.convertInstructionBytesToObjects(segment.getStartAddress(), segment.getBytes());
               ISection section = new AMD64Section(instructions,segment.getStartAddress(),entryPointIndex==index);
               sections.add(section);
               ++index;
           }
        return sections;
    }
  
}
