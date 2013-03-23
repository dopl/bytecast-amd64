/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
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
public class LEAVEInstructionDecoderTest {
  
  public static void main(String[] args) throws EOFException {
    System.out.println("Testing LEAVEInstructionDecoder");

    // Test data.
    List<Byte> list = Arrays.asList(
            (byte) 0xC9, (byte)0xC9, (byte)0xC9);
    InstructionByteListInputStream stream = new InstructionByteListInputStream(list, 10000L);
    
    LEAVEInstructionDecoder LEAVEDecoder = new LEAVEInstructionDecoder();
    IInstructionContext context = new InstructionContextImpl();
    IInstruction leaveInstruction = null;
    
    leaveInstruction = LEAVEDecoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) leaveInstruction));
    if(leaveInstruction.getInstructiontype() != InstructionType.LEAVE) {
      throw new RuntimeException("Test Failed!");
    }
    stream.updateInstructionAddress();
    
    leaveInstruction = LEAVEDecoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) leaveInstruction));
    if(leaveInstruction.getInstructiontype() != InstructionType.LEAVE) {
      throw new RuntimeException("Test Failed!");
    }
    stream.updateInstructionAddress();
    
    leaveInstruction = LEAVEDecoder.decode(context, stream);
    System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) leaveInstruction));
    if(leaveInstruction.getInstructiontype() != InstructionType.LEAVE) {
      throw new RuntimeException("Test Failed!");
    }
    stream.updateInstructionAddress();
    
    System.out.println("LEAVEInstructionDecoder Test Passed");
  }
  
}
