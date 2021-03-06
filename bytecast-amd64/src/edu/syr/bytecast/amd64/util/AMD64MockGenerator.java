/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.util;

import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionMutator;
import edu.syr.bytecast.amd64.impl.output.AMD64ExecutableFile;
import edu.syr.bytecast.amd64.impl.output.AMD64Section;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import edu.syr.bytecast.test.mockups.MockBytecastFsys;
import edu.syr.bytecast.util.Paths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.Size2DSyntax;

/**
 *
 * @author bytecast
 */
public class AMD64MockGenerator {
    private final IBytecastFsys fsys;
    
    private String objdumpFile;
    private final String startmethod;
    private HashMap<String, List<String>> sections;
    private final Set<String> fnexclusionlist;

    public AMD64MockGenerator(IBytecastFsys fsys, String objdumpFile,String startmethod,Set<String> fnexclusionlist) {
        this.fsys = fsys;
        this.objdumpFile = objdumpFile;
        this.startmethod = startmethod;
        this.fnexclusionlist = fnexclusionlist;
    }
    
    public IBytecastAMD64 generate() throws FileNotFoundException, IOException, Exception{
        collectSections(); 
        List<MemoryInstructionPair> ins = getDFSOrderInstructions();
        List<ISection> sections = new ArrayList<ISection>();
        ISection sec = new AMD64Section(ins, ins.get(0).getmInstructionAddress(), true);
        sections.add(sec);
        final IExecutableFile ex = new AMD64ExecutableFile(fsys.parse().getSegments(), sections, objdumpFile, "ELF", null);
        IBytecastAMD64 amd64 = new IBytecastAMD64() {

            @Override
            public IExecutableFile buildInstructionObjects() {
               return ex;
            }
        };
        return amd64;
    }

    private void collectSections() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(new File(objdumpFile));
        BufferedReader br = new BufferedReader(fr);
        String line="";
        sections = new HashMap<String,List<String>>();
        List<String> list = null;
        boolean on=false;
        while((line =br.readLine()) !=null)
        {
            if(line.equals("Disassembly of section .text:")){
                on=true;
                continue;
            }
            if(!on || line.contains("\tnop"))
                continue;
            
            if(line.contains("Disassembly of section"))
                    break;
                
            if(line.startsWith("00") )
            {
              list = new ArrayList<String>();
              sections.put(line.split(" ")[1].split(":")[0], list);
              continue;
            }
            if((list!=null && line.startsWith("  "))) {
                list.add(line);
            }
        }
        br.close();
        fr.close();
    }

    private List<MemoryInstructionPair> getDFSOrderInstructions() {
        List<MemoryInstructionPair> ins = new ArrayList<MemoryInstructionPair>();
        List<String> dfsqueue = new ArrayList<String>();
        dfsqueue.add(startmethod);
        
        while(!dfsqueue.isEmpty()){
            String fn = dfsqueue.remove(dfsqueue.size()-1);
            //System.out.println(fn);
            List<String> children = new ArrayList<String>();
            List<String> instructions = sections.get(fn);
            for(String in : instructions){
                if(in.contains("callq")){
                    String s[] = in.split(" ");
                    String fnname = s[s.length-1];
                    if(!fnexclusionlist.contains(fnname)) {
                        children.add(fnname);
                    }
                }
                ins.add(getStringToInstruction(in));
            }
            for(int i= children.size()-1; i>=0;--i) {
                dfsqueue.add(children.get(i));
            }
        }
        
        return ins;
    }

    private MemoryInstructionPair getStringToInstruction(String in) {
        
        Long mem = Long.parseLong(in.substring(0,in.indexOf(":")).trim(),16);
        String s[] = in.split("\t");
        StringToIInstruction util = new StringToIInstruction();
        List<Byte> bytes = getInstructionBytes(s[1]);
        
        IInstructionMutator ins =(IInstructionMutator) util.convert(s[s.length-1]);
        for(Byte b: bytes){
            ins.addByte(b);
        }
        return new MemoryInstructionPair(mem, ins); 
   }
    
    
    public static void main(String a[]) throws Exception{
        Set<String> exclusion = new HashSet<String>();
         Paths.v().setRoot("/home/bytecast/code/bytecast");                  
        try {
            Paths.v().parsePathsFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        exclusion.add("<_IO_printf>");
        AMD64MockGenerator gen = 
                new AMD64MockGenerator(new MockBytecastFsys(),
                "/home/bytecast/code/bytecast/bytecast-documents/AsciiManip01Prototype/a.out.static.objdump",
                "<main>",exclusion);
        try {
            IExecutableFile ex = gen.generate().buildInstructionObjects();
            
        } catch (FileNotFoundException ex1) {
            Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (IOException ex1) {
            Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    private List<Byte> getInstructionBytes(String bytesstr) {
        bytesstr = bytesstr.trim();
        List<Byte> bytes = new ArrayList<Byte>();
        String split[] = bytesstr.split(" ");
        for(int i=0;i<split.length;++i){
            int itngr = Integer.parseInt(split[i], 16);
            bytes.add((byte)itngr);
        }
        return bytes;
    }

    
    
}
