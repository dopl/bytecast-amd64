/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.decoder;

import com.bytecase.amd64.util.DecoderUtil;
import com.bytecast.amd64.api.constants.InstructionType;
import com.bytecast.amd64.api.constants.RegisterType;
import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.api.instruction.IOperand;
import com.bytecast.amd64.impl.instruction.AMD64Instruction;
import com.bytecast.amd64.impl.instruction.operand.OperandConstant;
import com.bytecast.amd64.impl.instruction.operand.OperandRegister;
import com.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author chenqian
 */
public class ADDInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instructionbytes) {
        IInstruction instruction = new AMD64Instruction(InstructionType.ADD);      
        
        if(decodeOperands(instruction, instructionbytes));
        
        return instruction;
    }
    
    // 48 83 c0 08   add $0x8 %rsp
    // 83 : reg + imm8
    private boolean decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
        if(instructionbytes.size()!=4) {
            return false;
        }
        
        if(instructionbytes.get(1) == 0x83){
            instruction.setOpCode("83");
           List<String> decodes = DecoderUtil.DecodeHexToOctal(instructionbytes.get(2));
           if(decodes.get(1).equals("000")){ // verify is a add insturction
               //add constant value
               instruction.addOperand(new OperandConstant(instructionbytes.get(3).longValue()));
               //add register
               instruction.addOperand(new OperandRegister(DecoderUtil.CastRegister(decodes.get(0))));
               return true;
           }
           else {
                return false;
            }
        }
        //other add opcode to be implement
        //else if(instructionbytes.get(1) == 0x81){}
        return false;
        
    }
    
}
