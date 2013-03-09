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
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

public class JMPInstructionDecoder implements IInstructionDecoder {

  @Override
  public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {

    IInstruction ret = new AMD64Instruction(InstructionType.JMP);

    byte b = input.read();
    if (b == (byte) 0xEB) {
      // JMP rel8off
      // TODO
    } else if (b == (byte) 0xE9) {
      // JMP rel16off or rel32off
      // TODO
    } else if (b == (byte) 0xFF) {
      b = input.read();
      if((b & (byte)0x38) == 4) {
        // JMP
        //TODO
      }

    }

    return ret;



//    byte tmp = instructionbytes.get(0).byteValue();
//    
//    if((tmp & 0xF0) == 0xE0) {
//      // jump to offset address
//      throw new UnsupportedOperationException("Not support JMP that jumps to anys offset yet.");
//    } else if(tmp == (byte)0xFF) {
//      // jump to reg or memory address
//      tmp = instructionbytes.get(1).byteValue();
//      tmp = (byte)(tmp >> 3 & 0x07);
//      if(tmp != 4) {
//        throw new IllegalArgumentException("For JMP in ModRM mode, the reg field should be 4"); 
//      }
//      if(instructionbytes.size() == 2) {
//        // analyze the register
//        
//      } else {
//        // analyze the memory address
//        long memAddr = 0;
//        for(int i = instructionbytes.size()-1; i>1; i--) {
//          memAddr = memAddr << 8;
//          memAddr += (0x00000000000000FFL & instructionbytes.get(i)); 
//        }
//        OperandMemoryAddress operandMemAddr = new OperandMemoryAddress(memAddr);
//        ret.addOperand(operandMemAddr);
//      }
//      
//    } else {
//      throw new IllegalArgumentException("For JMP, the assembly code is incorrect"); 
//    }
//    

  }
}
