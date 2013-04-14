/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.test;

import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandSectionName;
import edu.syr.bytecast.amd64.impl.output.AMD64ExecutableFile;
import edu.syr.bytecast.amd64.impl.output.AMD64Section;
import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import edu.syr.bytecast.test.mockups.MockBytecastFsys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author bytecast
 */
public class DepcrecatedMock implements IBytecastAMD64{

    @Override
    public IExecutableFile buildInstructionObjects() {
       
         List<ISection> sections = new ArrayList<ISection>();
         List<MemoryInstructionPair> memtoins = new ArrayList<MemoryInstructionPair>();
         /*
          * ###################### IMPORTANT #################################################################################
          * The order of operands you see in the code below seems - OP1,OP2. This is coded as seen in the objectdump output.
          * BUT, actually the operands are added right to left in the operands list. This is done here in the method
          * instruction (...)
          * #################################################################################################################
          */
        //<main> STARTS
            memtoins.add(new MemoryInstructionPair((long)0x400542, instruction("55",InstructionType.PUSH   ,new OperandRegister(RegisterType.RBP)                                ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x400543, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.RSP)                                ,new OperandRegister(RegisterType.RBP))));
            memtoins.add(new MemoryInstructionPair((long)0x400546, instruction("83",InstructionType.SUB    ,new OperandConstant((long)0x20)                                      ,new OperandRegister(RegisterType.RSP))));
            memtoins.add(new MemoryInstructionPair((long)0x40054a, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EDI)                                ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x14))));
            memtoins.add(new MemoryInstructionPair((long)0x40054d, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.RSI)                                ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x20))));
            memtoins.add(new MemoryInstructionPair((long)0x400551, instruction("83",InstructionType.CMPL   ,new OperandConstant((long)0x3)                                       ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x14))));
            memtoins.add(new MemoryInstructionPair((long)0x400555, instruction("75",InstructionType.JNE    ,new OperandMemoryEffectiveAddress(null, null, 1, 0x4005a3)           ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x400557, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x20)  ,new OperandRegister(RegisterType.RAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x40055b, instruction("83",InstructionType.ADD    ,new OperandConstant((long)0x10)                                      ,new OperandRegister(RegisterType.RAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x40055f, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RAX, null, 1, -0x0)   ,new OperandRegister(RegisterType.RAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x400562, instruction("0F B6",InstructionType.MOVZBL ,new OperandMemoryEffectiveAddress(RegisterType.RAX, null, 1, -0x0)   ,new OperandRegister(RegisterType.EAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x400565, instruction("0F BE",InstructionType.MOVSBL ,new OperandRegister(RegisterType.AL)                                 ,new OperandRegister(RegisterType.EDX) )));
            memtoins.add(new MemoryInstructionPair((long)0x400568, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x20)  ,new OperandRegister(RegisterType.RAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x40056c, instruction("83",InstructionType.ADD    ,new OperandConstant((long)0x8)                                       ,new OperandRegister(RegisterType.RAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x400570, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RAX, null, 1, -0x0)   ,new OperandRegister(RegisterType.RAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x400573, instruction("0F B6",InstructionType.MOVZBL ,new OperandMemoryEffectiveAddress(RegisterType.RAX, null, 1, -0x0)   ,new OperandRegister(RegisterType.EAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x400576, instruction("0F BE",InstructionType.MOVSBL ,new OperandRegister(RegisterType.AL)                                 ,new OperandRegister(RegisterType.EAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x400579, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EDX)                                ,new OperandRegister(RegisterType.ESI))));
            memtoins.add(new MemoryInstructionPair((long)0x40057b, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EAX)                                ,new OperandRegister(RegisterType.EDI))));
            memtoins.add(new MemoryInstructionPair((long)0x40057d, instruction("E8",InstructionType.CALLQ  ,new OperandMemoryEffectiveAddress(null, null, 1, 0x40050f)            ,new OperandSectionName("dostuff"))));
            memtoins.add(new MemoryInstructionPair((long)0x400582, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EAX)                                ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4)   )));
            memtoins.add(new MemoryInstructionPair((long)0x400585, instruction("b8",InstructionType.MOV    ,new OperandConstant((long) 0x40069c)                                 ,new OperandRegister(RegisterType.EAX) )));
            memtoins.add(new MemoryInstructionPair((long)0x40058a, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4)   ,new OperandRegister(RegisterType.EDX) )));
            memtoins.add(new MemoryInstructionPair((long)0x40058d, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EDX)                                ,new OperandRegister(RegisterType.ESI))));
            memtoins.add(new MemoryInstructionPair((long)0x40058f, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.RAX)                                ,new OperandRegister(RegisterType.RDI))));
            memtoins.add(new MemoryInstructionPair((long)0x400592, instruction("b8",InstructionType.MOV    ,new OperandConstant((long) 0x0)                                      ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x400597, instruction("E8",InstructionType.CALLQ  ,new OperandMemoryEffectiveAddress(null, null, 1, 0x4003e0)            ,new OperandSectionName("_IO_printf"))));
            memtoins.add(new MemoryInstructionPair((long)0x40059c, instruction("b8",InstructionType.MOV    ,new OperandConstant((long) 0x0)                                      ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x4005a1, instruction("EB",InstructionType.JMP    ,new OperandMemoryEffectiveAddress(null, null, 1, 0x4005a8)           ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4005a3, instruction("b8",InstructionType.MOV    ,new OperandConstant((long) 0x1)                                      ,new OperandRegister(RegisterType.EAX))));            
            memtoins.add(new MemoryInstructionPair((long)0x4005a8, instruction("C9",InstructionType.LEAVEQ ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4005a9, instruction("c3",InstructionType.RETQ   ,null                                                                 ,null)));
           
            //NOT used since fsys strips them of from functions
            /*memtoins.add(new MemoryInstructionPair((long)0x4005aa, instruction("",InstructionType.NOP    ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4005ab, instruction("",InstructionType.NOP    ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4005ac, instruction("",InstructionType.NOP    ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4005ad, instruction("",InstructionType.NOP    ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4005ae, instruction("",InstructionType.NOP    ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4005af, instruction("",InstructionType.NOP    ,null                                                                 ,null)));
            */
        //<main> ENDS
        //<dostuff> STARTS
            memtoins.add(new MemoryInstructionPair((long)0x40050f, instruction("55",InstructionType.PUSH   ,new OperandRegister(RegisterType.RBP)                                ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x400510, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.RSP)                                ,new OperandRegister(RegisterType.RBP))));
            memtoins.add(new MemoryInstructionPair((long)0x400513, instruction("83",InstructionType.SUB    ,new OperandConstant((long)0x8)                                       ,new OperandRegister(RegisterType.RSP))));
            memtoins.add(new MemoryInstructionPair((long)0x400517, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EDI)                                ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4))));
            memtoins.add(new MemoryInstructionPair((long)0x40051a, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.ESI)                                ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x8))));
            memtoins.add(new MemoryInstructionPair((long)0x40051d, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4)   ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x400520, instruction("3B",InstructionType.CMP    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x8)   ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x400523, instruction("7d",InstructionType.JGE    ,new OperandMemoryEffectiveAddress(null, null, 1, 0x400536)           ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x400525, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x8)   ,new OperandRegister(RegisterType.EDX))));
            memtoins.add(new MemoryInstructionPair((long)0x400528, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4)   ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x40052b, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EDX)                                ,new OperandRegister(RegisterType.ESI))));
            memtoins.add(new MemoryInstructionPair((long)0x40052d, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EAX)                                ,new OperandRegister(RegisterType.EDI))));
            memtoins.add(new MemoryInstructionPair((long)0x40052f, instruction("E8",InstructionType.CALLQ  ,new OperandMemoryEffectiveAddress(null, null, 1, 0x4004e4)            ,new OperandSectionName("sum"))));
            memtoins.add(new MemoryInstructionPair((long)0x400534, instruction("EB",InstructionType.JMP    ,new OperandMemoryEffectiveAddress(null, null, 1, 0x400540)           ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x400536, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4)   ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x400539, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EAX)                                ,new OperandRegister(RegisterType.EDI))));
            memtoins.add(new MemoryInstructionPair((long)0x40053b, instruction("E8",InstructionType.CALLQ  ,new OperandMemoryEffectiveAddress(null, null, 1, 0x4004f9)           ,new OperandSectionName("halve"))));
            memtoins.add(new MemoryInstructionPair((long)0x400540, instruction("C9",InstructionType.LEAVEQ ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x400541, instruction("c3",InstructionType.RETQ   ,null                                                                 ,null)));
        //<dostuff> ENDS
        //<sum> STARTS
            memtoins.add(new MemoryInstructionPair((long)0x4004e4, instruction("55",InstructionType.PUSH   ,new OperandRegister(RegisterType.RBP)                                ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4004e5, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.RSP)                                ,new OperandRegister(RegisterType.RBP))));
            memtoins.add(new MemoryInstructionPair((long)0x4004e8, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EDI)                                ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4))));
            memtoins.add(new MemoryInstructionPair((long)0x4004eb, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.ESI)                                ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x8))));
            memtoins.add(new MemoryInstructionPair((long)0x4004ee, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x8)   ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x4004f1, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4)   ,new OperandRegister(RegisterType.EDX))));
   /*check*/memtoins.add(new MemoryInstructionPair((long)0x4004f4, instruction("8D",InstructionType.LEA    ,new OperandMemoryEffectiveAddress(RegisterType.RDX, RegisterType.RAX, 1, 0x0)   ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x4004f7, instruction("C9",InstructionType.LEAVEQ ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4004f8, instruction("c3",InstructionType.RETQ   ,null                                                                 ,null)));
        //<sum> ENDS
        //<halve> STARTS
            memtoins.add(new MemoryInstructionPair((long)0x4004f9, instruction("55",InstructionType.PUSH   ,new OperandRegister(RegisterType.RBP)                                ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x4004fa, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.RSP)                                ,new OperandRegister(RegisterType.RBP))));
            memtoins.add(new MemoryInstructionPair((long)0x4004fd, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EDI)                                ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4))));
            memtoins.add(new MemoryInstructionPair((long)0x400500, instruction("8B",InstructionType.MOV    ,new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4)   ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x400503, instruction("89",InstructionType.MOV    ,new OperandRegister(RegisterType.EAX)                                ,new OperandRegister(RegisterType.EDX))));
            memtoins.add(new MemoryInstructionPair((long)0x400505, instruction("C1",InstructionType.SHR    ,new OperandConstant((long)0x1f)                                       ,new OperandRegister(RegisterType.EDX))));
   /*check*/memtoins.add(new MemoryInstructionPair((long)0x400508, instruction("8D",InstructionType.LEA    ,new OperandMemoryEffectiveAddress(RegisterType.RDX, RegisterType.RAX, 1, 0x0)   ,new OperandRegister(RegisterType.EAX))));
            memtoins.add(new MemoryInstructionPair((long)0x40050b, instruction("D1",InstructionType.SAR    ,new OperandRegister(RegisterType.EDX)                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x40050d, instruction("C9",InstructionType.LEAVEQ ,null                                                                 ,null)));
            memtoins.add(new MemoryInstructionPair((long)0x40050e, instruction("c3",InstructionType.RETQ   ,null                                                                 ,null)));
        //<halve> ENDS    
         
        ISection section  = new AMD64Section(memtoins,(long)0x400542 , true);
        sections.add(section);
        ExeObj fsysObj=null;
        try {
            fsysObj = new MockBytecastFsys().parse();
        } catch (Exception ex) {
            Logger.getLogger(DepcrecatedMock.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        IExecutableFile exeFile = new AMD64ExecutableFile(fsysObj.getSegments(),sections, "TEST_EXE_FILE", "ELF", null);
        return exeFile;
    }
    
    private AMD64Instruction instruction(String opcode,InstructionType ins, IOperand op1, IOperand op2){
        List<IOperand> operands = new ArrayList<IOperand>();
        
        if(op1!=null) {
            operands.add(op1);
        }
        
        if(op2!=null) {
            operands.add(op2);
        }
        AMD64Instruction amD64Instruction = new AMD64Instruction(ins, operands);
        amD64Instruction.setOpCode(opcode);
        return amD64Instruction;

    }
    
}



