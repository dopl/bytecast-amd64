/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import edu.syr.bytecast.amd64.impl.parser.InstructionByteListInputStream;
import edu.syr.bytecast.amd64.impl.parser.InstructionTestUtils;
import java.io.EOFException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hapan
 */
public class JCCInstructionDecoderTest {

  public static void assertTrue(IInstruction instruction, long addr) {
    if (((OperandTypeMemoryEffectiveAddress) instruction.getOperands().get(0).getOperandValue()).getOffset() != addr) {
      throw new RuntimeException("Assert failed!");
    }
  }

  public static void main(String[] args) throws EOFException {
    System.out.println("Testing JCCInstructionDecoder");

    // Test data.
    List<Byte> list = Arrays.asList(
            (byte) 0x70, (byte) 0x01, // JO   0x2713
            (byte) 0x76, (byte) 0xFF, // JBE  0x2713
            (byte) 0x7C, (byte) 0x01, // JL   0x2717
            (byte) 0x0F, (byte) 0x81, (byte) 0x01, (byte) 0x00, // JNO  0x271B
            (byte) 0x0F, (byte) 0x87, (byte) 0xFF, (byte) 0xFF, // JA   0x271D
            (byte) 0x0F, (byte) 0x8D, (byte) 0x01, (byte) 0x00);// JGE  0x2723
    InstructionByteListInputStream stream = new InstructionByteListInputStream(list, 10000L);

    JCCInstructionDecoder JCCDcoder = new JCCInstructionDecoder();
    IInstructionContext context = new InstructionContextImpl();
    IInstruction jccInstruction = null;

    // JO 0x2713
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));
    assertTrue(jccInstruction, 0x2713);
    stream.updateInstructionAddress();
    
    // JBE 0x2713
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));
    assertTrue(jccInstruction, 0x2713);
    stream.updateInstructionAddress();
    
    // JL 0x2717
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));
    assertTrue(jccInstruction, 0x2717);
    stream.updateInstructionAddress();
    
    // JNO 0x271B
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));
    assertTrue(jccInstruction, 0x271B);
    stream.updateInstructionAddress();
    
    // JA 0x271D
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));
    assertTrue(jccInstruction, 0x271D);
    stream.updateInstructionAddress();
    
    // JGE  0x2723
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));
    assertTrue(jccInstruction, 0x2723);
    stream.updateInstructionAddress();
    
    System.out.println("JCCInstructionDecoder Test Passed");
  }
}
