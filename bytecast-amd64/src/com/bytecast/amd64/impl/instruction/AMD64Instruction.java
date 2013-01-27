/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.instruction;

import com.bytecast.amd64.api.constants.InstructionType;
import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.api.instruction.IOperand;
import java.util.List;

/**
 *
 * @author harsh
 */
public class AMD64Instruction implements IInstruction {

    @Override
    public List<IOperand> getOperands() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getOpCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addOperand(IOperand op) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setOpCode(String opcode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public InstructionType getInstructiontype() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setInstructionType(InstructionType instructionType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
