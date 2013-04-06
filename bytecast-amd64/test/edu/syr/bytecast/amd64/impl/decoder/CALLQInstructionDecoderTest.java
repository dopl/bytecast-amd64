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
 * @author bytecast
 * e8 8d ff ff ff       	callq  40050f <dostuff>
 * can not test yet since dictionary and address are not error when complie.
 */
public class CALLQInstructionDecoderTest {
     public static void assertTrue(IInstruction instruction, Object operand1, Object operand2) {
    if (!instruction.getOperands().get(0).getOperandValue().equals(operand1)
      || instruction.getOperands().get(1).getOperandValue()!= operand2)
 
      // Can not get section name now, do not test yet  
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
      
      

  @Test
  public void main() throws EOFException {
    System.out.println("Testing CALLQInstructionDecoder");

    // Test data.
    //e8 8d ff ff ff       	callq  40050f <dostuff>
    List<Byte> list = Arrays.asList(
            (byte) 0xe8 , (byte) 0x8d, (byte) 0xff, (byte) 0xff, (byte) 0xff);
    InstructionByteListInputStream stream = new InstructionByteListInputStream(list, (long)0x40057d);

    CALLQInstructionDecoder CALLQDcoder = new CALLQInstructionDecoder();
    IInstructionContext context = getContext(stream);

    IInstruction callqInstruction = CALLQDcoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) callqInstruction));
    
    assertTrue(callqInstruction , new OperandTypeMemoryEffectiveAddress(null,null,1,(long)0x40050f), "SectionName");
    stream.updateInstructionAddress();
  }
}
