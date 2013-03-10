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

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

/**
 *
 *   @author sheng
 */
public class NOPInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.NOP);

        // Parse opcode. See AMD64, volume 3, page 237 (page 271 of pdf).
        if (b == (byte) 0x90) {
            // Description: Performs no operation.
            // Mnemonic:    NOP
            // Opcode:      90
            ret.setOpCode("90");
            return ret;
        } else if (b == (byte) 0x0F) {
            b = input.read();
            if (b == (byte) 0x1F) {
                // Description: Performs no operation on a 16-bit register or memory operand.
                // Mnemonic:    NOP reg/mem16
                // Opcode:      0F 1F /0

                // Description: Performs no operation on a 32-bit register or memory operand.
                // Mnemonic:    NOP reg/mem32
                // Opcode:      0F 1F /0

                // Description: Performs no operation on a 64-bit register or memory operand.
                // Mnemonic:    NOP reg/mem64
                // Opcode:      0F 1F /0

                ret.setOpCode("0F 1F");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                switch (context.getOperandSize()) {
                    case SIZE_16:
                        rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM16);
                        break;
                    case SIZE_32:
                        rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM32);
                        break;
                    case SIZE_64:
                        rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM64);
                        break;
                    default:
                        throw new RuntimeException("Unknown operand size!");
                }
                // Check "/0", which means that the REX.Reg should be 0.
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REG.Reg should be 0!");
                }
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else {
                throw new RuntimeException("Unknown opcode!");
            }
        } else {
            throw new RuntimeException("Unknown opcode!");
        }
    }
}
