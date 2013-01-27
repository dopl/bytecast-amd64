/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.instruction;

import com.bytecast.amd64.api.constants.InstructionType;
import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.api.instruction.IOperand;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author harsh
 */
public class AMD64Instruction implements IInstruction {

    private InstructionType instructionType;
    private String opCode;
    private List<IOperand> operands;
    
    public AMD64Instruction(InstructionType instructionType) {
        this.instructionType = instructionType;
        operands = new ArrayList<IOperand>();
    }
   
    
    @Override
    public List<IOperand> getOperands() {
        return this.operands;
    }

    @Override
    public String getOpCode() {
        return this.opCode;
    }

    @Override
    public void addOperand(IOperand op) {
        operands.add(op);
    }

    @Override
    public void setOpCode(String opcode) {
        this.opCode = opcode;
    }

    @Override
    public InstructionType getInstructiontype() {
        return this.instructionType;
    }

    
}
