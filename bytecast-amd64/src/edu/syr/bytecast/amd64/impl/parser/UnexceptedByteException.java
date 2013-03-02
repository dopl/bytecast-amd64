package edu.syr.bytecast.amd64.impl.parser;

/**
 * An exception is thrown if the byte is unexpected.
 *
 * @author sheng
 */
public class UnexceptedByteException extends RuntimeException {

    public UnexceptedByteException() {
        super();
    }

    public UnexceptedByteException(String message) {
        super(message);
    }
}
