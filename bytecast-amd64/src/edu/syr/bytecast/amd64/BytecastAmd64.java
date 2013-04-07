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
import edu.syr.bytecast.amd64.internal.api.parser.IFunctionCallListener;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionLexer;
import edu.syr.bytecast.common.impl.exception.BytecastAMD64Exception;
import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.ExeObjFunction;
import edu.syr.bytecast.interfaces.fsys.ExeObjSegment;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BytecastAmd64 implements IBytecastAMD64{

    private IBytecastFsys m_fsys;
    private String m_filepath;
    private Set<String> m_functionExclusionList;
    public BytecastAmd64(IBytecastFsys m_fsys, String m_filepath) {
        this.m_fsys = m_fsys;
        this.m_filepath = m_filepath;
    }
    
  
  @Override
    public IExecutableFile buildInstructionObjects() {
      setFunctionsExclusionList();
      IExecutableFile exec;
      try{
           
            ExeObj fsysExec = doFsys();
            List<ISection> sections = parseAllSegments(fsysExec.getSegments(),getEntryPointIndex(fsysExec));
            exec = new AMD64ExecutableFile(fsysExec.getSegments(),sections, m_filepath, FileFormats.FF_ELF, null);
            
       }catch(Exception ex)
       {
           ex.printStackTrace();
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
       Hashtable<Long,ExeObjFunction> fnSymbolTable = new Hashtable<Long, ExeObjFunction>();
       for(ExeObjFunction fn : functions)
       {
           fnSymbolTable.put(fn.getStartAddress(), fn);
           System.out.println(fn.getName());
       }
       AMD64Dictionary.getInstance().setFunctionSymbolTable(fnSymbolTable);
    }

    /**
     * This method searches for the starting of main function in the entry point segment and uses a callgraph search (DFS) 
     * to find and process all user defined functions found in main's callgraph. Finally for all the userdefined functions
     * in the callgraph the instruction objects are constructed.
     * @param segments
     * @param entryPointIndex
     * @return
     * @throws BytecastAMD64Exception 
     */
    private List<ISection> parseAllSegments(List<ExeObjSegment> segments, int entryPointIndex) throws BytecastAMD64Exception {
        
        List<MemoryInstructionPair> instructions = new ArrayList<MemoryInstructionPair>();
        //Getting the entrypoint segment ".text"
        
        ExeObjSegment segment = segments.get(entryPointIndex);
        List<ISection> sections = new ArrayList<ISection>();  
        IInstructionLexer lexer = new AMD64InstructionLexer();
        ExeObjFunction mainFunction = AMD64Dictionary.getInstance().getFunctionByName("main");
        List<ExeObjFunction> dfsStack = new ArrayList<ExeObjFunction>();
        
        //START DFS on Main function callstack
        dfsStack.add(mainFunction);
        
        while(!dfsStack.isEmpty()){
            
            ExeObjFunction fn = dfsStack.get(dfsStack.size()-1);dfsStack.remove(dfsStack.size()-1);
            System.out.println("******"+fn.getName()+"******");
            List<Byte> bytes = getInsBytesOfFunction(fn, segment);
            
            FunctionCallListener fnl = new FunctionCallListener(m_functionExclusionList);
            lexer.registerFnCallListener(fnl);
            
            instructions.addAll(
              lexer.convertInstructionBytesToObjects(fn.getStartAddress(), bytes)
            );
            List<ExeObjFunction> children = fnl.getFunctions();
            for(int i =children.size()-1; i>=0;--i ){
                dfsStack.add(children.get(i));
            }
            
        }
        //END DFS on Main function callstack
        
        ISection section = new AMD64Section(instructions,mainFunction.getStartAddress(),true);
        sections.add(section);
        return sections;
    }

    
    private List<Byte> getInsBytesOfFunction(ExeObjFunction fn, ExeObjSegment segment){
         
        Long fnAddress = fn.getStartAddress();
        int fnsize = fn.getSize();
        
        Long secStartAddr = segment.getStartAddress();
        int offset = Math.abs((secStartAddr.intValue() - fnAddress.intValue())) ;
        List<Byte> bytes = segment.getBytes(offset, fnsize);
        
        return bytes;
    }

    private void setFunctionsExclusionList() {
        m_functionExclusionList = new HashSet<String>();
        m_functionExclusionList.add("printf");
    }

    private int getEntryPointIndex(ExeObj fsysExec) {
        List<ExeObjSegment> segments = fsysExec.getSegments();
        int i=0;
        for(ExeObjSegment s : segments){
            if(s.getStartAddress()==fsysExec.getEntryPointIndex())
                break;
            ++i;
        }
        return i;
    }
    
    
   class FunctionCallListener implements IFunctionCallListener{
        private final List<ExeObjFunction> m_functions;
        private final Set<String> m_functionsToExclude;

        public FunctionCallListener(Set<String> functionsToExclude) {
            this.m_functions = new ArrayList<ExeObjFunction>();
            this.m_functionsToExclude = functionsToExclude;
            
        }

        @Override
        public void collectFunctionCall(String fnName, Long fnAddress) {
            if(m_functionsToExclude!=null && m_functionsToExclude.contains(fnName))
                return;
            
            ExeObjFunction fn = AMD64Dictionary.getInstance().getFunctionByAddress(fnAddress);
            if(fn!=null)
            m_functions.add(fn);
        }

        /**
         * @return the list of functions
         */
        public List<ExeObjFunction> getFunctions() {
            return m_functions;
        }
        
    }
}
