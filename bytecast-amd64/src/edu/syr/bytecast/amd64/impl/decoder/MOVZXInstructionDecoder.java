/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_16;
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_32;
import static edu.syr.bytecast.amd64.impl.instruction.IInstructionContext.OperandOrAddressSize.SIZE_64;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class MOVZXInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        

        // Parse opcode. See AMD64, volume 3, page 232 (page 266 of pdf).
        if (b == (byte) 0x0F) {
            b = input.read();
            if (b == (byte) 0xB6) {
                // Description: Move the contents of an 8-bit register or memory
                //     operand to a 16-bit register with zero-extension.
                // Mnemonic:    MOVZX reg16, reg/mem8
                // Opcode:      0F B6 /r

                // Description: Move the contents of an 8-bit register or memory
                //     operand to a 32-bit register with zero-extension.
                // Mnemonic:    MOVZX reg32, reg/mem8
                // Opcode:      0F B6 /r

                // Description: Move the contents of an 8-bit register or memory
                //     operand to a 64-bit register with zero-extension.
                // Mnemonic:    MOVZX reg64, reg/mem8
                // Opcode:      0F B6 /r
                   // Create the ret
                 AMD64Instruction ret = new AMD64Instruction(InstructionType.MOVZBL);
                ret.setOpCode("0F B6");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                IModRmParser.RegType reg_type;
                switch (context.getOperandSize()) {
                    case SIZE_16:
                        reg_type = IModRmParser.RegType.REG16;
                        break;
                    case SIZE_32:
                        reg_type = IModRmParser.RegType.REG32;
                        break;
                    case SIZE_64:
                        reg_type = IModRmParser.RegType.REG64;
                        break;
                    default:
                        throw new RuntimeException("Unsupported operand size.");
                }
                rm_parser.parse(context, input, reg_type, IModRmParser.RmType.REG_MEM8);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (b == (byte) 0xB7) {
                // Description: Move the contents of an 16-bit register or memory
                //     operand to a 32-bit register with zero-extension.
                // Mnemonic:    MOVZX reg32, reg/mem16
                // Opcode:      0F B7 /r

                // Description: Move the contents of an 16-bit register or memory
                //     operand to a 64-bit register with zero-extension.
                // Mnemonic:    MOVZX reg64, reg/mem16
                // Opcode:      0F B7 /r
                   // Create the ret
                AMD64Instruction ret = new AMD64Instruction(InstructionType.MOVZX); 
                ret.setOpCode("0F B7");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                IModRmParser.RegType reg_type;
                switch (context.getOperandSize()) {
                    case SIZE_32:
                        reg_type = IModRmParser.RegType.REG32;
                        break;
                    case SIZE_64:
                        reg_type = IModRmParser.RegType.REG64;
                        break;
                    default:
                        throw new RuntimeException("Unsupported operand size.");
                }
                rm_parser.parse(context, input, reg_type, IModRmParser.RmType.REG_MEM16);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else {
                throw new RuntimeException("Unknown opcode!");
            }
        } else {
            throw new RuntimeException("Unknown opcode!");
        }
    }
}
