/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.instruction.operand;

import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryLogicalAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;

/**
 *
 * @author hapan
 */
public class OperandMemoryLogicalAddress implements IOperand {

  private OperandTypeMemoryLogicalAddress logical_address;

  /**
   *
   */
  public OperandMemoryLogicalAddress(RegisterType segment, RegisterType base, RegisterType index, int scale, int offset) {
    this.logical_address = new OperandTypeMemoryLogicalAddress(segment, base, index, scale, offset);
  }

  public OperandMemoryLogicalAddress(RegisterType segment, OperandTypeMemoryEffectiveAddress omea) {
    this.logical_address = new OperandTypeMemoryLogicalAddress(segment, omea);
  }

  @Override
  public OperandType getOperandType() {
    return OperandType.MEMORY_LOGICAL_ADDRESS;
  }

  @Override
  public Object getOperandValue() {
    return this.logical_address;
  }
}
