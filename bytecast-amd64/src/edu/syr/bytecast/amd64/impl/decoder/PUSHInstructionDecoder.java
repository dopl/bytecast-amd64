/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.List;

/**
 *
 * @author sheng
 */
public class PUSHInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext sectionStartMemAddr, IInstructionByteInputStream input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
