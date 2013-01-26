/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.instruction.operand;

import com.bytecast.amd64.api.constants.OperandType;
import com.bytecast.amd64.api.constants.RegisterType;
import com.bytecast.amd64.api.instruction.IOperand;

/**
 *
 * @author harsh
 */
public class OperandRegister implements IOperand{
    private RegisterType register;

    
    public OperandRegister(RegisterType register)
    {
        this.register = register;
    }
    @Override
    public OperandType getOperandType() {
        return OperandType.REGISTER;
    }

    @Override
    public Object getOperandValue() {
       return this.register;
    }
    
}
