/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.api.instruction;

import java.util.List;

/**
 *
 * @author harsh
 */
public interface IInstruction {
    
    
    public List<IOperand> getOperands();
    
    public String getOpCode();
}
