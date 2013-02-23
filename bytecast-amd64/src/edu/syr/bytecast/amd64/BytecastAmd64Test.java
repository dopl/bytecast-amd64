/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64;

import edu.syr.bytecast.amd64.util.DecoderUtil;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.decoder.DecoderFactory;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.decoder.JCCInstructionDecoder;
import edu.syr.bytecast.amd64.impl.decoder.JMPInstructionDecoder;
import edu.syr.bytecast.amd64.impl.decoder.LEAVEInstructionDecoder;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hapan
 */
public class BytecastAmd64Test {
  
  public static void main(String[] args) {
        // initialize veriables 
    Long instructionMemAddr = (long)0x4004a4;
    List<Byte> instructionbytes = new ArrayList<Byte>();
    
    /*
    // test JCCInstrcutionDecoder
    // test 8 offset
    JCCInstructionDecoder jccInstructionDecoder = new JCCInstructionDecoder();
    instructionbytes.clear();
    instructionbytes.add((byte)0x75);
    instructionbytes.add((byte)0xFF);
    IInstruction jccInstruction = jccInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // test 8 offset with prefix
    jccInstructionDecoder = new JCCInstructionDecoder();
    instructionbytes.clear();
    instructionbytes.add((byte)0x67);
    instructionbytes.add((byte)0x75);
    instructionbytes.add((byte)0xFF);
    jccInstruction = jccInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // test 16 offset
    instructionbytes.clear();
    instructionbytes.add((byte)0x0F);
    instructionbytes.add((byte)0x85);
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0xFF);
    jccInstruction = jccInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // test 32 offset
    instructionbytes.clear();
    instructionbytes.add((byte)0x0F);
    instructionbytes.add((byte)0x85);
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0xFF);
    jccInstruction = jccInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    */
    
    /*
    // test LEAVEInstructionDecoder
    LEAVEInstructionDecoder leaveInstructionDecoder = new LEAVEInstructionDecoder();
    instructionbytes.clear();
    instructionbytes.add((byte)0xC9);
    IInstruction leaveInstruction = leaveInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    */
    
    // test JMPInstructionDecoder
    JMPInstructionDecoder jmpInstructionDecoder = new JMPInstructionDecoder();
    // jump to register
    instructionbytes.clear();
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0xE0);
    //IInstruction jmpInstruction = jmpInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
    
    // jump to memory address
    instructionbytes.clear();
    instructionbytes.add((byte)0xFF);
    instructionbytes.add((byte)0x25);
    instructionbytes.add((byte)0xA4);
    instructionbytes.add((byte)0x04);
    instructionbytes.add((byte)0x20);
    instructionbytes.add((byte)0x00);
    //jmpInstruction = jmpInstructionDecoder.decodeInstruction(instructionMemAddr, instructionbytes);
  }
  
  public BytecastAmd64Test()
  {

    
  }
}
