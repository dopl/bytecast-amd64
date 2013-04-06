/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.dictionary.tables.ocextensions;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.impl.dictionary.tables.primaryopcode.OpCodeExtensionGroup;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bytecast
 */
public class OpCodeExtensionTable {
    
    private static Hashtable<OpCodeExtensionGroup,List<OpCodeExtensionTableEntry> > m_ocExtTable
            = new Hashtable<OpCodeExtensionGroup, List<OpCodeExtensionTableEntry>>();
    
   
    public static void loadData()
    {
      List<OpCodeExtensionTableEntry> grp1table = new ArrayList<OpCodeExtensionTableEntry>();
      grp1table.add(new OpCodeExtensionTableEntry((int)0, InstructionType.ADD));
      grp1table.add(new OpCodeExtensionTableEntry((int)1, InstructionType.OR));
      grp1table.add(new OpCodeExtensionTableEntry((int)2, InstructionType.ADC));
      grp1table.add(new OpCodeExtensionTableEntry((int)3, InstructionType.SBB));
      grp1table.add(new OpCodeExtensionTableEntry((int)4, InstructionType.AND));
      grp1table.add(new OpCodeExtensionTableEntry((int)5, InstructionType.SUB));
      grp1table.add(new OpCodeExtensionTableEntry((int)6, InstructionType.XOR));
      grp1table.add(new OpCodeExtensionTableEntry((int)7, InstructionType.CMP));
      m_ocExtTable.put(OpCodeExtensionGroup.GROUP_1, grp1table);
      
      List<OpCodeExtensionTableEntry> grp1atable = new ArrayList<OpCodeExtensionTableEntry>();
      grp1table.add(new OpCodeExtensionTableEntry((int)0, InstructionType.POP));
      m_ocExtTable.put(OpCodeExtensionGroup.GROUP_1a, grp1atable);
      
     /*
      * Groups 2 to 11 not implemented
      */
      
    }
    
    public InstructionType getInstruction(OpCodeExtensionGroup opext, int modrmReg){
        List<OpCodeExtensionTableEntry> itypes = m_ocExtTable.get(opext);
        if(itypes==null)
            return null;
        for(OpCodeExtensionTableEntry i: itypes){
            if(i.getM_modRegFieldVal()==modrmReg)
                return i.getM_instructionType();
        }
        return null;
    }
}
