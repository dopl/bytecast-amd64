package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public interface ISibParser {

    void parse(IInstructionContext context, IInstructionByteInputStream input, int mod) throws EOFException;

    RegisterType getBaseRegister();

    RegisterType getIndexRegister();

    int getScale();

    int getScaleFactor();
}
