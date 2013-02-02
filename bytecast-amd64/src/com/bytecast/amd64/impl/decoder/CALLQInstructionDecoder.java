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
public class CALLQInstructionDecoder implements IInstructionDecoder{

    @Override
    public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instructionbytes) {
        IInstruction instruction = new AMD64Instruction(InstructionType.CALLQ);      
        
        decodeOperands(instruction, instructionbytes);
        
        return instruction;
    }
    
    // 48 83 c0 08   add $0x8 %rsp
    // 83 : reg + imm8
    private void decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
       
        
        if(instructionbytes.get(0) == 0xe8){
            instruction.setOpCode("e8");
           
         }
        else if(instructionbytes.get(0) == 0xff){
             instruction.setOpCode("ff");
        }
        else if(instructionbytes.get(0)==0x41 && instructionbytes.get(1) == 0xff){
             instruction.setOpCode("ff");
        }
        //other add opcode to be implement
        //else if(instructionbytes.get(1) == 0x81){}   
    }
    
}
