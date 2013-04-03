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
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionLexer;
import java.util.List;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.impl.decoder.DecoderFactory;
import edu.syr.bytecast.amd64.impl.dictionary.AMD64Dictionary;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.parser.ILegacyPrefixParser;
import edu.syr.bytecast.amd64.impl.parser.IRexPrefixParser;
import edu.syr.bytecast.amd64.impl.parser.InstructionByteListInputStream;
import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;
import edu.syr.bytecast.amd64.internal.api.parser.IFunctionCallListener;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import edu.syr.bytecast.common.impl.exception.BytecastAMD64Exception;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AMD64InstructionLexer implements IInstructionLexer {

    private IAMD64Dictionary m_dictionary;
    private Set<String> m_excludedFunctions;
    private IFunctionCallListener m_FunctionCallListener;
    
    public AMD64InstructionLexer() {
        m_dictionary =  AMD64Dictionary.getInstance();
    }

    @Override
    public List<MemoryInstructionPair> convertInstructionBytesToObjects(Long sectionStartMemeAddress,List<Byte> bytes ) {
        
        List<MemoryInstructionPair> memToInstrMap = new ArrayList<MemoryInstructionPair>();
        InstructionByteListInputStream istream = new InstructionByteListInputStream(bytes, sectionStartMemeAddress);
        IInstructionContext ctx=null ;
        
        boolean createNewCtx=true;
        try {
            while(istream.available()>0)
            {
                if(createNewCtx){
                   ctx = new InstructionContextImpl();
                   createNewCtx = false;
                   istream.updateInstructionAddress();
                }
               boolean foundInstructionOpCOde = processInstructionByte(ctx,istream);
               if(foundInstructionOpCOde)
               {
                    try {
                       InstructionType insType = getInstructionType(ctx,istream.peek());
                       IInstructionDecoder instructionDecoder = DecoderFactory.getInstructionDecoder(insType);
                       IInstruction instruction = instructionDecoder.decode(ctx, istream);
                       MemoryInstructionPair pair = new MemoryInstructionPair(istream.getInstructionAddress(), instruction);
                       memToInstrMap.add(pair);
                       createNewCtx = true;
                       if(instruction.getInstructiontype()==InstructionType.CALLQ && m_FunctionCallListener!=null){
                            notifyFunctionCall( instruction);
                       }
                               
                    } catch (BytecastAMD64Exception ex) {
                        Logger.getLogger(AMD64InstructionLexer.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
            }
        } catch (EOFException ex) {
                Logger.getLogger(AMD64InstructionLexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memToInstrMap;
        
        
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
        }else if(dictionary.isEscapeToSecondaryOpCode(b)){
            ctx.setOpcodeMapForInstruction(IInstructionContext.OpcodeMap.OCM_SECONDARY);
            return false;
        }else{
            //If the control reaches here, the byte must be a an instruction opcode
            return true;
        }
      
    }

    private InstructionType getInstructionType(IInstructionContext ctx, byte b) throws BytecastAMD64Exception {
        switch(ctx.getOpcodeMapForInstruction()){
            case OCM_PRIMARY: return m_dictionary.getInstructionFromPrimaryOCTable(b);
            case OCM_SECONDARY: return m_dictionary.getInstructionFromSecondaryOCTable(b);
            default: throw new BytecastAMD64Exception("Opcode map implementation not found");
        }
    }
    
    
    public static void main(String a[]){
        Long sectionStartMemeAddress;
        List<Byte> bytes;
        List<MemoryInstructionPair> expResult ;
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
        
        AMD64InstructionLexer instance = new AMD64InstructionLexer();
        List<MemoryInstructionPair> result = instance.convertInstructionBytesToObjects(sectionStartMemeAddress, bytes);
    }

    @Override
    public void setFunctionsToBeExcluded(Set<String> excludedFunctions) {
        m_excludedFunctions = excludedFunctions;
    }

    @Override
    public void registerFnCallListener(IFunctionCallListener fnCallListener) {
      m_FunctionCallListener = fnCallListener;
              
    }

    private void notifyFunctionCall(IInstruction instruction) {
        String fnName="";
        Long fnAddr=0l;
        List<IOperand> operands = instruction.getOperands();
         for(IOperand op: operands){
             if(op.getOperandType()==OperandType.SECTION_NAME)
                 fnName = (String)op.getOperandValue();
             else if(op.getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS)
                 fnAddr = (Long)((OperandTypeMemoryEffectiveAddress)op.getOperandValue()).getOffset();
         }
        m_FunctionCallListener.collectFunctionCall(fnName, fnAddr);
    }

}