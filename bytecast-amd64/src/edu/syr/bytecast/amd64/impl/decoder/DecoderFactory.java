/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author harsh
 */
public class DecoderFactory {
    private static Map<InstructionType, IInstructionDecoder> decoders = new HashMap<InstructionType, IInstructionDecoder>();

    static {
        decoders.put(InstructionType.RET, new RETInstructionDecoder());
        decoders.put(InstructionType.MOV, new MOVInstructionDecoder());
        decoders.put(InstructionType.NOP, new NOPInstructionDecoder());
        decoders.put(InstructionType.POP, new POPInstructionDecoder());
        decoders.put(InstructionType.PUSH, new PUSHInstructionDecoder());
        decoders.put(InstructionType.ADD, new ADDInstructionDecoder());   
        decoders.put(InstructionType.HLT, new HLTInstructionDecoder());  
        decoders.put(InstructionType.JAE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JB, new JCCInstructionDecoder());
        decoders.put(InstructionType.JE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JNE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JMP, new JMPInstructionDecoder());
        //decoders.put(InstructionType.LEA, new LEAInstructionDecoder());
        decoders.put(InstructionType.LEAVE, new LEAVEInstructionDecoder());
    }
    

    
    
    public static IInstructionDecoder getInstructionDecoder(InstructionType type) {
        return decoders.get(type);
    }
}
