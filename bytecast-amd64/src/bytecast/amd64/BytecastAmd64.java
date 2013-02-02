/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bytecast.amd64;

import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.impl.decoder.JCCInstructionDecoder;
import com.bytecast.amd64.impl.decoder.JMPInstructionDecoder;
import com.bytecast.amd64.impl.decoder.LEAVEInstructionDecoder;
import com.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author harsh
 */
public class BytecastAmd64 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    

    // initialize veriables 
    Long instructionMemAddr = (long)0x40043a;
    List<Byte> instructionbytes = new ArrayList<Byte>();
    
    // test JCCInstrcutionDecoder
    JCCInstructionDecoder jccInstructionDecoder = new JCCInstructionDecoder();
    instructionbytes.clear();
    instructionbytes.add((byte)0x75);
    instructionbytes.add((byte)0x02);
    IInstruction jccInstruction = jccInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // test LEAVEInstructionDecoder
    LEAVEInstructionDecoder leaveInstructionDecoder = new LEAVEInstructionDecoder();
    instructionbytes.clear();
    instructionbytes.add((byte)0xC9);
    IInstruction leaveInstruction = leaveInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // test JMPInstructionDecoder
    JMPInstructionDecoder jmpInstructionDecoder = new JMPInstructionDecoder();
    // jump to register
    instructionbytes.clear();
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0xE0);
    IInstruction jmpInstruction = jmpInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // jump to memory address
    instructionbytes.clear();
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0x25);
    instructionbytes.add((byte)0xA4);
    instructionbytes.add((byte)0x04);
    instructionbytes.add((byte)0x20);
    instructionbytes.add((byte)0x00);
    jmpInstruction = jmpInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
  }

  public BytecastAmd64()
  {
    
  }
    
    
 
    
}