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
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

public class LEAVEInstructionDecoder implements IInstructionDecoder {
  
  @Override
  public IInstruction decodeInstruction(Long instructionMemAddress, List<Byte> instructionbytes) {
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
