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
package edu.syr.bytecast.amd64.impl.instruction;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import java.util.ArrayList;
import java.util.List;

public class AMD64Instruction implements IInstruction {

  private InstructionType instructionType;
  private String opCode;
  private List<IOperand> operands;
  private long instructionMemoryAddress;

  public AMD64Instruction(InstructionType instructionType) {
    this.instructionType = instructionType;
    operands = new ArrayList<IOperand>();
  }

  public AMD64Instruction(InstructionType instructionType,List<IOperand> operands) {
    this.instructionType = instructionType;
    this.operands = operands;
  }
  
  @Override
  public List<IOperand> getOperands() {
    return this.operands;
  }

  @Override
  public String getOpCode() {
    return this.opCode;
  }

  public void addOperand(IOperand op) {
    operands.add(op);
  }

  public void setOpCode(String opcode) {
    this.opCode = opcode;
  }

  @Override
  public InstructionType getInstructiontype() {
    return this.instructionType;
  }

  @Override
  public long getInstructionMemoryAddress() {
    return this.instructionMemoryAddress;
  }

  public void setInstructionMemoryAddress(long instructionMemoryAddress) {
    this.instructionMemoryAddress = instructionMemoryAddress;
  }
}
