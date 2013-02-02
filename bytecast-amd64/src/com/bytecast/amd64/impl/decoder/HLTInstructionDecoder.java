/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.decoder;

import com.bytecase.amd64.util.DecoderUtil;
import com.bytecast.amd64.api.constants.InstructionType;
import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.impl.instruction.AMD64Instruction;
import com.bytecast.amd64.impl.instruction.operand.OperandConstant;
import com.bytecast.amd64.impl.instruction.operand.OperandRegister;
import com.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author chenqian
 */
public class HLTInstructionDecoder implements IInstructionDecoder{

    
    @Override
    public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instructionbytes) {
        IInstruction instruction = new AMD64Instruction(InstructionType.HLT);      
        
        decodeOperands(instruction, instructionbytes);
        
        return instruction;
    }
    
    private void decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
        if(instructionbytes.size()!=1) {
            return;
        }      
        if(instructionbytes.get(0) == 0xf4){
            instruction.setOpCode("f4"); 
        }
    }
}
