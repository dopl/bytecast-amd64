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

import edu.syr.bytecast.amd64.util.DecoderUtil;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IImmParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.IRegImmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;
import java.util.List;

public class ANDInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {

        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.ADD);

        // AND AL,imm8
        if (b == (byte) 0x83) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("83");
                IRegImmParser ri_parser = ParserFactory.getRegImmParser();
                ri_parser.parse(context, input, IRegImmParser.RegType.REG64, IRegImmParser.Type.IMM64);
                ret.addOperand(ri_parser.getRegOperand());
                ret.addOperand(ModifyOperand(ri_parser.getImmOperand()));
                return ret;
            }
        }
        throw new UnsupportedOperationException("just for one and case, not recongzise this one");
    }

    OperandConstant ModifyOperand(OperandConstant operand) throws EOFException {        
        if( operand.getOperandValue() instanceof Long){
            Long tmp = (Long)operand.getOperandValue() + 0xffffffffffffff00L;
            return new OperandConstant(tmp);
        }
        throw new RuntimeException("AND ModifyOperand Cast Fail.");
    }
}

