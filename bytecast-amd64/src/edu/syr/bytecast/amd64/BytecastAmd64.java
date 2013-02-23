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

package edu.syr.bytecast.amd64;

import edu.syr.bytecast.amd64.util.DecoderUtil;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.decoder.DecoderFactory;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.decoder.JCCInstructionDecoder;
import edu.syr.bytecast.amd64.impl.decoder.JMPInstructionDecoder;
import edu.syr.bytecast.amd64.impl.decoder.LEAVEInstructionDecoder;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.ArrayList;
import java.util.List;

public class BytecastAmd64 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here

    // initialize veriables 
    Long instructionMemAddr = (long)0x4004a4;
    List<Byte> instructionbytes = new ArrayList<Byte>();
    
    // test JCCInstrcutionDecoder
    JCCInstructionDecoder jccInstructionDecoder = new JCCInstructionDecoder();
    instructionbytes.clear();
    instructionbytes.add((byte)0x75);
    instructionbytes.add((byte)0xe2);
    IInstruction jccInstruction = jccInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // test LEAVEInstructionDecoder
    LEAVEInstructionDecoder leaveInstructionDecoder = new LEAVEInstructionDecoder();
    instructionbytes.clear();
    instructionbytes.add((byte)0xC9);
    IInstruction leaveInstruction = leaveInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // test JMPInstructionDecoder
    JMPInstructionDecoder jmpInstructionDecoder = new JMPInstructionDecoder();
    // jump to register
    instructionbytes.clear();
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0xE0);
    IInstruction jmpInstruction = jmpInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // jump to memory address
    instructionbytes.clear();
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0x25);
    instructionbytes.add((byte)0xA4);
    instructionbytes.add((byte)0x04);
    instructionbytes.add((byte)0x20);
    instructionbytes.add((byte)0x00);
    jmpInstruction = jmpInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
  }

  public BytecastAmd64()
  {

    
  }
    
    
 
    
}