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

package edu.syr.bytecast.amd64.impl.parserdemo;

/**
 * An interface to store context information of a current parsed instruction.
 *
 * @author sheng
 */
public interface IInstructionContext {

    public static enum OperandOrAddressSize {

        SIZE_16, SIZE_32, SIZE_64
    }

    OperandOrAddressSize getAddressSize();

    OperandOrAddressSize getOperandSize();

    void setAddressSize(OperandOrAddressSize address_size);

    void setOperandSize(OperandOrAddressSize operand_size);

    /**
     * Return true if the REX.B bit is 1.
     *
     * @return
     */
    boolean isRexB();

    /**
     * Return true if the REX.R bit is 1.
     *
     * @return
     */
    boolean isRexR();

    /**
     * Return true if the REX.X bit is 1.
     *
     * @return
     */
    boolean isRexX();

    void setRexB(boolean rex_b);

    void setRexR(boolean rex_r);

    void setRexX(boolean rex_x);
}
