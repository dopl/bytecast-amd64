/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.instruction.operand;

import com.bytecast.amd64.api.constants.OperandType;
import com.bytecast.amd64.api.instruction.IOperand;

/**
 *
 * @author hapan
 */
public class OperandMemoryAddress implements IOperand {

    private String memory_address;
    
    public OperandMemoryAddress(String memory_address) {
        this.memory_address = memory_address;
    }

    @Override
    public OperandType getOperandType() {
        return OperandType.MEMORY_ADDRESS;
    }

    @Override
    public Object getOperandValue() {
        return this.memory_address;
    }
    
}
