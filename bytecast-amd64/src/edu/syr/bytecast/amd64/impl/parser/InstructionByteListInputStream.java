package edu.syr.bytecast.amd64.impl.parser;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to encapsulate a byte list into a
 * {@link IInstructionByteInputStream}.
 *
 * @author sheng
 */
public class InstructionByteListInputStream implements IInstructionByteInputStream {

    /**
     * The list to provide bytes.
     */
    private List<Byte> list;
    /**
     * The initial address of the bytes. It is the address of the first byte in
     * the list.
     */
    private long initialAddress;
    /**
     * The index of the next byte in the list.
     */
    private int index;
    /**
     * The address of the current instruction.
     */
    private long instructionAddress;

    /**
     * Construct an object.
     *
     * @param list a list to provide bytes.
     * @param initialAddress The initial address of the bytes. It is the address
     * of the first byte in the list.
     */
    public InstructionByteListInputStream(List<Byte> list, long initialAddress) {
        this.list = list;
        this.initialAddress = initialAddress;
        instructionAddress = initialAddress;
    }

    @Override
    public int available() {
        return list.size() - index;
    }

    @Override
    public void close() {
        // Move the index to the end.
        index = list.size();
    }

    @Override
    public long getNextByteAddress() {
        return initialAddress + index;
    }

    @Override
    public long getInstructionAddress() {
        return instructionAddress;
    }

    /**
     * Update the instruction address using {@link #getNextByteAddress() }.
     */
    public void updateInstructionAddress() {
        instructionAddress = getNextByteAddress();
    }

    @Override
    public byte read() throws EOFException {
        if (index >= list.size()) {
            throw new EOFException();
        }
        // Get one byte and increase index. 
        return list.get(index++);
    }

    @Override
    public int read(byte[] buf) {
        if (buf.length == 0) {
            return 0;
        }
        // Get bytes and increase index.
        int i;
        for (i = 0; i < buf.length && index < list.size(); i++) {
            buf[i] = list.get(index);
            index++;
        }
        return i;
    }

    @Override
    public byte peek() throws EOFException {
        if (index >= list.size()) {
            throw new EOFException();
        }
        // Get one byte without increasing index.
        return list.get(index);
    }

    @Override
    public int peek(byte[] buf) {
        if (buf.length == 0) {
            return 0;
        }
        // Get bytes without increasing index.
        int i;
        for (i = 0; i < buf.length && i + index < list.size(); i++) {
            buf[i] = list.get(i + index);
        }
        return i;
    }

    @Override
    public long skip(long n) {
        if (index + n <= list.size()) {
            // Has enough data to skip.
            index += n;
            return n;
        } else {
            // Don't have enough data to skip. Move index to the end.
            int ret = list.size() - index;
            index = list.size();
            return ret;
        }
    }

    /**
     * Return the index of the next byte in the list.
     *
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the index of the next byte in the list.
     *
     * @param index
     * @throws IndexOutOfBoundsException if the index is smaller than zero or
     * bigger than the list size.
     */
    public void setIndex(int index) {
        if (index < 0 || index > list.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.index = index;
    }

    @Override
    public List<Byte> getBytesWithinRange(int start, int end) {
        if (index < 0 || index > list.size()) {
            throw new IndexOutOfBoundsException();
        }
        return  list.subList(start, end);
    }
}
