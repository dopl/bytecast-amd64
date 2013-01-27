/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.impl.lexer;

import com.bytecast.amd64.internal.api.parser.IInstructionLexer;
import edu.syr.bytecast.fsys.ExeObjSegment;
import java.util.List;

/**
 *
 * @author Harsh
 */
public class AMD64InstructionLexer implements IInstructionLexer {

    public AMD64InstructionLexer() {
    }

    @Override
    public List<Byte> getTokenizedInstructionBytes(ExeObjSegment segment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
  
    
    
}
