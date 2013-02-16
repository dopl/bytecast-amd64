/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.internal.api.parser;

import java.util.List;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
/**
 *
 * @author Harsh
 */
public interface IInstructionLexer {
    
    public List<IInstruction> convertInstructionBytesToObjects(Long sectionStartMemeAddress, List<Byte> bytes );

}
