/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandSectionName;
import java.io.EOFException;

/**
 *
 * @author bytecast
 */
public interface IAddressFunctionParser {
    /**
     *
     * @param context
     * @param input
     * @param regtype
     * @param type
     */
    void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException;

    /**
     *
     * @return
     */
    OperandMemoryEffectiveAddress getAddressOperand();

    /**
     *
     * @return
     */
    OperandSectionName getSectionNameOperand();
}
