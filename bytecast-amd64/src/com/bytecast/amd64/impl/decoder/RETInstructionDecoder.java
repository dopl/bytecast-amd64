/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.decoder;

import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.impl.instruction.AMD64Instruction;
import com.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import com.bytecast.amd64.api.constants.InstructionType;
/**
 *
 * @author harsh
 */
public class RETInstructionDecoder implements IInstructionDecoder {

   
    private void decodeOperands(IInstruction instruction, String instructionbytes) {
        
    }

    @Override
    public IInstruction decodeInstruction(String sectionStartMemAddr, String instructionbytes) {
         IInstruction instruction = new AMD64Instruction();
        instruction.setInstructionType(InstructionType.RET);
        
        decodeOperands(instruction, instructionbytes);
        
        return instruction;
    }
    
}
