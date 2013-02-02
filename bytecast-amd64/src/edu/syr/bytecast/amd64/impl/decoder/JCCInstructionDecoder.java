/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryAddress;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;
/**
 *
 * @author hapan
 */
public class JCCInstructionDecoder implements IInstructionDecoder {
    
  private void decodeOperands(IInstruction instruction, Long sectionStartMemAddr, List<Byte> instructionbytes) {
    byte tmp = instructionbytes.get(0).byteValue();
    if(tmp != 0x0F) {
      // 8 offset JCC
      if(instructionbytes.size() != 2) {
       throw new IllegalArgumentException("For 8 offset JCC, the length of instruction should be 2 bytes"); 
      }
      
      Long targetMemAddr = sectionStartMemAddr + instructionbytes.size() + instructionbytes.get(1);
      OperandMemoryAddress operandMemAddr = new OperandMemoryAddress(targetMemAddr); 
      instruction.addOperand(operandMemAddr);
    } else {
      // 16 offset JCC or larger
      throw new UnsupportedOperationException("Not support JCC that larger than 8 offset yet.");
    }
  }
    
  @Override
  public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instructionbytes) {
    IInstruction ret = null;
    byte tmp = instructionbytes.get(0).byteValue();
    if((tmp & 0xF0) == 0x70) {
      switch(tmp & 0x0F) {
        case 0x02:
          ret = new AMD64Instruction(InstructionType.JB);
          break;
        case 0x03:
          ret = new AMD64Instruction(InstructionType.JAE);
          break;
        case 0x04:
          ret = new AMD64Instruction(InstructionType.JE);
          break;
        case 0x05:
          ret = new AMD64Instruction(InstructionType.JNE);
          break;
        default:
          break;
      }
    } else if(tmp == 0x0F) {
      tmp = instructionbytes.get(1).byteValue();
      if((tmp & 0xF0) == 0x80) {
        switch(tmp & 0x0F) {
          case 0x02:
            ret = new AMD64Instruction(InstructionType.JB);
            break;
          case 0x03:
            ret = new AMD64Instruction(InstructionType.JAE);
            break;
          case 0x04:
            ret = new AMD64Instruction(InstructionType.JE);
            break;
          case 0x05:
            ret = new AMD64Instruction(InstructionType.JNE);
            break;
          default:
            break;
        }
      }
    }
    // need add the method to parse the operands
    decodeOperands(ret, sectionStartMemAddr, instructionbytes);

    return ret;
  }
    
}
