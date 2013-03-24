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
import edu.syr.bytecast.amd64.impl.parser.IImmParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

public class JMPInstructionDecoder implements IInstructionDecoder {

  @Override
  public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {

    AMD64Instruction ret = new AMD64Instruction(InstructionType.JMP);
    IImmParser imm_parser = ParserFactory.getImmParser();
    long curr_addr = input.getInstructionAddress();
    long addr;

    byte b = input.read();
    if (b == (byte) 0xEB) {
      // Description: Short jump with the target specified by an 8-bit signed displacement.
      // Mnemonic:    JMP rel8off
      // Opcode:      EB
      ret.setOpCode("EB");
      imm_parser.parse(input, IImmParser.Type.IMM8);
      addr = imm_parser.getNumber() + curr_addr + 2;
      OperandMemoryEffectiveAddress operandMemAddr = new OperandMemoryEffectiveAddress(null, null, 1, addr);
      ret.addOperand(operandMemAddr);
      return ret;
    } else if (b == (byte) 0xE9) {
      // In 64-bit mode, default size in opcode E9 is set to 32 bits
      if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
        // Description: Near jump with the target specified by a 16-bit signed displacement.
        // Mnemonic:    JMP rel16off
        // Opcode:      E9
        ret.setOpCode("E9");
        imm_parser.parse(input, IImmParser.Type.IMM16);
        addr = imm_parser.getNumber() + curr_addr + 3;
        OperandMemoryEffectiveAddress operandMemAddr = new OperandMemoryEffectiveAddress(null, null, 1, addr);
        ret.addOperand(operandMemAddr);
        return ret;
      } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
        // Description: Near jump with the target specified by a 32-bit signed displacement.
        // Mnemonic:    JMP rel32off
        // Opcode:      E9
        ret.setOpCode("E9");
        imm_parser.parse(input, IImmParser.Type.IMM32);
        addr = imm_parser.getNumber() + curr_addr + 5;
        OperandMemoryEffectiveAddress operandMemAddr = new OperandMemoryEffectiveAddress(null, null, 1, addr);
        ret.addOperand(operandMemAddr);
        return ret;
      }

    } else if (b == (byte) 0xFF) {
      // In 64-bit mode, default size in opcode FF /4 is set to 64 bits
      if(context.getOperandSize() != IInstructionContext.OperandOrAddressSize.SIZE_16) {
        context.setOperandSize(IInstructionContext.OperandOrAddressSize.SIZE_64);
      }
      
      b = input.peek(); // the R/M field of this opcode byte is needed, so use peek
      if ((byte)(b & (byte) 0x38) != (byte)0x20) {
        throw new RuntimeException("Incorrect form for JMP instruction");
      }
      ret.setOpCode("FF /4");
      IModRmParser rm_parser = ParserFactory.getModRmParser();
      if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
        // Description: Near jump with the target specified reg/mem16.
        // Mnemonic:    JMP reg/mem16
        // Opcode:      FF /4
        rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM16);
        ret.addOperand(rm_parser.getRmOperand());
        return ret;
      } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
        // No prefix is available to encode a 32-bit operand size in 64-bit mode.
        // Description: Near jump with the target specified reg/mem32.
        // Mnemonic:    JMP reg/mem32
        // Opcode:      FF /4
        rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM32);
        ret.addOperand(rm_parser.getRmOperand());
        return ret;
      } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
        // Description: Near jump with the target specified reg/mem64.
        // Mnemonic:    JMP reg/mem64
        // Opcode:      FF /4
        rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM64);
        ret.addOperand(rm_parser.getRmOperand());
        return ret;
      }

    } else {
      throw new RuntimeException("Incorrect form for JMP instruction");
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
