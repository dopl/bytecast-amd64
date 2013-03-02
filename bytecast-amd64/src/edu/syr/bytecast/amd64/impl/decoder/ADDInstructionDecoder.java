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
/**
 *
 * @author Chen Qian
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.parser.IImmParser;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.IModRmParser;
import edu.syr.bytecast.amd64.impl.parser.IMoffsetParser;
import edu.syr.bytecast.amd64.impl.parser.IRegImmParser;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import edu.syr.bytecast.amd64.internal.api.parser.IInstructionDecoder;
import java.io.EOFException;

public class ADDInstructionDecoder implements IInstructionDecoder {

    @Override
    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        byte b = input.read();

        // Create the ret
        AMD64Instruction ret = new AMD64Instruction(InstructionType.ADD);

        // ADD AL,imm8
        if (b == (byte) 0x04) {
            ret.setOpCode("04");
            IImmParser imm_parser = ParserFactory.getImmParser();
            imm_parser.parse(input, IImmParser.Type.IMM8);
            ret.addOperand(imm_parser.getOperand());
            ret.addOperand(new OperandRegister(RegisterType.AL));
            return ret;
        } else if (b == (byte) 0x05) { //ADD 
            ret.setOpCode("05");
            IImmParser imm_parser = ParserFactory.getImmParser();
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                imm_parser.parse(input, IImmParser.Type.IMM32);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                return ret;
            } else if (context.getAddressSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                imm_parser.parse(input, IImmParser.Type.IMM64);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                return ret;
            } else if (context.getAddressSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                imm_parser.parse(input, IImmParser.Type.IMM16);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.AX));
                return ret;
            } 
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0x80) {
                //TODO ADD reg/mem8, imm8
        } else if (b == (byte) 0x81) {
        } else if (b == (byte) 0x83) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                ret.setOpCode("83");
                IRegImmParser ri_parser = ParserFactory.getRegImmParser();
                ri_parser.parse(context, input, IRegImmParser.RegType.REG16, IRegImmParser.Type.IMM16);
                ret.addOperand(ri_parser.getRegOperand());
                ret.addOperand(ri_parser.getImmOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("83");
                // TODO We should get a rm parser rather than creating an instance.
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRegOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of a 64-bit register to a 64-bit
                //     destination register or memory operand.
                // Mnemonic:    MOV reg/mem64, reg64
                // Opcode:      89 /r
                ret.setOpCode("89");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
                ret.addOperand(rm_parser.getRegOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0x8A) {
            // Description: Move the contents of an 8-bit register or memory
            //     operand to an 8-bit destination register.
            // Mnemonic:    MOV reg8, reg/mem8
            // Opcode:      8A /r
            // TODO MOV reg8, reg/mem8      8A /r
        } else if (b == (byte) 0x8B) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // Description: Move the contents of an 16-bit register or memory
                //     operand to an 16-bit destination register.
                // Mnemonic:    MOV reg16, reg/mem16
                // Opcode:      8B /r
                // TODO MOV reg16, reg/mem16      8B /r
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                // Description: Move the contents of an 32-bit register or memory
                //     operand to an 32-bit destination register.
                // Mnemonic:    MOV reg32, reg/mem32
                // Opcode:      8B /r
                ret.setOpCode("8B");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                // Description: Move the contents of an 64-bit register or memory
                //     operand to an 64-bit destination register.
                // Mnemonic:    MOV imm32
                // Opcode:      8B /r
                ret.setOpCode("8B");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
                ret.addOperand(rm_parser.getRmOperand());
                ret.addOperand(rm_parser.getRegOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0x8C) {
            // TODO
        } else if (b == (byte) 0x8E) {
            // TODO
        } else if (b == (byte) 0xA0) {
            // TODO
        } else if (b == (byte) 0xA1) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("A1");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET32);
                ret.addOperand(moffset_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("A1");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET64);
                ret.addOperand(moffset_parser.getOperand());
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xA2) {
            // TODO
        } else if (b == (byte) 0xA3) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("A3");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET32);
                ret.addOperand(new OperandRegister(RegisterType.EAX));
                ret.addOperand(moffset_parser.getOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("A3");
                IMoffsetParser moffset_parser = ParserFactory.getMoffsetParser();
                moffset_parser.parse(input, IMoffsetParser.Type.MOFFSET64);
                ret.addOperand(new OperandRegister(RegisterType.RAX));
                ret.addOperand(moffset_parser.getOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        } else if (b == (byte) 0xC6) {
        } else if (b == (byte) 0xC7) {
            if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_16) {
                // TODO
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
                ret.setOpCode("C7");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM32);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM32);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            } else if (context.getOperandSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
                ret.setOpCode("C7");
                IModRmParser rm_parser = ParserFactory.getModRmParser();
                rm_parser.parse(context, input, IModRmParser.RegType.NONE, IModRmParser.RmType.REG_MEM64);
                if (rm_parser.getReg() != 0) {
                    throw new RuntimeException("REX.Reg should be 0.");
                }
                IImmParser imm_parser = ParserFactory.getImmParser();
                imm_parser.parse(input, IImmParser.Type.IMM64);
                ret.addOperand(imm_parser.getOperand());
                ret.addOperand(rm_parser.getRmOperand());
                return ret;
            }
            throw new RuntimeException("Unkonwn operand size.");
        }
        // TODO

        throw new UnsupportedOperationException(
                "TODO");
    }
}
//    @Override
//    public IInstruction decode(IInstructionContext context, IInstructionByteInputStream input) {
//        IInstruction instruction = new AMD64Instruction(InstructionType.ADD);      
//        
//        //decodeOperands(instruction, input);
//        
//        return instruction;
//    }
// 48 83 c0 08   add $0x8 %rsp
// 83 : reg + imm8
//    private void decodeOperands(IInstruction instruction, IInstructionByteInputStream input) {
//        if(instructionbytes.size()!=4) {
//            throw new UnsupportedOperationException("Not A correct ADD instructionbytes.");
//        }
//        
//        if(instructionbytes.get(1) == 0x83){
//           instruction.setOpCode("83");
//                  
//           if(DecoderUtil.getRegField(instructionbytes.get(2))==0){// verify is a add insturction
//               //add constant value
//               instruction.addOperand(new OperandConstant(instructionbytes.get(3).longValue()));
//               //add register
//               instruction.addOperand(new OperandRegister(DecoderUtil.getRegister(DecoderUtil.getRmField(instructionbytes.get(2)))));
//           }
//        }
//        
//        //other add opcode to be implement
//        //else if(instructionbytes.get(1) == 0x81){}   
//    }
//  ******old version for String type DecoderUtil 
//            List<String> tempdecodes = DecoderUtil.DecodeHexToOctal(instructionbytes.get(2));
//           if(tempdecodes.get(1).equals("000")){ // verify is a add insturction
//               //add constant value
//               instruction.addOperand(new OperandConstant(instructionbytes.get(3).longValue()));
//               //add register
//               instruction.addOperand(new OperandRegister(DecoderUtil.CastRegister(tempdecodes.get(0))));        
//            }          

