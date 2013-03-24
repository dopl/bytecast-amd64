/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary.tables.primaryopcode;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.impl.dictionary.tables.secondaryopcode.SecOpCodesTableEntry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bytecast
 */
public class PrimaryOpCodeTable {
    private static List<PrimaryOpCodeTableEntry> table = new ArrayList<PrimaryOpCodeTableEntry>();
    public static void loadData(){
        table.add(new PrimaryOpCodeTableEntry((byte)0x00, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x01, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x02, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x03, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x04, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x05, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x06, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x07, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x08, InstructionType.OR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x09, InstructionType.OR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x0a, InstructionType.OR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x0b, InstructionType.OR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x0c, InstructionType.OR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x0d, InstructionType.OR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x0e, InstructionType.PUSH));
        //0x0f = Escape to secondary opcode
        
        
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x10, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x11, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x12, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x13, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x14, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x15, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x16, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x17, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x18, InstructionType.SBB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x19, InstructionType.SBB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x1a, InstructionType.SBB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x1b, InstructionType.SBB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x1c, InstructionType.SBB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x1d, InstructionType.SBB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x1e, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x1f, InstructionType.POP));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x20, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x21, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x22, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x23, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x24, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x25, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x26, InstructionType.NULL_PREFIX_x64));
        table.add(new PrimaryOpCodeTableEntry((byte)0x27, InstructionType.DAA));
        table.add(new PrimaryOpCodeTableEntry((byte)0x28, InstructionType.SUB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x29, InstructionType.SUB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x2a, InstructionType.SUB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x2b, InstructionType.SUB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x2c, InstructionType.SUB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x2d, InstructionType.SUB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x2e, InstructionType.NULL_PREFIX_x64));
        table.add(new PrimaryOpCodeTableEntry((byte)0x2f, InstructionType._INVALID_IN_X64));
        
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x30, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x31, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x32, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x33, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x34, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x35, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x36, InstructionType.NULL_PREFIX_x64));
        table.add(new PrimaryOpCodeTableEntry((byte)0x37, InstructionType.AAA));
        table.add(new PrimaryOpCodeTableEntry((byte)0x38, InstructionType.CMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x39, InstructionType.CMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x3a, InstructionType.CMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x3b, InstructionType.CMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x3c, InstructionType.CMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x3d, InstructionType.CMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x3e, InstructionType.NULL_PREFIX_x64));
        table.add(new PrimaryOpCodeTableEntry((byte)0x3f, InstructionType._INVALID_IN_X64));
        //0x40 - 0x4f = REX prefix
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x50, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x51, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x52, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x53, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x54, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x55, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x56, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x57, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x58, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x59, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x5a, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x5b, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x5c, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x5d, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x5e, InstructionType.POP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x5f, InstructionType.POP));
        
        //0x60 - 0x63 = Invalid in 64 bit mode
         table.add(new PrimaryOpCodeTableEntry((byte)0x64, InstructionType.NULL_PREFIX_x64));
         table.add(new PrimaryOpCodeTableEntry((byte)0x65, InstructionType.NULL_PREFIX_x64));
        //0x66 - operand override prefix
        //0x67 - address size override prefix
        table.add(new PrimaryOpCodeTableEntry((byte)0x68, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x69, InstructionType.IMUL));
        table.add(new PrimaryOpCodeTableEntry((byte)0x6a, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x6b, InstructionType.IMUL));
        table.add(new PrimaryOpCodeTableEntry((byte)0x6c, InstructionType.INSB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x6d, InstructionType.INSW));
        table.add(new PrimaryOpCodeTableEntry((byte)0x6e, InstructionType.OUTS));
        table.add(new PrimaryOpCodeTableEntry((byte)0x6f, InstructionType.OUTS));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x70, InstructionType.JO));
        table.add(new PrimaryOpCodeTableEntry((byte)0x71, InstructionType.JNO));
        table.add(new PrimaryOpCodeTableEntry((byte)0x72, InstructionType.JB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x73, InstructionType.JNB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x74, InstructionType.JZ));
        table.add(new PrimaryOpCodeTableEntry((byte)0x75, InstructionType.JNZ));
        table.add(new PrimaryOpCodeTableEntry((byte)0x76, InstructionType.JBE));
        table.add(new PrimaryOpCodeTableEntry((byte)0x77, InstructionType.JNBE));
        table.add(new PrimaryOpCodeTableEntry((byte)0x78, InstructionType.JS));
        table.add(new PrimaryOpCodeTableEntry((byte)0x79, InstructionType.JNS));
        table.add(new PrimaryOpCodeTableEntry((byte)0x7a, InstructionType.JP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x7b, InstructionType.JNP));
        table.add(new PrimaryOpCodeTableEntry((byte)0x7c, InstructionType.JL));
        table.add(new PrimaryOpCodeTableEntry((byte)0x7d, InstructionType.JNL));
        table.add(new PrimaryOpCodeTableEntry((byte)0x7e, InstructionType.JLE));
        table.add(new PrimaryOpCodeTableEntry((byte)0x7f, InstructionType.JNLE));
        //0x80-0x83 = Group 1 opcode extension
         
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x84, InstructionType.TEST));
        table.add(new PrimaryOpCodeTableEntry((byte)0x85, InstructionType.TEST));
        table.add(new PrimaryOpCodeTableEntry((byte)0x86, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x87, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x88, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0x89, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0x8a, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0x8b, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0x8c, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0x8d, InstructionType.LEA));
        table.add(new PrimaryOpCodeTableEntry((byte)0x8e, InstructionType.MOV));
      
        //0x8f = Group 1a opcode extension prefix
        
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x90, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x91, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x92, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x93, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x94, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x95, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x96, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x97, InstructionType.XCHG));
        table.add(new PrimaryOpCodeTableEntry((byte)0x98, InstructionType.CBW));
        table.add(new PrimaryOpCodeTableEntry((byte)0x99, InstructionType.CWD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x9a, InstructionType.CALL));
        table.add(new PrimaryOpCodeTableEntry((byte)0x9b, InstructionType.WAIT));
        table.add(new PrimaryOpCodeTableEntry((byte)0x9c, InstructionType.PUSHF));
        table.add(new PrimaryOpCodeTableEntry((byte)0x9d, InstructionType.POPF));
        table.add(new PrimaryOpCodeTableEntry((byte)0x9e, InstructionType.SAHF));
        table.add(new PrimaryOpCodeTableEntry((byte)0x9f, InstructionType.LAHF));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0xa0, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa1, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa2, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa3, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa4, InstructionType.MOVSB));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa5, InstructionType.MOVSW));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa6, InstructionType.CMPSB));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa7, InstructionType.CMPSBW));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa8, InstructionType.TEST));
        table.add(new PrimaryOpCodeTableEntry((byte)0xa9, InstructionType.TEST));
        table.add(new PrimaryOpCodeTableEntry((byte)0xaa, InstructionType.STOSB));
        table.add(new PrimaryOpCodeTableEntry((byte)0xab, InstructionType.STOSW));
        table.add(new PrimaryOpCodeTableEntry((byte)0xac, InstructionType.LODSB));
        table.add(new PrimaryOpCodeTableEntry((byte)0xad, InstructionType.LODSW));
        table.add(new PrimaryOpCodeTableEntry((byte)0xae, InstructionType.SCASB));
        table.add(new PrimaryOpCodeTableEntry((byte)0xaf, InstructionType.SCASW));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0xb0, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb1, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb2, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb3, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb4, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb5, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb6, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb7, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb8, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xb9, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xba, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xbb, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xbc, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xbd, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xbe, InstructionType.MOV));
        table.add(new PrimaryOpCodeTableEntry((byte)0xbf, InstructionType.MOV));
        //0xc0, 0xc1 = Group 2 primary opcode extension
        
        table.add(new PrimaryOpCodeTableEntry((byte)0xc2, InstructionType.RET));
        table.add(new PrimaryOpCodeTableEntry((byte)0xc3, InstructionType.RET));
        //0xc4, 0xc5 = VEX escape prefix
        //0xc6, 0xc7 = Group 11 opcode extension 
        table.add(new PrimaryOpCodeTableEntry((byte)0xc8, InstructionType.ENTER));
        table.add(new PrimaryOpCodeTableEntry((byte)0xc9, InstructionType.LEAVE));
        table.add(new PrimaryOpCodeTableEntry((byte)0xca, InstructionType.RET));
        table.add(new PrimaryOpCodeTableEntry((byte)0xcb, InstructionType.RET));
        table.add(new PrimaryOpCodeTableEntry((byte)0xcc, InstructionType.INT3));
        table.add(new PrimaryOpCodeTableEntry((byte)0xcd, InstructionType.INT));
        table.add(new PrimaryOpCodeTableEntry((byte)0xce, InstructionType._INVALID_IN_X64));
        table.add(new PrimaryOpCodeTableEntry((byte)0xcf, InstructionType.IRET));
        
        //0xd0, 0xd3 = Group 2 primary opcode extension
        
        table.add(new PrimaryOpCodeTableEntry((byte)0xd4, InstructionType._INVALID_IN_X64));
        table.add(new PrimaryOpCodeTableEntry((byte)0xd5, InstructionType._INVALID_IN_X64));
        table.add(new PrimaryOpCodeTableEntry((byte)0xd6, InstructionType._INVALID_IN_X64));
        table.add(new PrimaryOpCodeTableEntry((byte)0xd7, InstructionType.XLAT));
        //0xd8 - 0xdf = x87 instructions
        
        table.add(new PrimaryOpCodeTableEntry((byte)0xe0, InstructionType.LOOPNE));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe1, InstructionType.LOOPE));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe2, InstructionType.LOOP));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe3, InstructionType.JRCXZ));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe4, InstructionType.IN));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe5, InstructionType.IN));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe6, InstructionType.OUT));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe7, InstructionType.OUT));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe8, InstructionType.CALL));
        table.add(new PrimaryOpCodeTableEntry((byte)0xe9, InstructionType.JMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0xea, InstructionType.JMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0xeb, InstructionType.JMP));
        table.add(new PrimaryOpCodeTableEntry((byte)0xec, InstructionType.IN));
        table.add(new PrimaryOpCodeTableEntry((byte)0xed, InstructionType.IN));
        table.add(new PrimaryOpCodeTableEntry((byte)0xee, InstructionType.OUT));
        table.add(new PrimaryOpCodeTableEntry((byte)0xef, InstructionType.OUT));
        
        
        table.add(new PrimaryOpCodeTableEntry((byte)0xf0, InstructionType.LOCK));
        table.add(new PrimaryOpCodeTableEntry((byte)0xf1, InstructionType.INT1));
        table.add(new PrimaryOpCodeTableEntry((byte)0xf2, InstructionType.REPNE));
        table.add(new PrimaryOpCodeTableEntry((byte)0xf3, InstructionType.REP));
        table.add(new PrimaryOpCodeTableEntry((byte)0xf4, InstructionType.HLT));
        table.add(new PrimaryOpCodeTableEntry((byte)0xf5, InstructionType.CMC));
        //0xf6-0xf7 = Group 3 opcode extension
        table.add(new PrimaryOpCodeTableEntry((byte)0xf8, InstructionType.CLC));
        table.add(new PrimaryOpCodeTableEntry((byte)0xf9, InstructionType.STC));
        table.add(new PrimaryOpCodeTableEntry((byte)0xfa, InstructionType.CLI));
        table.add(new PrimaryOpCodeTableEntry((byte)0xfb, InstructionType.STI));
        table.add(new PrimaryOpCodeTableEntry((byte)0xfc, InstructionType.CLD));
        table.add(new PrimaryOpCodeTableEntry((byte)0xfd, InstructionType.STD));
        //0xfe Group 4 opcode extension
        //0xff Group 5 opcode extension
        
    }
    
    public InstructionType getInstructionFromOpCode(Byte opcode)
    {
        for(PrimaryOpCodeTableEntry row : table)
        {
            if(row.getOpcode()==opcode)
                return row.getInstruction();
        }
        return null;
    }
}
