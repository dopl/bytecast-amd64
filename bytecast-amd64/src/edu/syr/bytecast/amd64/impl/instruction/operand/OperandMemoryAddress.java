/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.instruction.operand;

import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;

/**
 *
 * @author hapan
 */
public class OperandMemoryAddress implements IOperand {


    private Long memory_address;
    
    public OperandMemoryAddress(Long memory_address) {

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
