/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.util.DecoderUtil;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author chenqian
 */
public class CALLQInstructionDecoder implements IInstructionDecoder{

    @Override
    public IInstruction decodeInstruction(Long instructionMemAddress, List<Byte> instructionbytes) {
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
