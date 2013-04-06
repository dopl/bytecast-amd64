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
import edu.syr.bytecast.amd64.impl.dictionary.tables.ocextensions.OpCodeExtensionTable;
import edu.syr.bytecast.amd64.impl.dictionary.tables.primaryopcode.OpCodeExtensionGroup;
import edu.syr.bytecast.amd64.impl.dictionary.tables.primaryopcode.PrimaryOpCodeTable;
import edu.syr.bytecast.amd64.impl.dictionary.tables.secondaryopcode.SecOpCodeTable;
import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;
import edu.syr.bytecast.common.impl.exception.BytecastAMD64Exception;
import edu.syr.bytecast.interfaces.fsys.ExeObjFunction;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AMD64Dictionary implements IAMD64Dictionary{

    private static AMD64Dictionary m_instance = new AMD64Dictionary();
    private static LegacyOpCodeTable m_legacyOpCodeTable;
    private static SecOpCodeTable m_secondaryOpCodeTable;
    private static PrimaryOpCodeTable m_primaryOpCodeTable;
    private Hashtable<Long, ExeObjFunction> m_fnSymbolTable;
    private Hashtable<Byte,OpCodeExtensionGroup > m_opcodeextension_map;
    private OpCodeExtensionTable m_ocexttable;
    
    private void loadData(){
        
       
        
    }
    private AMD64Dictionary() {
        loadData();
        m_legacyOpCodeTable = new LegacyOpCodeTable();
        m_secondaryOpCodeTable = new SecOpCodeTable();
        m_primaryOpCodeTable = new PrimaryOpCodeTable();
        m_ocexttable = new OpCodeExtensionTable();
        
        m_ocexttable.loadData();
        m_secondaryOpCodeTable.loadData();
        m_legacyOpCodeTable.loadData();
        m_primaryOpCodeTable.loadData();
        loadOpCodeExtensionMap();
    }
    
    private void loadOpCodeExtensionMap() {
       m_opcodeextension_map = new Hashtable<Byte, OpCodeExtensionGroup>();
       m_opcodeextension_map.put((byte)0x80, OpCodeExtensionGroup.GROUP_1);
       m_opcodeextension_map.put((byte)0x81, OpCodeExtensionGroup.GROUP_1);
       m_opcodeextension_map.put((byte)0x82, OpCodeExtensionGroup.GROUP_1);
       m_opcodeextension_map.put((byte)0x83, OpCodeExtensionGroup.GROUP_1);
       
       m_opcodeextension_map.put((byte)0x8f, OpCodeExtensionGroup.GROUP_1a);
       
       m_opcodeextension_map.put((byte)0xc0, OpCodeExtensionGroup.GROUP_2);
       m_opcodeextension_map.put((byte)0xc1, OpCodeExtensionGroup.GROUP_2);
       m_opcodeextension_map.put((byte)0xd0, OpCodeExtensionGroup.GROUP_2);
       m_opcodeextension_map.put((byte)0xd1, OpCodeExtensionGroup.GROUP_2);
       m_opcodeextension_map.put((byte)0xd2, OpCodeExtensionGroup.GROUP_2);
       m_opcodeextension_map.put((byte)0xd3, OpCodeExtensionGroup.GROUP_2);
       
       m_opcodeextension_map.put((byte)0xf6, OpCodeExtensionGroup.GROUP_3);
       m_opcodeextension_map.put((byte)0xf7, OpCodeExtensionGroup.GROUP_3);
       
       m_opcodeextension_map.put((byte)0xfe, OpCodeExtensionGroup.GROUP_4);
       
       m_opcodeextension_map.put((byte)0xff, OpCodeExtensionGroup.GROUP_5);
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
            fnName = m_fnSymbolTable.get(address).getName();
        }
        return fnName;
    }
    
    public void setFunctionSymbolTable(Hashtable<Long,ExeObjFunction> fnSymbolTable)
    {
        this.m_fnSymbolTable = fnSymbolTable;
    }

   

    @Override
    public boolean isOpcodeExtensionIndicator(Byte opcode) {
        return m_opcodeextension_map.containsKey(opcode);
    }

    @Override
    public Long getFunctionAddressFromName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExeObjFunction getFunctionByName(String name) {
        for (Long key :m_fnSymbolTable.keySet()){
            ExeObjFunction fn = m_fnSymbolTable.get(key);
            if(fn.getName().equals(name))
                return fn;
            
        }
        return null;
    }

    @Override
    public ExeObjFunction getFunctionByAddress(Long address) {
        return m_fnSymbolTable.get(address);
    }

    @Override
    public OpCodeExtensionGroup getOCExtGroup(Byte opcode) {
        return m_opcodeextension_map.get(opcode);
    }

    @Override
    public InstructionType getInstructionTypeFromOCExt(OpCodeExtensionGroup grp, int modrmreg) {
        return m_ocexttable.getInstruction(grp, modrmreg);
    }

    
}
