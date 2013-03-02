/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandSectionName;

/**
 *
 * @author bytecast
 */
public class AddressFunctionParserImpl implements IAddressFunctionParser{

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public OperandMemoryEffectiveAddress getAddressOperand() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public OperandSectionName getSectionNameOperand() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
