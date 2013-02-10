/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.internal.api.parser;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import java.util.List;

/**
 *
 * @author harsh
 */
public interface IInstructionDecoder {
    
    IInstruction decodeInstruction(Long instructionMemAddress, List<Byte> instructionbytes);
}

