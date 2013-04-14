package edu.syr.bytecast.amd64.impl.parser;

import java.io.EOFException;
import java.util.List;

/**
 * An interface to read instruction bytes. It also support peeking and skipping
 * over.
 *
 * @author sheng
 */
public interface IInstructionByteInputStream {

    /**
     * Returns an estimate of the number of bytes that can be read (or peeked or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream.
     *
     * @return
     */
    int available();

    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     */
    void close();

    /**
     * Returns the memory address of the next readable byte.
     *
     * @return the memory address.
     */
    long getNextByteAddress();

    /**
     * Returns the memory address of the current instruction.
     *
     * @return
     */
    long getInstructionAddress();

    /**
     * Reads the next byte of data from the input stream.
     *
     * @return the next byte of data.
     * @throws EOFException if the end of the stream is reached.
     */
    byte read() throws EOFException;

    /**
     * Reads some number of bytes from the input stream and stores them into the
     * buffer array buf.
     *
     * @param buf the buffer into which the data is read.
     * @return the total number of bytes read into the buffer, or -1 is there is
     * no more data because the end of the stream has been reached.
     */
    int read(byte[] buf);

    /**
     * Gets the next byte of data from the input stream without forwarding the
     * reading position.
     *
     * @return the next byte of data.
     * @throws EOFException if the end of the stream is reached.
     */
    byte peek() throws EOFException;

    /**
     * Gets some number of bytes from the input stream without forwarding the
     * reading position and stores them into the buffer array buf.
     *
     * @param buf the buffer into which the data is read.
     * @return the total number of bytes read into the buffer, or -1 is there is
     * no more data because the end of the stream has been reached.
     */
    int peek(byte[] buf);

    /**
     * Skips over and discards n bytes of data from this input stream.
     *
     * @param n the number of bytes to be skipped.
     * @return the actual number of bytes skipped.
     */
    long skip(long n);
    
    /**
     * Gets a list of bytes from the stream within the specified indices
     * @param start start index
     * @param end end index
     * @return list of bytes
     */
    List<Byte> getBytesWithinRange(int start, int end);
}
