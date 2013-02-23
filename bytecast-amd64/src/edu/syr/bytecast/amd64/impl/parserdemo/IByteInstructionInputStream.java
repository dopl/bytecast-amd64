/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.amd64.impl.parserdemo;

import java.io.EOFException;

/**
 * An interface to read instruction bytes. It also support peeking and pushing
 * back.
 *
 * @author sheng
 */
public interface IByteInstructionInputStream {

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
     * Returns the member address of the next readable byte.
     *
     * @return the member address.
     */
    long getMemberAddress();

    /**
     * Reads the next byte of data from the input stream.
     *
     * @return the next byte of data.
     * @throws EOFException if the end of the stream is reached.
     */
    byte read() throws EOFException;

    /**
     * Reads some number of bytes from the input stream and stores them into the
     * buffer array b.
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
     * reading position and stores them into the buffer array b.
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
}
