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

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to convert instruction bytes to instruction objects.
 *
 * @author sheng
 */
public class AMD64InstructionParser {

    private IAMD64Dictionary m_dictionary;
    private ILegacyPrefixParser m_legacy_parser;
    private IRexPrefixParser m_rex_parser;

    /**
     * Convert instruction bytes to instruction objects.
     *
     * @param input
     * @return
     */
    public List<IInstruction> parse(IByteInstructionInputStream input) {
        List<IInstruction> ret = new ArrayList<IInstruction>();
        try {
            while (true) {
                if (input.available() == 0) {
                    // Reach the end of the input normally. Return the ret.
                    return ret;
                }

                InstructionContextImpl context = new InstructionContextImpl();
                byte b = input.peek();

                // Parse legacy prefixes.
                if (m_dictionary.isLegacyOpcode(b)) {
                    m_legacy_parser.parse(context, input);
                    b = input.peek();
                }

                // Parse REX prefix
                if (m_dictionary.isRexPrefix(b)) {
                    m_rex_parser.parse(context, input);
                    b = input.peek();
                }

                // Parse opcode.
                // TODO read instruction type from the opcode dictionary.
                // Assume we got MOV instruction.
                InstructionType type = InstructionType.MOV;
                // Assume we got MOVInstructionDecoder through DecoderFactory.
                DemoMovInstructionDecoder decoder = new DemoMovInstructionDecoder();
                ret.add(decoder.decode(context, input));
            }
        } catch (EOFException ex) {
            // Reach the end of the input abnormally. Throw the exception.
            // TODO Maybe we should ignore the current instruction.
            throw new RuntimeException(ex);
        }
    }
}
