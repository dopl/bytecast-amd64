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
public class NumberParserImpl implements INumberParser {

    private long m_number;
    private OperandConstant m_constant;

    @Override
    public void parse(IByteInstructionInputStream input, NumberType type) throws EOFException {
        int size;
        switch (type) {
            case SIZE8:
                size = 1;
                break;
            case SIZE16:
                size = 2;
                break;
            case SIZE32:
                size = 4;
                break;
            case SIZE64:
                size = 8;
                break;
            default:
                throw new RuntimeException("Unknown type.");
        }
        for (int i = 0; i < size; i++) {
            m_number = m_number << 8;
            byte b = input.read();
            m_number += b & 0xFF;
        }
        m_constant = new OperandConstant(m_number);
    }

    @Override
    public OperandConstant getOperand() {
        return m_constant;
    }
}
