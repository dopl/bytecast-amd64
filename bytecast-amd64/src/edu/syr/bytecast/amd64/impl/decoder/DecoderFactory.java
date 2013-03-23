/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.HashMap;
import java.util.Map;

public class DecoderFactory {
    private static Map<InstructionType, IInstructionDecoder> decoders = new HashMap<InstructionType, IInstructionDecoder>();

    static {
        decoders.put(InstructionType.RET, new RETInstructionDecoder());
        decoders.put(InstructionType.MOV, new MOVInstructionDecoder());
        decoders.put(InstructionType.MOVSX, new MOVSXInstructionDecoder());
        decoders.put(InstructionType.MOVZX, new MOVZXInstructionDecoder());
        decoders.put(InstructionType.NOP, new NOPInstructionDecoder());
        decoders.put(InstructionType.POP, new POPInstructionDecoder());
        decoders.put(InstructionType.PUSH, new PUSHInstructionDecoder());
        decoders.put(InstructionType.ADD, new ADDInstructionDecoder());   
        decoders.put(InstructionType.HLT, new HLTInstructionDecoder());  
        decoders.put(InstructionType.JO, new JCCInstructionDecoder());
        decoders.put(InstructionType.JNO, new JCCInstructionDecoder());
        decoders.put(InstructionType.JB, new JCCInstructionDecoder());
        decoders.put(InstructionType.JAE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JNE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JBE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JA, new JCCInstructionDecoder());
        decoders.put(InstructionType.JS, new JCCInstructionDecoder());
        decoders.put(InstructionType.JNS, new JCCInstructionDecoder());
        decoders.put(InstructionType.JP, new JCCInstructionDecoder());
        decoders.put(InstructionType.JNP, new JCCInstructionDecoder());
        decoders.put(InstructionType.JL, new JCCInstructionDecoder());
        decoders.put(InstructionType.JGE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JLE, new JCCInstructionDecoder());
        decoders.put(InstructionType.JG, new JCCInstructionDecoder());
        decoders.put(InstructionType.JMP, new JMPInstructionDecoder());
        //decoders.put(InstructionType.LEA, new LEAInstructionDecoder());
        decoders.put(InstructionType.LEAVE, new LEAVEInstructionDecoder());
        decoders.put(InstructionType.TEST, new TESTInstructionDecoder());
        decoders.put(InstructionType.SHR, new SHRInstructionDecoder());
    }
    

    
    
    public static IInstructionDecoder getInstructionDecoder(InstructionType type) {
        return decoders.get(type);
    }
}
