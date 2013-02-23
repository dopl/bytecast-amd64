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

import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import java.io.EOFException;

/**
 * A class to parse numbers such as "Immediate Bytes", "Displacement Bytes".
 *
 * @author sheng
 */
public interface INumberParser {

    public static enum NumberType {

        SIZE8, SIZE16, SIZE32, SIZE64
    }

    /**
     * Return an OperandConstant which contains the parsed number.
     *
     * @return
     */
    // TODO We should return different type of IOperand according to byte types
    // such as "Immediate Bytes", "Displacement Bytes".
    OperandConstant getOperand();

    /**
     *
     * @param input
     * @param type
     * @return
     * @throws EOFException
     */
    void parse(IByteInstructionInputStream input, NumberType type) throws EOFException;
}
