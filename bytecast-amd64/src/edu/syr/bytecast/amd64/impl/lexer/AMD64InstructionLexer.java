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
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionLexer;
import java.util.List;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.impl.dictionary.AMD64Dictionary;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import edu.syr.bytecast.amd64.impl.parser.ILegacyPrefixParser;
import edu.syr.bytecast.amd64.impl.parser.IRexPrefixParser;
import edu.syr.bytecast.amd64.impl.parser.InstructionByteListInputStream;
import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AMD64InstructionLexer implements IInstructionLexer {

    private IAMD64Dictionary dictionary;
    public AMD64InstructionLexer() {
        dictionary =  AMD64Dictionary.getInstance();
    }

    @Override
    public List<MemoryInstructionPair> convertInstructionBytesToObjects(Long sectionStartMemeAddress,List<Byte> bytes ) {
        Long memoryAddress = sectionStartMemeAddress;
        List<MemoryInstructionPair> memToInstrMap = new ArrayList<MemoryInstructionPair>();
        InstructionByteListInputStream istream = new InstructionByteListInputStream(bytes, sectionStartMemeAddress);
        IInstructionContext ctx=null ;
        
        boolean createNewCtx=false;
        try {
            while(istream.available()>=0)
            {
                if(createNewCtx)
                   ctx = new InstructionContextImpl();
                
               boolean foundInstructionOpCOde = processInstructionByte(ctx,istream);
               if(foundInstructionOpCOde)
               {
                   InstructionType insType = getInstructionType(istream.peek());
               }
                    

            }
        } catch (EOFException ex) {
                Logger.getLogger(AMD64InstructionLexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
        
    }
    
    private boolean processInstructionByte(IInstructionContext ctx,InstructionByteListInputStream istream) throws EOFException
    {
        AMD64Dictionary dictionary = AMD64Dictionary.getInstance();
        byte b = istream.peek();
        
        if(dictionary.isLegacyOpcode(b)) {
            ILegacyPrefixParser legacyPrefixParser = ParserFactory.getLegacyPrefixParser();
            legacyPrefixParser.parse(ctx, istream);
            return false;
        }
        else if(dictionary.isRexPrefix(b)) {
            IRexPrefixParser rexPrefixParser = ParserFactory.getRexPrefixParser();
            rexPrefixParser.parse(ctx, istream);
            return false;
        }
        return false;
    }

    private InstructionType getInstructionType(byte b) {
        return null;
    }

}