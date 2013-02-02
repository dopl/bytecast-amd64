/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.decoder;

import com.bytecast.amd64.api.constants.InstructionType;
import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.impl.instruction.AMD64Instruction;
import com.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;
/**
 *
 * @author hapan
 */
public class JCCInstructionDecoder implements IInstructionDecoder {
    
    
    @Override
    public IInstruction decodeInstruction(Long sectionStartMemAddr, List<Byte> instructionbytes) {
        IInstruction instruction = null;
        Byte tmp = instructionbytes.get(0);
        if((tmp & 0x70) == 0x70) {
            switch(tmp | 0xF0) {
                case 0xF2:
                    instruction = new AMD64Instruction(InstructionType.JB);
                    break;
                case 0xF3:
                    instruction = new AMD64Instruction(InstructionType.JAE);
                    break;
                case 0xF4:
                    instruction = new AMD64Instruction(InstructionType.JE);
                    break;
                case 0xF5:
                    instruction = new AMD64Instruction(InstructionType.JNE);
                    break;
                default:
                    break;
            }
        } else if(tmp == 0x0F) {
            tmp = instructionbytes.get(2);
            if((tmp & 0x80) == 0x80) {
                switch(tmp | 0xF0) {
                    case 0xF2:
                        instruction = new AMD64Instruction(InstructionType.JB);
                        break;
                    case 0xF3:
                        instruction = new AMD64Instruction(InstructionType.JAE);
                        break;
                    case 0xF4:
                        instruction = new AMD64Instruction(InstructionType.JE);
                        break;
                    case 0xF5:
                        instruction = new AMD64Instruction(InstructionType.JNE);
                        break;
                    default:
                        break;
                }
            }
        }
        // need add the method to parse the operands
        return instruction;
    }
    
}
