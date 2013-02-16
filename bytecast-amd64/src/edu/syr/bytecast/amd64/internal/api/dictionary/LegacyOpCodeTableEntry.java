/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.internal.api.dictionary;

/**
 *
 * @author Harsh
 */
public class LegacyOpCodeTableEntry {
    private LegacyPrefixGroup prefixGroup;
    private Byte opCode;

    public LegacyOpCodeTableEntry(LegacyPrefixGroup prefixGroup, Byte opCode) {
        this.prefixGroup = prefixGroup;
        this.opCode = opCode;
    }

    /**
     * @return the prefixGroup
     */
    public LegacyPrefixGroup getPrefixGroup() {
        return prefixGroup;
    }

    /**
     * @return the opCode
     */
    public Byte getOpCode() {
        return opCode;
    }
    
    
}
