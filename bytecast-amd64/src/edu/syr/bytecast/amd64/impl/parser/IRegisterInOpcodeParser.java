package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;

/**
 *  A parser for get register values according to the one-byte opcode. The parser
 * is for the opcode syntax "+rb", "+rw", "+rd" and "+rq". See Table 2-2, Volume
 * 2, AMD64 Manual.
 *
 *  @author sheng
 */
public interface IRegisterInOpcodeParser {

    /**
     *  They are for "+rb", "+rw", "+rd" and "+rq".
     */
    enum Type {

        /**
         *  for 8-bit registers.
         */
        RB,
        /**
         *  for 16-bit registers.
         */
        RW,
        /**
         *  for 32-bit registers.
         */
        RD,
        /**
         *  for 64-bit registers.
         */
        RQ
    }

    /**
     *  Parse the opcode syntax "+rb", "+rw", "+rd" and "+rq".
     *
     *  @param type one of "+rb", "+rw", "+rd" and "+rq".
     *  @param value the value for getting register values. It ranges from 0 to
     * 7.
     */
    void parse(Type type, int value);

    /**
     *  Return the parsed register.
     *
     *  @return
     */
    OperandRegister getRegisterOperand();
}
