/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.internal.api.parser;

import com.bytecast.amd64.api.instruction.IInstruction;
import java.util.List;

/**
 *
 * @author harsh
 */
public interface IInstructionDecoder {
    
    IInstruction decodeInstruction(String sectionStartMemAddr, List<Byte> instructionbytes);
}
