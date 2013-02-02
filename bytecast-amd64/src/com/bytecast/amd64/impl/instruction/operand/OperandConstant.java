/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.instruction.operand;

import com.bytecast.amd64.api.constants.OperandType;
import com.bytecast.amd64.api.instruction.IOperand;

/**
 *
 * @author chenqian
 */
public class OperandConstant implements IOperand  {
    
    private Long constant;
    
    public OperandConstant(Long constant)
    {
        this.constant = constant;
    }
    
    @Override
    public OperandType getOperandType() {
        return OperandType.CONSTANT;
    }

    @Override
    public Object getOperandValue() {
        return this.constant;
    }
    
}
