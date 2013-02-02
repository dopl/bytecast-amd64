/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.instruction.operand;

import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;

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
