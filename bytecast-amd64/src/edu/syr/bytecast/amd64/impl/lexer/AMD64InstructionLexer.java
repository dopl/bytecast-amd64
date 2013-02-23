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

package edu.syr.bytecast.amd64.impl.lexer;

import edu.syr.bytecast.amd64.internal.api.parser.IInstructionLexer;
import java.util.List;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.dictionary.AMD64Dictionary;
import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;
import java.util.ArrayList;

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