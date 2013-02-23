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

import edu.syr.bytecast.amd64.util.DecoderUtil;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

public class ANDInstructionDecoder implements IInstructionDecoder{

  @Override
    public IInstruction decodeInstruction(Long instructionMemAddress, List<Byte> instructionbytes) {
        IInstruction instruction = new AMD64Instruction(InstructionType.AND);      
        
        decodeOperands(instruction, instructionbytes);
        
        return instruction;
    }
    
    // 48 83 e4 f0   add $0xfffffffffffffff0 %rsp
    // 83 : reg + imm64
    private void decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
        if(instructionbytes.size()!=4) {
            throw new UnsupportedOperationException("Not A correct ADD instructionbytes.");
        }
        
        if(instructionbytes.get(1) == 0x83){
           instruction.setOpCode("83");
                  
           if(DecoderUtil.getRegField(instructionbytes.get(2))==4){// verify is a and insturction
                //and constant value need add 1 in long value
               Long operand = instructionbytes.get(3).longValue() + 0xffffffffffffff00L;
               instruction.addOperand(new OperandConstant(operand));
               //add register
               instruction.addOperand(new OperandRegister(DecoderUtil.getRegister(DecoderUtil.getRmField(instructionbytes.get(2)))));
           }
        }
        
        //other and opcode to be implement
   
    }
    
}


//  ******old version for String type DecoderUtil 
// List<String> tempdecodes = DecoderUtil.DecodeHexToOctal(instructionbytes.get(2));
//           if(tempdecodes.get(1).equals("100")){ // verify is a and insturction
//               //and constant value need add 1 in long value
//               Long operand = instructionbytes.get(3).longValue() + 0xffffffffffffff00L;
//               instruction.addOperand(new OperandConstant(operand));
//               //add register
//               instruction.addOperand(new OperandRegister(DecoderUtil.CastRegister(tempdecodes.get(0))));        
//            }          
