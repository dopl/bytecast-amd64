/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary.tables.secondaryopcode;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import java.util.List;

/**
 *
 * @author bytecast
 */
public class SecOpCodeTable {
    private static List<SecOpCodesTableEntry> table;
    
    public static void loadData(){
        table.add(new SecOpCodesTableEntry((byte)0x66, (byte)0x1f, InstructionType.NOP));
    }
    
    public InstructionType getInstructionFromOpCode(Byte opcode)
    {
        for(SecOpCodesTableEntry row : table)
        {
            if(row.getOpCode()==opcode)
                return row.getInstructionType();
        }
        return null;
    }
}
