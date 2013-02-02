/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.decoder;

import com.bytecast.amd64.api.constants.InstructionType;
import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.impl.instruction.AMD64Instruction;
import com.bytecast.amd64.impl.instruction.operand.OperandMemoryAddress;
import com.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author hapan
 */
public class JMPInstructionDecoder implements IInstructionDecoder {

  @Override
  public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instructionbytes) {
    
    IInstruction ret = new AMD64Instruction(InstructionType.JMP);
    byte tmp = instructionbytes.get(0).byteValue();
    
    if((tmp & 0xF0) == 0xE0) {
      // jump to offset address
      throw new UnsupportedOperationException("Not support JMP that jumps to anys offset yet.");
    } else if(tmp == (byte)0xFF) {
      // jump to reg or memory address
      tmp = instructionbytes.get(1).byteValue();
      tmp = (byte)(tmp >> 3 & 0x07);
      if(tmp != 4) {
        throw new IllegalArgumentException("For JMP in ModRM mode, the reg field should be 4"); 
      }
      if(instructionbytes.size() == 2) {
        // analyze the register
        
      } else {
        // analyze the memory address
        long memAddr = 0;
        for(int i = instructionbytes.size()-1; i>1; i--) {
          memAddr = memAddr << 8;
          memAddr += instructionbytes.get(i); 
        }
        OperandMemoryAddress operandMemAddr = new OperandMemoryAddress(memAddr);
        ret.addOperand(operandMemAddr);
      }
      
    } else {
      throw new IllegalArgumentException("For JMP, the assembly code is incorrect"); 
    }
    
    return ret;
  }
  
}
