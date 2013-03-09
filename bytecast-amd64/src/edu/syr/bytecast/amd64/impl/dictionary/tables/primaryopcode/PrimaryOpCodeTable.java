/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary.tables.primaryopcode;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import java.util.List;

/**
 *
 * @author bytecast
 */
public class PrimaryOpCodeTable {
    private static List<PrimaryOpCodeTableEntry> table;
    public static void loadData(){
        table.add(new PrimaryOpCodeTableEntry((byte)0x00, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x01, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x02, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x03, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x04, InstructionType.ADD));
        table.add(new PrimaryOpCodeTableEntry((byte)0x05, InstructionType.ADD));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x06, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x07, InstructionType.POP));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x10, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x11, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x12, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x13, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x14, InstructionType.ADC));
        table.add(new PrimaryOpCodeTableEntry((byte)0x15, InstructionType.ADC));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x16, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x17, InstructionType.POP));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x20, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x21, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x22, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x23, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x24, InstructionType.AND));
        table.add(new PrimaryOpCodeTableEntry((byte)0x25, InstructionType.AND));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x26, InstructionType.NULL_PREFIX_x64));
        table.add(new PrimaryOpCodeTableEntry((byte)0x27, InstructionType.DAA));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x30, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x31, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x32, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x33, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x34, InstructionType.XOR));
        table.add(new PrimaryOpCodeTableEntry((byte)0x35, InstructionType.XOR));
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x36, InstructionType.NULL_PREFIX_x64));
        table.add(new PrimaryOpCodeTableEntry((byte)0x37, InstructionType.AAA));
        
        //0x40 - 0x4f = REX prefix
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x50, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x51, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x52, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x53, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x54, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x55, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x56, InstructionType.PUSH));
        table.add(new PrimaryOpCodeTableEntry((byte)0x57, InstructionType.PUSH));
        
        //0x60 - 0x63 = Invalid in 64 bit mode
         table.add(new PrimaryOpCodeTableEntry((byte)0x64, InstructionType.NULL_PREFIX_x64));
         table.add(new PrimaryOpCodeTableEntry((byte)0x65, InstructionType.NULL_PREFIX_x64));
         
        //0x66 - operand override prefix
        //0x67 - address size override prefix
        
        table.add(new PrimaryOpCodeTableEntry((byte)0x70, InstructionType.JO));
        table.add(new PrimaryOpCodeTableEntry((byte)0x71, InstructionType.JNO));
        table.add(new PrimaryOpCodeTableEntry((byte)0x72, InstructionType.JB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x73, InstructionType.JNB));
        table.add(new PrimaryOpCodeTableEntry((byte)0x74, InstructionType.JZ));
        table.add(new PrimaryOpCodeTableEntry((byte)0x75, InstructionType.JNZ));
        table.add(new PrimaryOpCodeTableEntry((byte)0x76, InstructionType.JBE));
        table.add(new PrimaryOpCodeTableEntry((byte)0x77, InstructionType.JNBE));
         
    }
}
