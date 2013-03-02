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

package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IAddressFunctionParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import edu.syr.bytecast.amd64.util.DecoderUtil;
import java.io.EOFException;
import java.util.List;

public class CALLQInstructionDecoder implements IInstructionDecoder{

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {    
        //_instructionMemAddress = instructionMemAddress;
        //decodeOperands(instruction, instructionbytes);
        AMD64Instruction ret = new AMD64Instruction(InstructionType.CALLQ);
        byte b = input.read();
        
        if(b == (byte) 0xe8){            
            ret.setOpCode("e8");
            IAddressFunctionParser iaf_parser = ParserFactory.getAddressFunctionParser();
            iaf_parser.parse(context, input);
            ret.addOperand(iaf_parser.getAddressOperand());
            ret.addOperand(iaf_parser.getSectionNameOperand());
            return ret;
        } else if (b == (byte) 0xff) {
            
        } else if(b == (byte) 0x41){
            
        } 
        
        
        throw new UnsupportedOperationException("TODO");
    }
    
    private long _instructionMemAddress;
    
    // 48 83 c0 08   add $0x8 %rsp
    // 83 : reg + imm8
//    private void decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
//       
//        int offset = instructionbytes.size();
//        if(((instructionbytes.get(0)>>4) & 0x07)==4){ // check is prefix or not
//              
//        }
//        else{
//              if(instructionbytes.get(0) == 0xe8){
//                instruction.setOpCode("e8");
//                long a = DecoderUtil.ByteConcatenator(instructionbytes,offset-1);
//                offset = offset + (int)a;
//                long operand = _instructionMemAddress + offset; 
//                 instruction.addOperand(new OperandConstant(operand));
//                }
//              else if(instructionbytes.get(0) == 0xff){
//                 instruction.setOpCode("ff");
//              }
//         }  
//        //other callq opcode to be implement 
//    }
    
    private long AnalOperand(long oldoperand) {
       long ret = 0;
       
       return ret;
    }    
}
