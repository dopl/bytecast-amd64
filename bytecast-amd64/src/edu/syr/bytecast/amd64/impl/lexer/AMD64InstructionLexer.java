/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.lexer;

import edu.syr.bytecast.amd64.internal.api.parser.IInstructionLexer;
import edu.syr.bytecast.fsys.ExeObjSegment;
import java.util.List;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.dictionary.AMD64Dictionary;
import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;
import java.util.ArrayList;

/**
 *
 * @author Harsh
 */
public class AMD64InstructionLexer implements IInstructionLexer {

    private IAMD64Dictionary dictionary;
    public AMD64InstructionLexer() {
        dictionary =  AMD64Dictionary.getInstance();
    }

    @Override
    public List<IInstruction> convertInstructionBytesToObjects(Long sectionStartMemeAddress,List<Byte> bytes ) {
        Long memoryAddress = sectionStartMemeAddress;
        List<Byte> oneinstruction = new ArrayList<Byte>();
        boolean end=false;
        Byte previousByte=null;
        
        for(Byte b : bytes)
        {
            ++memoryAddress;
            
            if(end)
                oneinstruction = new ArrayList<Byte>();
            
            if(dictionary.isLegacyOpcode(b))
            {
                //Legacy opcode repeats are equivalent to one instance
                if(b==previousByte)
                    continue;
            }
            
            //REX prefixes
            if(b>= 0x40 && b<= 0x4f)
            {
                String bin = Integer.toBinaryString(b);
                oneinstruction.add(b);
            }
            previousByte=b;
        }
        return null;
        
        
    }

}