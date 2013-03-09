/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary.tables.modrmexttable;

import edu.syr.bytecast.amd64.api.constants.InstructionType;

/**
 *
 * @author bytecast
 */
public class ModRMExtensionTableEntry {
    private GroupName m_groupName;
    private Byte m_prefix;
    private Byte m_opCode;
    private short m_modRmRegfield;
    private InstructionType instructionType;

    public ModRMExtensionTableEntry(GroupName m_groupName, Byte m_prefix, Byte m_opCode, short m_modRmRegfield, InstructionType instructionType) {
        this.m_groupName = m_groupName;
        this.m_prefix = m_prefix;
        this.m_opCode = m_opCode;
        this.m_modRmRegfield = m_modRmRegfield;
        this.instructionType = instructionType;
    }

    /**
     * @return the m_groupName
     */
    public GroupName getM_groupName() {
        return m_groupName;
    }

    /**
     * @param m_groupName the m_groupName to set
     */
    public void setM_groupName(GroupName m_groupName) {
        this.m_groupName = m_groupName;
    }

    /**
     * @return the m_prefix
     */
    public Byte getM_prefix() {
        return m_prefix;
    }

    /**
     * @param m_prefix the m_prefix to set
     */
    public void setM_prefix(Byte m_prefix) {
        this.m_prefix = m_prefix;
    }

    /**
     * @return the m_opCode
     */
    public Byte getM_opCode() {
        return m_opCode;
    }

    /**
     * @param m_opCode the m_opCode to set
     */
    public void setM_opCode(Byte m_opCode) {
        this.m_opCode = m_opCode;
    }

    /**
     * @return the m_modRmRegfield
     */
    public short getM_modRmRegfield() {
        return m_modRmRegfield;
    }

    /**
     * @param m_modRmRegfield the m_modRmRegfield to set
     */
    public void setM_modRmRegfield(short m_modRmRegfield) {
        this.m_modRmRegfield = m_modRmRegfield;
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
