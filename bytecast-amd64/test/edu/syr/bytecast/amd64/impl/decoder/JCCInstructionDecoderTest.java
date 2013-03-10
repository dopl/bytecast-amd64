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
            (byte) 0x70, (byte) 0x01, // JO   10003
            (byte) 0x76, (byte) 0xFF, // JBE  10003
            (byte) 0x7C, (byte) 0x01, // JL   10007
            (byte) 0x0F, (byte) 0x81, (byte) 0x01, (byte) 0x00, // JNO  10011
            (byte) 0x0F, (byte) 0x87, (byte) 0xFF, (byte) 0xFF, // JA   10013
            (byte) 0x0F, (byte) 0x8D, (byte) 0x01, (byte) 0x00);// JGE  10019
    InstructionByteListInputStream stream = new InstructionByteListInputStream(list, 10000L);

    JCCInstructionDecoder JCCDcoder = new JCCInstructionDecoder();
    IInstructionContext context = new InstructionContextImpl();
    IInstruction jccInstruction = null;

    // JO 10003
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));
    assertTrue(jccInstruction, 10003);
    stream.updateInstructionAddress();
    
    // JBE 1003
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));;
    assertTrue(jccInstruction, 10003);
    stream.updateInstructionAddress();
    
    // JL 1007
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));;
    assertTrue(jccInstruction, 10007);
    stream.updateInstructionAddress();
    
    // JNO 10011
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));;
    assertTrue(jccInstruction, 10011);
    stream.updateInstructionAddress();
    
    // JA 10013
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));;
    assertTrue(jccInstruction, 10013);
    stream.updateInstructionAddress();
    
    // JGE  10019
    jccInstruction = JCCDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) jccInstruction));;
    assertTrue(jccInstruction, 10019);
    stream.updateInstructionAddress();
  }
}
