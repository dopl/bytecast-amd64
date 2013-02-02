/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.api.output;

import com.bytecast.amd64.api.instruction.IInstruction;
import java.util.List;

/**
 *
 * @author Harsh
 */
public interface ISection {
    
    public List<IInstruction> getAllInstructionObjects();
    
    public long getSectionStartMemAddr();
    
    public boolean isEntryPoint();
}
