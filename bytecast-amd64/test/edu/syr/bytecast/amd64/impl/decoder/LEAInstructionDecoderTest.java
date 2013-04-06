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
public class LEAInstructionDecoderTest {
  
  public static void assertTrue(IInstruction instruction, OperandTypeMemoryEffectiveAddress eaddr, RegisterType reg) {
    if (!((OperandTypeMemoryEffectiveAddress) instruction.getOperands().get(0).getOperandValue()).equals(eaddr)) {
      throw new RuntimeException("Assert failed!");
    } else if(((RegisterType)instruction.getOperands().get(1).getOperandValue()) != reg) {
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
  public void LEATest() throws EOFException {
    System.out.println("Testing LEAInstructionDecoder");
    
    // Test data.
    List<Byte> list = Arrays.asList(
            // lea 0x20017b(%rip),%rbp
            (byte) 0x48, (byte) 0x8d, (byte) 0x2d, (byte) 0x7b, (byte) 0x01, (byte) 0x20, (byte) 0x00,
            // lea 0x200174(%rip),%r12
            (byte) 0x4c, (byte) 0x8d, (byte) 0x25, (byte) 0x74, (byte) 0x01, (byte) 0x20, (byte) 0x00,
            // lea (%rdx,%rax,1),%eax
            (byte) 0x8d, (byte) 0x04, (byte) 0x02,
            // lea 0xe0(%rsp),%rax
            (byte) 0x48, (byte) 0x8d, (byte) 0x84, (byte) 0x24, (byte) 0xe0, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            // lea (%rax,%r12,1),%r12d
            (byte) 0x46, (byte) 0x8d, (byte) 0x24, (byte) 0x20);
    InstructionByteListInputStream stream = new InstructionByteListInputStream(list, 10000L);

    LEAInstructionDecoder LEADcoder = new LEAInstructionDecoder();
    IInstruction leaInstruction = null;
    OperandTypeMemoryEffectiveAddress eaddr = null;
    
    // lea 0x20017b(%rip),%rbp
    IInstructionContext context = getContext(stream);
    leaInstruction = LEADcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(RegisterType.RIP, null, 1, 0x20017b);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) leaInstruction));
    assertTrue(leaInstruction, eaddr, RegisterType.RBP);
    stream.updateInstructionAddress();
    
    // lea 0x200174(%rip),%r12
    context = getContext(stream);
    leaInstruction = LEADcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(RegisterType.RIP, null, 1, 0x200174);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) leaInstruction));
    assertTrue(leaInstruction, eaddr, RegisterType.R12);
    stream.updateInstructionAddress();
    
    // lea (%rdx,%rax,1),%eax
    context = getContext(stream);
    leaInstruction = LEADcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(RegisterType.RDX, RegisterType.RAX, 1, 0);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) leaInstruction));
    assertTrue(leaInstruction, eaddr, RegisterType.EAX);
    stream.updateInstructionAddress();
    
    // lea 0xe0(%rsp),%rax
    context = getContext(stream);
    leaInstruction = LEADcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(RegisterType.RSP, null, 1, 0xE0);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) leaInstruction));
    assertTrue(leaInstruction, eaddr, RegisterType.RAX);
    stream.updateInstructionAddress();
    
    // lea (%rax,%r12,1),%r12d
    context = getContext(stream);
    leaInstruction = LEADcoder.decode(context, stream);
    eaddr = new OperandTypeMemoryEffectiveAddress(RegisterType.RAX, RegisterType.R12, 1, 0);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) leaInstruction));
    assertTrue(leaInstruction, eaddr, RegisterType.R12D);
    stream.updateInstructionAddress();
    
    System.out.println("LEAInstructionDecoder Test passed");
  }
}
