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

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.impl.dictionary.tables.secondaryopcode.SecOpCodeTable;
import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;

public class AMD64Dictionary implements IAMD64Dictionary{

    private static AMD64Dictionary _instance = new AMD64Dictionary();
    private static LegacyOpCodeTable legacyOpCodeTable;
    private static SecOpCodeTable secondaryOpCodeTable;
    
    private AMD64Dictionary() {
        legacyOpCodeTable = new LegacyOpCodeTable();
        secondaryOpCodeTable = new SecOpCodeTable();
        
        secondaryOpCodeTable.loadData();
        legacyOpCodeTable.loadData();
    }
    
    public static AMD64Dictionary getInstance()
    {
       return _instance;
    }
    
    @Override
    public boolean isLegacyOpcode(Byte opcode) {
        return legacyOpCodeTable.isLegacyOpCode(opcode);
    }

    @Override
    public boolean isRexPrefix(Byte opcode) {
       return opcode >= 0x40 && opcode <=0x4f;
    }

    @Override
    public boolean isEscapeToSecondaryOpCode(Byte opcode) {
        return opcode == 0x0f;
    }

    @Override
    public InstructionType getInstructionFromSecondaryOCTable(Byte opcode) {
       return  secondaryOpCodeTable.getInstructionFromOpCode(opcode);
    }
    
}
