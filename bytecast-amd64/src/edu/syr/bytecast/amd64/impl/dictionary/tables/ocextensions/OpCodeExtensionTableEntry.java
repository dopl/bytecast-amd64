/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary.tables.ocextensions;

import edu.syr.bytecast.amd64.api.constants.InstructionType;

/**
 *
 * @author bytecast
 */
public class OpCodeExtensionTableEntry {
    
    private int m_modRegFieldVal;
    private InstructionType m_instructionType;

    public OpCodeExtensionTableEntry(int m_modRegFieldVal, InstructionType m_instructionType) {
        this.m_modRegFieldVal = m_modRegFieldVal;
        this.m_instructionType = m_instructionType;
    }

    /**
     * @return the m_modRegFieldVal
     */
    public int getM_modRegFieldVal() {
        return m_modRegFieldVal;
    }

    /**
     * @param m_modRegFieldVal the m_modRegFieldVal to set
     */
    public void setM_modRegFieldVal(int m_modRegFieldVal) {
        this.m_modRegFieldVal = m_modRegFieldVal;
    }

    /**
     * @return the m_instructionType
     */
    public InstructionType getM_instructionType() {
        return m_instructionType;
    }

    /**
     * @param m_instructionType the m_instructionType to set
     */
    public void setM_instructionType(InstructionType m_instructionType) {
        this.m_instructionType = m_instructionType;
    }

    
    
    
}
