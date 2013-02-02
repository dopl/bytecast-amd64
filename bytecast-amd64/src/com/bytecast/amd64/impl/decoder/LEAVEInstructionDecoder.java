/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.decoder;

import com.bytecast.amd64.api.constants.InstructionType;
import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.impl.instruction.AMD64Instruction;
import com.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author hapan
 */
public class LEAVEInstructionDecoder implements IInstructionDecoder {
  
  @Override
  public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instructionbytes) {
    if(instructionbytes.size() != 1) {
      throw new IllegalArgumentException("For LEAVE, the length of instruction should be 1 byte");
    }
    
    if(instructionbytes.get(0) != (byte)0xC9) {
      throw new IllegalArgumentException("For LEAVE, the assembly code should be 0xC9");
    }
    
    IInstruction ret = new AMD64Instruction(InstructionType.LEAVE);
    return ret;
  }
  
}
