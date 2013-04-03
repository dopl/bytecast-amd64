/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.amd64.internal.api.parser;

import java.util.List;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import java.util.Set;


public interface IInstructionLexer {
    
    public List<MemoryInstructionPair> convertInstructionBytesToObjects(Long sectionStartMemeAddress, List<Byte> bytes );
    /**
     * This method is used to set a list of functions that the lexer can ignore going into.
     * @param excludedFunctions 
     */
    public void setFunctionsToBeExcluded(Set<String> excludedFunctions);
    
    /**
     * The listener can be used to trigger notifications when the lexer encounters a function call
     * @param fnCallListener 
     */
    public void registerFnCallListener(IFunctionCallListener fnCallListener);
}
