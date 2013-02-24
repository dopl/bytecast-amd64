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
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import edu.syr.bytecast.amd64.util.DecoderUtil;
import java.util.List;

public class JCCInstructionDecoder implements IInstructionDecoder {
    
  @Override
  public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) {
      IInstruction ret = null;
//    int pos = 0;
//    byte key_byte = instructionbytes.get(pos).byteValue();
//    
//    // 0x67: address-size override prefix
//    if(key_byte == (byte)0x67) {
//      key_byte = instructionbytes.get(++pos).byteValue();
//    }
//    
//    if((byte)(key_byte & (byte)0xF0) == (byte)0x70) {
//      // 8 offset
//      ret = getInstruction((byte)(key_byte & 0x0F));
//    } else if(key_byte == (byte)0x0F) {
//      key_byte = instructionbytes.get(++pos).byteValue();
//      if((byte)(key_byte & (byte)0xF0) == (byte)0x80) {
//        // 16 offset or 32 offset
//        ret = getInstruction((byte)(key_byte & 0x0F));
//      } else {
//        throw new IllegalArgumentException("Incorrect form for JCC instruction");
//      }
//    } else {
//      throw new IllegalArgumentException("Incorrect form for JCC instruction");
//    }
//        
//    // need add the method to parse the operands
//    decodeOperands(ret, instructionMemAddress, instructionbytes);

    return ret;
  }
  
    
  private void decodeOperands(IInstruction instruction, Long instructionMemAddress, List<Byte> instructionbytes) {
    int pos = 0;
    byte tmp = instructionbytes.get(pos).byteValue();
    Long ltargetMemAddr = null;
    
    // 0x67: address-size override prefix
    if(tmp == (byte)0x67) {
      tmp = instructionbytes.get(++pos).byteValue();
    }
    
    if(tmp != (byte)0x0F) {
      // 8 offset JCC
      if(instructionbytes.size()-pos != 2) {
       throw new IllegalArgumentException("For 8 offset JCC, the length of instruction should be 2 bytes"); 
      }
      long targetMemAddr = instructionMemAddress.longValue() + instructionbytes.size() + instructionbytes.get(pos+1).byteValue();
      ltargetMemAddr = (long)targetMemAddr;
    } else {
      // 16 offset JCC or 32 offset JCC
      //throw new UnsupportedOperationException("Not support JCC that larger than 8 offset yet.");
      if(instructionbytes.size()-pos == 4) {
        Long offset = DecoderUtil.ByteConcatenator(instructionbytes, 2);
        long targetMemAddr = instructionMemAddress.longValue() + instructionbytes.size() + offset.shortValue();
        ltargetMemAddr = (long)targetMemAddr;
      } else if(instructionbytes.size()-pos == 6) {
        Long offset = DecoderUtil.ByteConcatenator(instructionbytes, 4);
        long targetMemAddr = instructionMemAddress.longValue() + instructionbytes.size() + offset.intValue();
        ltargetMemAddr = (long)targetMemAddr;
      } else {
        throw new IllegalArgumentException("For 16 or 32 offset JCC, the length of instruction should be either 4 or 6 bytes"); 
      }
    }
    OperandMemoryEffectiveAddress operandMemAddr = new OperandMemoryEffectiveAddress(ltargetMemAddr); 
    //instruction.addOperand(operandMemAddr);
  }
  
  private IInstruction getInstruction(byte tmp) {
    switch(tmp) {
      case 0x00:
        return new AMD64Instruction(InstructionType.JO);
      case 0x01:
        return new AMD64Instruction(InstructionType.JNO);
      case 0x02:
        return new AMD64Instruction(InstructionType.JB);
      case 0x03:
        return new AMD64Instruction(InstructionType.JAE);
      case 0x04:
        return new AMD64Instruction(InstructionType.JE);
      case 0x05:
        return new AMD64Instruction(InstructionType.JNE);
      case 0x06:
        return new AMD64Instruction(InstructionType.JBE);
      case 0x07:
        return new AMD64Instruction(InstructionType.JA);
      case 0x08:
        return new AMD64Instruction(InstructionType.JS);
      case 0x09:
        return new AMD64Instruction(InstructionType.JNS);
      case 0x0A:
        return new AMD64Instruction(InstructionType.JP);
      case 0x0B:
        return new AMD64Instruction(InstructionType.JNP);
      case 0x0C:
        return new AMD64Instruction(InstructionType.JL);
      case 0x0D:
        return new AMD64Instruction(InstructionType.JGE);
      case 0x0E:
        return new AMD64Instruction(InstructionType.JLE);
      case 0x0F:
        return new AMD64Instruction(InstructionType.JG);
      default:
        return null;
    }
  }
    
}
