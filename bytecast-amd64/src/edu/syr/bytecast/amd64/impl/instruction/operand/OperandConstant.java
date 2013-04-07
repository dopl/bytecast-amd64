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
import edu.syr.bytecast.amd64.api.instruction.IOperand;

public class OperandConstant implements IOperand  {
    
    private Long constant;
    
    public OperandConstant(Long constant)
    {
        this.constant = constant;
    }
    
    @Override
    public OperandType getOperandType() {
        return OperandType.CONSTANT;
    }

    @Override
    public Object getOperandValue() {
        return this.constant;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.constant != null ? this.constant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OperandConstant other = (OperandConstant) obj;
        if (this.constant != other.constant && (this.constant == null || !this.constant.equals(other.constant))) {
            return false;
        }
        return true;
    }
    
}
