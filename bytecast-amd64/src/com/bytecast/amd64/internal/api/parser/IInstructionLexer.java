/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.internal.api.parser;

import edu.syr.bytecast.fsys.ExeObjSegment;
import java.util.List;

/**
 *
 * @author Harsh
 */
public interface IInstructionLexer {
    
    List<Byte> getTokenizedInstructionBytes(ExeObjSegment segment);

}
