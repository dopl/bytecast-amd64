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

package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IAddressFunctionParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import edu.syr.bytecast.amd64.util.DecoderUtil;
import java.io.EOFException;
import java.util.List;

public class CALLQInstructionDecoder implements IInstructionDecoder{

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {    
        //_instructionMemAddress = instructionMemAddress;
        //decodeOperands(instruction, instructionbytes);
        AMD64Instruction ret = new AMD64Instruction(InstructionType.CALLQ);
        byte b = input.read();
        
        if(b == (byte) 0xE8){            
            ret.setOpCode("E8");
            IAddressFunctionParser iaf_parser = ParserFactory.getAddressFunctionParser();
            iaf_parser.parse(context, input);
            ret.addOperand(iaf_parser.getAddressOperand());
            ret.addOperand(iaf_parser.getSectionNameOperand());
            return ret;
        } else if (b == (byte) 0xff) {
            ret.setOpCode("FF");
            IModRmParser imr_parser = ParserFactory.getModRmParser();
//            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
//                
//                imr_parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
//                
//            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
//
//                imr_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
//
//            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {

                imr_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);

//            } else {
//                throw new RuntimeException("Unknown operand size.");
//            }
            ret.addOperand(imr_parser.getRmOperand());
            return ret;
        } else if(b == (byte) 0x41){
            throw new UnsupportedOperationException("Not supported CALLQ instruction Opcode 41");
        } 
        
        
        throw new UnsupportedOperationException("TODO");
    }
    
   
}
