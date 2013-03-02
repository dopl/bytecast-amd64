/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import java.io.EOFException;

/**
 *
 * @author bytecast
 */
public interface IRegImmParser {
     static enum Type {

        IMM8, IMM16, IMM32, IMM64
    }
     
       public static enum RegType {

        NONE, REG8, REG16, REG32, REG64
    }
       
    void parse(IInstructionContext context, IInstructionByteInputStream input, IRegImmParser.RegType regtype,IRegImmParser.Type type) ;
    
    OperandRegister getRegOperand();

    OperandConstant getImmOperand();
}
