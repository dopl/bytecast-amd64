package edu.syr.bytecast.amd64.impl.parser;

import java.io.EOFException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sheng
 */
public class InstructionByteListInputStreamTest {

    public static void assertTrue(boolean b) {
        if (!b) {
            throw new RuntimeException("Assert failed!");
        }
    }

    public static void main(String[] args) throws EOFException {
        System.out.println("Testing InstructionByteListInputStream");
        
        // Sample data.
        List<Byte> list = Arrays.asList((byte) 0x01, (byte) 0x02, (byte) 0x03,
                (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08,
                (byte) 0x09, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13,
                (byte) 0x14, (byte) 0x15);
        InstructionByteListInputStream instance = new InstructionByteListInputStream(list, 10000L);

        // Test gettings.
        assertTrue(instance.available() == 15);
        assertTrue(instance.getInstructionAddress() == 10000L);
        assertTrue(instance.getNextByteAddress() == 10000L);
        assertTrue(instance.getIndex() == 0);

        // Test peek()
        assertTrue(instance.peek() == (byte) 0x01);
        assertTrue(instance.peek() == (byte) 0x01);

        // Test gettings after peek().
        assertTrue(instance.available() == 15);
        assertTrue(instance.getInstructionAddress() == 10000L);
        assertTrue(instance.getNextByteAddress() == 10000L);
        assertTrue(instance.getIndex() == 0);

        // Test read()
        assertTrue(instance.read() == (byte) 0x01);
        assertTrue(instance.read() == (byte) 0x02);

        // Test gettings after read().
        assertTrue(instance.available() == 13);
        assertTrue(instance.getInstructionAddress() == 10000L);
        assertTrue(instance.getNextByteAddress() == 10002L);
        assertTrue(instance.getIndex() == 2);

        // Test updateInstructionAddress()
        instance.updateInstructionAddress();

        // Test gettings after updateInstructionAddress().
        assertTrue(instance.available() == 13);
        assertTrue(instance.getInstructionAddress() == 10002L);
        assertTrue(instance.getNextByteAddress() == 10002L);
        assertTrue(instance.getIndex() == 2);

        // Test peek(buf)
        byte[] buf = new byte[3];
        assertTrue(instance.peek(buf) == 3);
        assertTrue(buf[0] == (byte) 0x03);
        assertTrue(buf[1] == (byte) 0x04);
        assertTrue(buf[2] == (byte) 0x05);

        // Test gettings after peek(buf).
        assertTrue(instance.available() == 13);
        assertTrue(instance.getInstructionAddress() == 10002L);
        assertTrue(instance.getNextByteAddress() == 10002L);
        assertTrue(instance.getIndex() == 2);

        // Test read(buf)
        assertTrue(instance.read(buf) == 3);
        assertTrue(buf[0] == (byte) 0x03);
        assertTrue(buf[1] == (byte) 0x04);
        assertTrue(buf[2] == (byte) 0x05);

        // Test gettings after read(buf).
        assertTrue(instance.available() == 10);
        assertTrue(instance.getInstructionAddress() == 10002L);
        assertTrue(instance.getNextByteAddress() == 10005L);
        assertTrue(instance.getIndex() == 5);

        // Test close.
        instance.close();
        
        // Test gettings after close().
        assertTrue(instance.available() == 0);
        assertTrue(instance.getInstructionAddress() == 10002L);
        assertTrue(instance.getNextByteAddress() == 10015L);
        assertTrue(instance.getIndex() == 15);

        // Test EOFException
        try {
            instance.read();
            assertTrue(false);
        } catch (EOFException ex) {
            // excepted
        }

        // Test EOFException
        try {
            instance.peek();
            assertTrue(false);
        } catch (EOFException ex) {
            // excepted
        }

        assertTrue(instance.peek(buf) == 0);
        assertTrue(instance.read(buf) == 0);

        // Test setIndex(int)
        instance.setIndex(14);
        assertTrue(instance.peek(buf) == 1);
        assertTrue(instance.available() == 1);
        assertTrue(instance.getInstructionAddress() == 10002L);
        assertTrue(instance.getNextByteAddress() == 10014L);
        assertTrue(instance.getIndex() == 14);

        assertTrue(instance.read(buf) == 1);
        assertTrue(instance.available() == 0);
        assertTrue(instance.getInstructionAddress() == 10002L);
        assertTrue(instance.getNextByteAddress() == 10015L);
        assertTrue(instance.getIndex() == 15);


    }
}
