/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary.tables.secondaryopcode;

import edu.syr.bytecast.amd64.api.constants.InstructionType;

/**
 *
 * @author bytecast
 */
public class SecOpCodesTableEntry {
    
    private Byte prefix;
    private Byte opCode;
    private InstructionType instructionType;

    public SecOpCodesTableEntry(Byte prefix, Byte opCode, InstructionType instructionType) {
        this.prefix = prefix;
        this.opCode = opCode;
        this.instructionType = instructionType;
    }

    /**
     * @return the prefix
     */
    public Byte getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(Byte prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the opCode
     */
    public Byte getOpCode() {
        return opCode;
    }

    /**
     * @param opCode the opCode to set
     */
    public void setOpCode(Byte opCode) {
        this.opCode = opCode;
    }

    /**
     * @return the instructionType
     */
    public InstructionType getInstructionType() {
        return instructionType;
    }

    /**
     * @param instructionType the instructionType to set
     */
    public void setInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
    }
}
