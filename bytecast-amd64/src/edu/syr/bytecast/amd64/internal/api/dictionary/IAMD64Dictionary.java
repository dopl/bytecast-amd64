/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.internal.api.dictionary;

/**
 *
 * @author Harsh
 */
public interface IAMD64Dictionary {
    
    public boolean isLegacyOpcode(Byte opcode);
    public boolean isRexPrefix(Byte opcode);
    public boolean isEscapeToSecondaryOpCode(Byte opcode);
    
}
