/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.util.DecoderUtil;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author chenqian
 */
public class ADDInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decodeInstruction(Long instructionMemAddress, List<Byte> instructionbytes) {
        IInstruction instruction = new AMD64Instruction(InstructionType.ADD);      
        
        decodeOperands(instruction, instructionbytes);
        
        return instruction;
    }
    
    // 48 83 c0 08   add $0x8 %rsp
    // 83 : reg + imm8
    private void decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
        if(instructionbytes.size()!=4) {
            throw new UnsupportedOperationException("Not A correct ADD instructionbytes.");
        }
        
        if(instructionbytes.get(1) == 0x83){
            instruction.setOpCode("83");
           List<String> tempdecodes = DecoderUtil.DecodeHexToOctal(instructionbytes.get(2));
           if(tempdecodes.get(1).equals("000")){ // verify is a add insturction
               //add constant value
               instruction.addOperand(new OperandConstant(instructionbytes.get(3).longValue()));
               //add register
               instruction.addOperand(new OperandRegister(DecoderUtil.CastRegister(tempdecodes.get(0))));        
            }          
         }
        //other add opcode to be implement
        //else if(instructionbytes.get(1) == 0x81){}   
    }
}
