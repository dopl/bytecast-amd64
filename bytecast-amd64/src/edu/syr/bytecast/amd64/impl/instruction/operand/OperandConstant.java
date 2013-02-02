/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.instruction.operand;

import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;

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
