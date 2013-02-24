/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;
import java.util.List;

/**
 *
 * @author hapan
 */
public class INCInstructionDecoder implements IInstructionDecoder {
  
  private byte lock = (byte)0xF0;
  private byte group5 = (byte)0xFF;

  @Override
  public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
}
