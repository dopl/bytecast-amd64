package edu.syr.bytecast.amd64.impl.parser;

import java.io.EOFException;

/**
 *
 * @author sheng
 */
public interface IDispParser {

    enum Type {

        DISP8, DISP16, DISP32
    }

    void parse(IInstructionByteInputStream input, Type type) throws EOFException;

    long getNumber();
}
