/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

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

/**
 *
 * @author bytecast
 */
/*
 * 4004fd:	48 83 c0 08          	add    $0x8,%rax
 */
public class ADDInstructionDecoderTest {
      public static void assertTrue(IInstruction instruction, Object operand1, Object operand2) {
    if (instruction.getOperands().get(0).getOperandValue() != operand1
      || instruction.getOperands().get(1).getOperandValue() != operand2)
      throw new RuntimeException("Assert failed!");
    
  }
      
      private static final List<Byte> LEGACY_PREFIX_LIST = Arrays.asList(
            (byte) 0x66, (byte) 0x67, (byte) 0xf0, (byte) 0xf3, (byte) 0xf2,
            (byte) 0x2e, (byte) 0x3Ee, (byte) 0x26, (byte) 0x64, (byte) 0x65,
            (byte) 0x36);
      
      static IInstructionContext getContext(IInstructionByteInputStream input) throws EOFException {
        IInstructionContext context = new InstructionContextImpl();
        byte b = input.peek();

        // Parse legacy prefixes.
        while (LEGACY_PREFIX_LIST.contains(b)) {
            ParserFactory.getLegacyPrefixParser().parse(context, input);
            b = input.peek();
        }

        // Parse REX prefix
        if (b >= (byte) 0x40 && b <= (byte) 0x4f) {
            ParserFactory.getRexPrefixParser().parse(context, input);
        }

        return context;
    }
      
      

  public static void main(String[] args) throws EOFException {
    System.out.println("Testing ADDInstructionDecoder");

    // Test data.
    //40055b:	48 83 c0 10          	add    $0x10,%rax
    List<Byte> list = Arrays.asList(
            (byte) 0x48 , (byte) 0x83, (byte) 0xC0, (byte) 0x08,
            (byte) 0x48 , (byte) 0x83, (byte) 0xC0, (byte) 0x10 );// JGE  10019
    InstructionByteListInputStream stream = new InstructionByteListInputStream(list, 10000L);

    ADDInstructionDecoder ADDDcoder = new ADDInstructionDecoder();
    IInstructionContext context = getContext(stream);
    IInstruction addInstruction = null;

    //4004fd:	48 83 c0 08          	add    $0x8,%rax
    addInstruction = ADDDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) addInstruction));
    assertTrue(addInstruction , 8L, RegisterType.RAX);
    stream.updateInstructionAddress();
    
    context = getContext(stream);
    addInstruction = ADDDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) addInstruction));
    assertTrue(addInstruction , 16L, RegisterType.RAX);
    stream.updateInstructionAddress();
    
    // JBE 1003
  }
}
