/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.instruction;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;

/**
 *
 * @author bytecast
 */
public interface IInstructionMutator extends IInstruction{
 
    public void addByte(Byte b);
}
