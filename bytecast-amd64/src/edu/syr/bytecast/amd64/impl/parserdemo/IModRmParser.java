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

import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import java.io.EOFException;

/**
 * An interface to parse the ModRM bytes. It will also parse SIB bytes if the
 * ModRM bytes need them.
 *
 * @author sheng
 */
public interface IModRmParser {

    public static enum RegType {

        NONE, REG8, REG16, REG32, REG64
    }

    public static enum RmType {

        NONE, REG_MEM8, REG_MEM16, REG_MEM32, REG_MEM64
    }

    /**
     * Return the number of mod bits. It is from 0 to 3.
     *
     * @return
     */
    int getMod();

    /**
     * Return the number of reg bits. It is from 0 to 7.
     *
     * @return
     */
    int getReg();

    /**
     * Return the number of r/m bits. It is from 0 to 7.
     *
     * @return
     */
    int getRm();

    /**
     * Return the extended number of reg bits. It includes REX.R, VEX.R or
     * XOP.R. It is from 0 to 15.
     *
     * @return
     */
    int getExtendedReg();

    /**
     * Return the extended number of reg bits. It includes REX.B, VEX.B or
     * XOP.B. It is from 0 to 15.
     *
     * @return
     */
    int getExtendedRm();

    /**
     * Return the operand of the reg parameter.
     *
     * @return
     */
    OperandRegister getRegOperand();

    /**
     * Return the operand of the rm parameter.
     *
     * @return
     */
    IOperand getRmOperand();

    /**
     * Parse the ModRm bytes in the input.
     *
     * @param context
     * @param input
     * @param reg_type
     * @param rm_type
     * @throws EOFException
     */
    void parse(InstructionContextImpl context, IByteInstructionInputStream input,
            RegType reg_type, RmType rm_type) throws EOFException;
}
