/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary;

import edu.syr.bytecast.amd64.internal.api.dictionary.IAMD64Dictionary;
import java.util.Set;

/**
 *
 * @author Harsh
 */
public class AMD64Dictionary implements IAMD64Dictionary{

    private static AMD64Dictionary _instance = new AMD64Dictionary();
    private static LegacyOpCodeTable legacyOpCodeTable;
    
    private AMD64Dictionary() {
        legacyOpCodeTable = new LegacyOpCodeTable();
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
    
}
