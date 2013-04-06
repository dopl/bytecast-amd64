/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.InstructionByteListInputStream;
import edu.syr.bytecast.amd64.impl.parser.InstructionTestUtils;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import java.io.EOFException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author hapan
 */
public class JMPInstructionDecoderTest {

  public static void assertTrue(IInstruction instruction, OperandTypeMemoryEffectiveAddress eaddr) {
    if (!((OperandTypeMemoryEffectiveAddress) instruction.getOperands().get(0).getOperandValue()).equals(eaddr)) {
      throw new RuntimeException("Assert failed!");
    }
  }

  static IInstructionContext getContext(IInstructionByteInputStream input) throws EOFException {
        IInstructionContext context = new InstructionContextImpl();
        byte b = input.peek();

        // Parse REX prefix
        if (b >= (byte) 0x40 && b <= (byte) 0x4f) {
            ParserFactory.getRexPrefixParser().parse(context, input);
        }
        return context;
    }
  
  @Test
  public void JMPTest() throws EOFException {
    System.out.println("Testing JMPInstructionDecoder");

    // Test data.
    List<Byte> list = Arrays.asList(
            // jmp 0x2713
            (byte) 0xEB, (byte) 0x01,
            // jmp 0x2713
            (byte) 0xEB, (byte) 0xFF,
            // jmp 0x271A
            (byte) 0xE9, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            // jmp 0x271D
            (byte) 0xE9, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            // jmp *%rax
            (byte) 0xFF, (byte) 0xE0,
            // jmp *0x2004a4(%rip)
            (byte) 0xFF, (byte) 0x25, (byte) 0xA4, (byte) 0x04, (byte) 0x20, (byte) 0x00,
            // jmp *0x486640(,%r12,8)
            (byte) 0x42, (byte) 0xff, (byte) 0x24, (byte) 0xe5, (byte) 0x40, (byte) 0x66, (byte) 0x48, (byte) 0x00);
    InstructionByteListInputStream stream = new InstructionByteListInputStream(list, 10000L);

    JMPInstructionDecoder JMPDcoder = new JMPInstructionDecoder();
    IInstruction jmpInstruction = null;
    OperandTypeMemoryEffectiveAddress eaddr = null;

    // jmp 0x2713
    IInstructionContext context = getContext(stream);
    jmpInstruction = JMPDcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(null, null, 1, 0x2713);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jmpInstruction));
    assertTrue(jmpInstruction, eaddr);
    stream.updateInstructionAddress();
    
    // jmp 0x2713
    context = getContext(stream);
    jmpInstruction = JMPDcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(null, null, 1, 0x2713);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jmpInstruction));
    assertTrue(jmpInstruction, eaddr);
    stream.updateInstructionAddress();
    
    // jmp 0x271A
    context = getContext(stream);
    jmpInstruction = JMPDcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(null, null, 1, 0x271A);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jmpInstruction));
    assertTrue(jmpInstruction, eaddr);
    stream.updateInstructionAddress();
    
    // jmp 0x271D
    context = getContext(stream);
    jmpInstruction = JMPDcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(null, null, 1, 0x271D);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jmpInstruction));
    assertTrue(jmpInstruction, eaddr);
    stream.updateInstructionAddress();
    
    // jmp *%rax
    context = getContext(stream);
    jmpInstruction = JMPDcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(RegisterType.RAX, null, 1, 0);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jmpInstruction));
    assertTrue(jmpInstruction, eaddr);
    stream.updateInstructionAddress();
    
    // jmp *0x2004a4(%rip)
    context = getContext(stream);
    jmpInstruction = JMPDcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(RegisterType.RIP, null, 1, 0x2004a4);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jmpInstruction));
    assertTrue(jmpInstruction, eaddr);
    stream.updateInstructionAddress();
    
    // jmp *0x486640(,%r12,8)
    context = getContext(stream);
    jmpInstruction = JMPDcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(null, RegisterType.R12, 8, 0x486640);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jmpInstruction));
    assertTrue(jmpInstruction, eaddr);
    stream.updateInstructionAddress();

    System.out.println("JMPInstructionDecoder Test passed");
  }
}
