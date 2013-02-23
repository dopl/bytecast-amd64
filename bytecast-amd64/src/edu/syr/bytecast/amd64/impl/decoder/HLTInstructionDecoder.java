/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.util.DecoderUtil;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author chenqian
 */
public class HLTInstructionDecoder implements IInstructionDecoder{

    
    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) {
        IInstruction instruction = new AMD64Instruction(InstructionType.HLT);      
        
        //decodeOperands(instruction, instructionbytes);
        
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
