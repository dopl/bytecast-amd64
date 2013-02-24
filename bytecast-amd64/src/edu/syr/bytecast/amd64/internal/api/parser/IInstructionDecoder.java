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
package edu.syr.bytecast.amd64.internal.api.parser;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import java.io.EOFException;

public interface IInstructionDecoder {

    /**
     * Decode instruction bytes in the input to an instruction. The next byte of
     * the input is the opcode.
     *
     * @param context the instruction context.
     * @param input the input of instruction bytes.
     * @return the decoded instruction.
     * @throws EOFException if the end of the stream is reached.
     */
    IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException;
}
