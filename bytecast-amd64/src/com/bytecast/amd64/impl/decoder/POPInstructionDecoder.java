/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.decoder;

import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.internal.api.parser.IInstructionDecoder;

/**
 *
 * @author sheng
 */
public class POPInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decodeInstruction(String sectionStartMemAddr, String instructionbytes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
