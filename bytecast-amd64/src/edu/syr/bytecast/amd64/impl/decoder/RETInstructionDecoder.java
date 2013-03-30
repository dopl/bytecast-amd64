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
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.parser.IImmParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import java.io.EOFException;

public class RETInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext sectionStartMemAddr, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.RET);

        // Parse opcode. See AMD64, volume 3, page 268 (page 302 of pdf).
        // And AMD64, volume 3, page 269 (page 303 of pdf).
        if (b == (byte) 0xC3 || b == (byte) 0xCB) {
            ret.setOpCode(String.format("%x", b));
            return ret;
        } else if (b == (byte) 0xC2 || b == (byte) 0xCA) {
            ret.setOpCode(String.format("%x", b));
            IImmParser parser = ParserFactory.getImmParser();
            parser.parse(input, IImmParser.Type.IMM16);
            ret.addOperand(parser.getOperand());
            return ret;
        }
        throw new RuntimeException("Unknown opcode!");
    }
}
