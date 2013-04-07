/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.output;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bytecast
 */
public class AMD64Section implements ISection {

    private List<MemoryInstructionPair> m_instructions;
    private Map<Long, Integer> m_memindexmap;
    private Long m_startMemoryAddress;
    private boolean m_isEntryPoint;

    public AMD64Section(List<MemoryInstructionPair> m_instructions, Long m_startMemoryAddress, boolean m_isEntryPoint) {
        this.m_instructions = m_instructions;
        createMemoryIndexMap();
        this.m_startMemoryAddress = m_startMemoryAddress;
        this.m_isEntryPoint = m_isEntryPoint;
    }
    
    
    @Override
    public List<MemoryInstructionPair> getAllInstructionObjects() {
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

    @Override
    public int indexOfInstructionAt(Long memoryAddress) {
        return m_memindexmap.get(memoryAddress);
    }

    private void createMemoryIndexMap() {
        m_memindexmap = new HashMap<Long, Integer>();
        final int size = m_instructions.size();
        for(int i=0;i<size;++i){
            m_memindexmap.put(m_instructions.get(i).getmInstructionAddress(), i);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.m_instructions != null ? this.m_instructions.hashCode() : 0);
        hash = 59 * hash + (this.m_memindexmap != null ? this.m_memindexmap.hashCode() : 0);
        hash = 59 * hash + (this.m_startMemoryAddress != null ? this.m_startMemoryAddress.hashCode() : 0);
        hash = 59 * hash + (this.m_isEntryPoint ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AMD64Section other = (AMD64Section) obj;
        if (this.m_instructions != other.m_instructions && (this.m_instructions == null || !this.m_instructions.equals(other.m_instructions))) {
            return false;
        }
        if (this.m_memindexmap != other.m_memindexmap && (this.m_memindexmap == null || !this.m_memindexmap.equals(other.m_memindexmap))) {
            return false;
        }
        if (this.m_startMemoryAddress != other.m_startMemoryAddress && (this.m_startMemoryAddress == null || !this.m_startMemoryAddress.equals(other.m_startMemoryAddress))) {
            return false;
        }
        if (this.m_isEntryPoint != other.m_isEntryPoint) {
            return false;
        }
        return true;
    }
    
}
