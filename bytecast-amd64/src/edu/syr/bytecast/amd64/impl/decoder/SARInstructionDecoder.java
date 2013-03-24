/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.parser.IImmParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.IRegImmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

/**
 *
 * @author bytecast
 * 
 * 40050b:	d1 f8      sar    %eax
 */
public class SARInstructionDecoder  implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {

        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.SAR);

        // AND AL,imm8
        if (b == (byte) 0xD1) {
            ret.setOpCode("D1");

            IModRmParser imr_parser = ParserFactory.getModRmParser();
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                
                imr_parser.parse(context, input, IModRmParser.RegType.REG16, IModRmParser.RmType.REG_MEM16);
                
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {

                imr_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);

            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {

                imr_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);

            } else {
                throw new RuntimeException("Unknown operand size.");
            }
            if (imr_parser.getReg() != 7) {
                throw new RuntimeException("This is not an SAR instruction");
            }
            
          
            // add imm first and reg second
            ret.addOperand(imr_parser.getRmOperand());
            //ret.addOperand(new OperandConstant(1L));
            return ret;
        }
        throw new UnsupportedOperationException("just for one and case, not recongzise this one");
    }

}
