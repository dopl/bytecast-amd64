/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

/**
 *
 * @author hapan
 */
public class LEAInstructionDecoder implements IInstructionDecoder {

  @Override
  public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
    AMD64Instruction ret = new AMD64Instruction(InstructionType.LEA);
    ret.setOpCode("8D");

    // get the opcode
    byte b = input.read();
    if (b != (byte) 0x8D) {
      throw new RuntimeException("Incorrect opcode for LEA instruction");
    }

    if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
      // Description: store effective address in 16-bit register
      // Opcode:      8D /r
      // Mnemonic:    LEA mem, reg16
      IModRmParser rm_parser = ParserFactory.getModRmParser();
      rm_parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
      ret.addOperand(rm_parser.getRmOperand());
      ret.addOperand(rm_parser.getRegOperand());
      return ret;
    } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
      // Description: store effective address in 32-bit register
      // Opcode:      8D /r
      // Mnemonic:    LEA mem, reg32
      IModRmParser rm_parser = ParserFactory.getModRmParser();
      rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
      ret.addOperand(rm_parser.getRmOperand());
      ret.addOperand(rm_parser.getRegOperand());
      return ret;
    } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
      // Description: store effective address in 64-bit register
      // Opcode:      8D /r
      // Mnemonic:    LEA mem, reg64
      IModRmParser rm_parser = ParserFactory.getModRmParser();
      rm_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
      ret.addOperand(rm_parser.getRmOperand());
      ret.addOperand(rm_parser.getRegOperand());
      return ret;
    } else {
      throw new RuntimeException("Unknown operand size for LEA instruction");
    }

  }
}
