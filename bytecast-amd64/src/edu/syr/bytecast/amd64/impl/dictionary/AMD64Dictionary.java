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
import edu.syr.bytecast.amd64.impl.dictionary.tables.legacyopcode.LegacyOpCodeTable;
import edu.syr.bytecast.amd64.impl.dictionary.tables.primaryopcode.PrimaryOpCodeTable;
import edu.syr.bytecast.amd64.impl.dictionary.tables.secondaryopcode.SecOpCodeTable;
import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;
import edu.syr.bytecast.common.impl.exception.BytecastAMD64Exception;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class AMD64Dictionary implements IAMD64Dictionary{

    private static AMD64Dictionary m_instance = new AMD64Dictionary();
    private static LegacyOpCodeTable m_legacyOpCodeTable;
    private static SecOpCodeTable m_secondaryOpCodeTable;
    private static PrimaryOpCodeTable m_primaryOpCodeTable;
    private Hashtable<Long, String> m_fnSymbolTable;
   
    
    private void loadData(){
        
       
        
    }
    private AMD64Dictionary() {
        loadData();
        m_legacyOpCodeTable = new LegacyOpCodeTable();
        m_secondaryOpCodeTable = new SecOpCodeTable();
        m_primaryOpCodeTable = new PrimaryOpCodeTable();
        
        m_secondaryOpCodeTable.loadData();
        m_legacyOpCodeTable.loadData();
        m_primaryOpCodeTable.loadData();
    }
    
    public static AMD64Dictionary getInstance()
    {
       return m_instance;
    }
    
    @Override
    public boolean isLegacyOpcode(Byte opcode) {
        return m_legacyOpCodeTable.isLegacyOpCode(opcode);
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
    public InstructionType getInstructionFromSecondaryOCTable(Byte opcode) throws BytecastAMD64Exception{
        InstructionType instructionFromOpCode = m_secondaryOpCodeTable.getInstructionFromOpCode(opcode);
        if(instructionFromOpCode==null){
            throw new BytecastAMD64Exception("opcode "+Integer.toHexString(opcode)+" not found in secondary opcode table" );
        }
         return instructionFromOpCode;   
    }
    
    @Override
    public InstructionType getInstructionFromPrimaryOCTable(Byte opcode) throws BytecastAMD64Exception{
        InstructionType instructionFromOpCode = m_primaryOpCodeTable.getInstructionFromOpCode(opcode);
        if(instructionFromOpCode==null) {
            throw new BytecastAMD64Exception("opcode "+Integer.toHexString(opcode)+" not found in primary opcode table" );
        }
        
        return instructionFromOpCode;
    }
    
    
    @Override
    public String getFunctionNameFromAddress(Long address) {
        String fnName;
        if(m_fnSymbolTable==null ) {
            fnName = "FUNCTION_NAME_NOT_FOUND";
        }
        else {
            fnName = m_fnSymbolTable.get(address);
        }
        return fnName;
    }
    
    public void setFunctionSymbolTable(Hashtable<Long,String> fnSymbolTable)
    {
        this.m_fnSymbolTable = fnSymbolTable;
    }

   

    @Override
    public boolean isOpcodeExtensionIndicator(Byte opcode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
