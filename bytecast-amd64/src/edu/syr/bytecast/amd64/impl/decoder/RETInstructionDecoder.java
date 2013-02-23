/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import java.util.List;
/**
 *
 * @author harsh
 */
public class RETInstructionDecoder implements IInstructionDecoder {

   
    private void decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
        
    }

    @Override
    public IInstruction decode(IInstructionContext sectionStartMemAddr, IInstructionByteInputStream input) {
        IInstruction instruction = new AMD64Instruction(InstructionType.RET);
        
        
        //decodeOperands(instruction, instructionbytes);
        
        return instruction;
    }
    
}

