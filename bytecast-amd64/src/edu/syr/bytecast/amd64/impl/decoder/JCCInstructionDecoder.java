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
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

public class JCCInstructionDecoder implements IInstructionDecoder {

  @Override
  public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
    AMD64Instruction ret = null;

    // may need to set the instruction memory address
    IImmParser imm_parser = ParserFactory.getImmParser();
    long curr_addr = input.getInstructionAddress();
    long addr;  // store the calculated address

    byte b = input.read();
    if ((b & (byte) 0xF0) == (byte) 0x70) {
      // 8 offset
      ret = getInstruction((byte) (b & (byte) 0x0F));
      ret.setOpCode(String.format("%x", b));
      imm_parser.parse(input, IImmParser.Type.IMM8);
      addr = imm_parser.getNumber() + curr_addr + 2;
    } else if (b == (byte) 0x0F) {
      if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
        // 16 offset
        b = input.read();
        if ((b & (byte) 0xF0) != (byte) 0x80) {
          throw new RuntimeException("Incorrect form for JCC instruction");
        }
        ret = getInstruction((byte) (b & (byte) 0x0F));
        ret.setOpCode("0F " + String.format("%x", b));
        imm_parser.parse(input, IImmParser.Type.IMM16);
        addr = imm_parser.getNumber() + curr_addr + 4;
      } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
        // 32 offset
        b = input.read();
        if ((b & (byte) 0xF0) != (byte) 0x80) {
          throw new RuntimeException("Incorrect form for JCC instruction");
        }
        ret = getInstruction((byte) (b & (byte) 0x0F));
        ret.setOpCode("0F " + String.format("%x", b));
        imm_parser.parse(input, IImmParser.Type.IMM32);
        addr = imm_parser.getNumber() + curr_addr + 6;
      } else {
        throw new RuntimeException("Incorrect form for JCC instruction");
      }

    } else {
      throw new RuntimeException("Incorrect form for JCC instruction");
    }

    OperandMemoryEffectiveAddress operandMemAddr = new OperandMemoryEffectiveAddress(null, null, 1, addr);
    ret.addOperand(operandMemAddr);
    return ret;
  }

  private AMD64Instruction getInstruction(byte tmp) {
    switch (tmp) {
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
