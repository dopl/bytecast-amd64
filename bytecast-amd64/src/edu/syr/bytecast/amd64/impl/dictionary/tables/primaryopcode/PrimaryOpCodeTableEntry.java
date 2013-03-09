/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary.tables.primaryopcode;

import edu.syr.bytecast.amd64.api.constants.InstructionType;

/**
 *
 * @author bytecast
 */
public class PrimaryOpCodeTableEntry {
    private Byte opcode;
    private InstructionType instruction;

    public PrimaryOpCodeTableEntry(Byte opcode, InstructionType instruction) {
        this.opcode = opcode;
        this.instruction = instruction;
    }

    /**
     * @return the opcode
     */
    public Byte getOpcode() {
        return opcode;
    }

    /**
     * @param opcode the opcode to set
     */
    public void setOpcode(Byte opcode) {
        this.opcode = opcode;
    }

    /**
     * @return the instruction
     */
    public InstructionType getInstruction() {
        return instruction;
    }

    /**
     * @param instruction the instruction to set
     */
    public void setInstruction(InstructionType instruction) {
        this.instruction = instruction;
    }
    
    
}
