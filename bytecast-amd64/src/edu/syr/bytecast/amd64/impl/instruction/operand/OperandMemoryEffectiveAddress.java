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
package edu.syr.bytecast.amd64.impl.instruction.operand;

import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;

/**
 *
 * @author hapan
 */
public class OperandMemoryEffectiveAddress implements IOperand {

  private OperandTypeMemoryEffectiveAddress effective_address;

  /**
   * TODO: would need to add the effective_address class in api
   */
  public OperandMemoryEffectiveAddress(RegisterType base, RegisterType index, int scale, long offset) {

    this.effective_address = new OperandTypeMemoryEffectiveAddress(base, index, scale, offset);
  }

  @Override
  public OperandType getOperandType() {
    return OperandType.MEMORY_EFFECITVE_ADDRESS;
  }

  @Override
  public Object getOperandValue() {
    return this.effective_address;
  }
}
