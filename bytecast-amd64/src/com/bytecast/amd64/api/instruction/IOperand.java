/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.api.instruction;

import com.bytecast.amd64.api.constants.OperandType;

/**
 *
 * @author harsh
 */
public interface IOperand {
    
    public OperandType getOperandType();
    
    public Object getOperandValue();
    
}
