/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.output;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.ISection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bytecast
 */
public class AMD64Section implements ISection {

    private Map<Long,IInstruction> m_instructions;
    private Long m_startMemoryAddress;
    private boolean m_isEntryPoint;

    public AMD64Section(Map<Long,IInstruction> m_instructions, Long m_startMemoryAddress, boolean m_isEntryPoint) {
        this.m_instructions = m_instructions;
        this.m_startMemoryAddress = m_startMemoryAddress;
        this.m_isEntryPoint = m_isEntryPoint;
    }
    
    
    @Override
    public Map<Long,IInstruction> getAllInstructionObjects() {
        return m_instructions;
    }

    @Override
    public long getSectionStartMemAddr() {
        return m_startMemoryAddress;
    }

    @Override
    public boolean isEntryPoint() {
        return m_isEntryPoint;
    }

    @Override
    public String getSectionName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
