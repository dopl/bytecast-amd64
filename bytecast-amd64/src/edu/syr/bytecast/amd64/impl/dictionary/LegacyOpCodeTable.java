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

package edu.syr.bytecast.amd64.impl.dictionary;

import edu.syr.bytecast.amd64.internal.api.dictionary.LegacyOpCodeTableEntry;
import edu.syr.bytecast.amd64.internal.api.dictionary.LegacyPrefixGroup;
import java.util.List;

public class LegacyOpCodeTable {
    private static List<LegacyOpCodeTableEntry> legacyOpCodes;
    
    public static void loadData()
    {
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.OPERAND_SIZE_OVERRIDE, (byte)0x66));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.ADDRESS_SIZE_OVERRIDE, (byte)0x67));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.SEGMENT_OVERRIDE, (byte)0x2e));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.SEGMENT_OVERRIDE, (byte)0x3e));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.SEGMENT_OVERRIDE, (byte)0x26));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.SEGMENT_OVERRIDE, (byte)0x64));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.SEGMENT_OVERRIDE, (byte)0x65));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.SEGMENT_OVERRIDE, (byte)0x36));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.LOCK, (byte)0xf0));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.REPEAT, (byte)0xf3));
        legacyOpCodes.add(new LegacyOpCodeTableEntry(LegacyPrefixGroup.REPEAT, (byte)0xf2));
    }
    
    public static boolean isLegacyOpCode(Byte opcode)
    {
        for(int i=0;i<legacyOpCodes.size();++i)
        {
            if(legacyOpCodes.get(i).getOpCode()==opcode)
                return true;
        }
        return false;
    }
}
