/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import edu.syr.bytecast.amd64.util.DecoderUtil;
import java.util.List;

/**
 *
 * @author chenqian
 */
public class CALLQInstructionDecoder implements IInstructionDecoder{

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) {
        IInstruction instruction = new AMD64Instruction(InstructionType.CALLQ);      
        //_instructionMemAddress = instructionMemAddress;
        //decodeOperands(instruction, instructionbytes);
        
        return instruction;
    }
    
    private long _instructionMemAddress;
    
    // 48 83 c0 08   add $0x8 %rsp
    // 83 : reg + imm8
//    private void decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
//       
//        int offset = instructionbytes.size();
//        if(((instructionbytes.get(0)>>4) & 0x07)==4){ // check is prefix or not
//              
//        }
//        else{
//              if(instructionbytes.get(0) == 0xe8){
//                instruction.setOpCode("e8");
//                long a = DecoderUtil.ByteConcatenator(instructionbytes,offset-1);
//                offset = offset + (int)a;
//                long operand = _instructionMemAddress + offset; 
//                 instruction.addOperand(new OperandConstant(operand));
//                }
//              else if(instructionbytes.get(0) == 0xff){
//                 instruction.setOpCode("ff");
//              }
//         }  
//        //other callq opcode to be implement 
//    }
    
    private long AnalOperand(long oldoperand) {
       long ret = 0;
       
       return ret;
    }    
}
