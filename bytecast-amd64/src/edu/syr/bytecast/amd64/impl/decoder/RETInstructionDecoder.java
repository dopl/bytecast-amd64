/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import java.util.List;
/**
 *
 * @author harsh
 */
public class RETInstructionDecoder implements IInstructionDecoder {

   
    private void decodeOperands(IInstruction instruction, List<Byte> instructionbytes) {
        
    }

    @Override
    public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instructionbytes) {
        IInstruction instruction = new AMD64Instruction(InstructionType.RET);
        
        
        decodeOperands(instruction, instructionbytes);
        
        return instruction;
    }
    
}

