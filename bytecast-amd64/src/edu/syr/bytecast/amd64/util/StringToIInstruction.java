/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.util;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryLogicalAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandSectionName;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bytecast
 */
public class StringToIInstruction {

    private String m_s;   // MOV %eax,%ebx
    private String m_instructiontype; // MOV
    private String[] m_field; //%eax %ebx 
    private String m_sectionname; // main

    public IInstruction convert(String s) {
        if (s.isEmpty()) {
            return null;
        }
        this.m_s = s;
        parseOneLine(m_s);
        AMD64Instruction ret = new AMD64Instruction(StringToInstructionType(this.m_instructiontype));
        for (IOperand o : parseField()) {
            ret.addOperand(o);
        }
        if (m_sectionname != null) {
            ret.addOperand(new OperandSectionName(m_sectionname));
        }
        clear();
        return ret;
    }

    private void clear() {
        this.m_s = null;
        this.m_field = null;
        this.m_instructiontype = null;
        this.m_sectionname = null;
    }

    private InstructionType StringToInstructionType(String s) {
        String iput = s.toUpperCase();
        Class<InstructionType> clazz = InstructionType.class;
        InstructionType type;
        if (iput.equals("MOVZ")) {
            iput = "MOVZX";
        } else if (iput.equals("MOVS")) {
            iput = "MOVSX";
        }
        try {
            type = Enum.valueOf(clazz, iput);
            return type;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private RegisterType StringToRegisterType(String s) {
        String iput = s.toUpperCase();
        Class<RegisterType> clazz = RegisterType.class;
        RegisterType type;
        if (iput.equals("MOVZ")) {
            iput = "MOVZX";
        } else if (iput.equals("MOVS")) {
            iput = "MOVSX";
        }
        try {
            type = Enum.valueOf(clazz, iput);
            return type;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private List<IOperand> parseField() {
        List<IOperand> ret = new ArrayList<IOperand>();
        if (m_field != null) {
            for (String f : m_field) {
                parseOneField(ret, f);
            }
        }
        return ret;
    }

    private void parseOneField(List<IOperand> ret, String line) {
        String effExpr = "(?:(?:(\\-?)0x(\\w+))?\\((?:%(\\w+))?(?:,(?:%(\\w+)),(\\d))?\\))";
        String expr = "^(?:(\\w+)|(?:\\$0x(\\w+))|(?:%(\\w+))|" + effExpr + "|(?:%(\\w+):" + effExpr + "))?$";
        // 3 5 1 5
        Pattern regex = Pattern.compile(expr);
        Matcher mat = regex.matcher(line);
        if (mat.find()) {
            if (mat.group(1) != null && !mat.group(1).isEmpty()) {
                ret.add(new OperandMemoryEffectiveAddress(null, null, 1, (getLongVal(mat.group(1)))));
                return;
            } else if (mat.group(2) != null && !mat.group(2).isEmpty()) {
                ret.add(new OperandConstant(getLongVal(mat.group(2))));
                return;
            } else if (mat.group(3) != null && !mat.group(3).isEmpty()) {
                ret.add(new OperandRegister(StringToRegisterType(mat.group(3))));
                return;
            } else if (EffectHelper(mat, ret)) {
                return;
            } else if (LogicHelper(mat, ret)) {
                return;
            }
        }
    }
    
    private long getLongVal(String in){
        long l = new BigInteger(in, 16).longValue();
        return l;
    }

    private Boolean LogicHelper(Matcher m, List<IOperand> ret) {
        RegisterType s = null;
        RegisterType a = null;
        RegisterType b = null;
        int index = 1;
        long offset = 0;
        Boolean flag = false;
        if (m.group(9) != null && !m.group().isEmpty()) {
            s = StringToRegisterType(m.group(9));
            flag = true;
        }
        if (m.group(12) != null && !m.group(12).isEmpty()) {
            a = StringToRegisterType(m.group(12));
            flag = true;
        }
        if (m.group(13) != null && !m.group(13).isEmpty()) {
            b = StringToRegisterType(m.group(13));
            flag = true;
        }
        if (m.group(14) != null && !m.group(14).isEmpty()) {
            index = Integer.parseInt(m.group(14));
            flag = true;
        }
        if (m.group(11) != null && !m.group(11).isEmpty()) {
            if (m.group(10).equals("-")) {
                offset = getLongVal(m.group(11)) * (-1);
            } else {
                offset = getLongVal(m.group(11));
            }
            flag = true;
        }
        if (flag) {
            ret.add(new OperandMemoryLogicalAddress(s, a, b, index, offset));
        }
        return flag;
    }

    private Boolean EffectHelper(Matcher m, List<IOperand> ret) {
        RegisterType a = null;
        RegisterType b = null;
        int index = 1;
        long offset = 0;
        Boolean flag = false;
        if (m.group(6) != null && !m.group(6).isEmpty()) {
            a = StringToRegisterType(m.group(6));
            flag = true;
        }
        if (m.group(7) != null && !m.group(7).isEmpty()) {
            b = StringToRegisterType(m.group(7));
            flag = true;
        }
        if (m.group(8) != null && !m.group(8).isEmpty()) {
            index = Integer.parseInt(m.group(8));
            flag = true;
        }
        if (m.group(5) != null && !m.group(5).isEmpty()) {
            if (m.group(4).equals("-")) {
                offset = getLongVal(m.group(5)) * (-1);
            } else {
                offset = getLongVal(m.group(5));
            }
            flag = true;
        }
        if (flag) {
            ret.add(new OperandMemoryEffectiveAddress(a, b, index, offset));
        }
        return flag;
    }

    private void parseOneLine(String line) {
        String expr = "^\\s*(?:\\w+\\b\\s+)??(\\w+)\\b\\s*?(?:\\s+(\\S+)?)?\\s*?(?:\\s+<([^>]+)>?)?";
        Pattern regex = Pattern.compile(expr);
        Matcher mat = regex.matcher(line);
        if (mat.find()) {
            //#1 instructiontype
            String g1 = mat.group(1);
            if (g1 != null) {
                this.m_instructiontype = g1;
            }
            //#2 field
            String g2 = mat.group(2);
            if (g2 != null) {
                String[] fields = g2.split(",(?=[^\\(\\)]*(\\(|$))");
                this.m_field = fields;
            }
            //#3 section name
            String g3 = mat.group(3);
            if (g3 != null) {
                m_sectionname = g3;
            }
        }
    }

    public static void main(String[] args) {
        StringToIInstruction s = new StringToIInstruction();
        // 3 5 1 5
        //s.convert("nop");
        List<IInstruction> test = new ArrayList<IInstruction>();
        test.add(s.convert("nop"));
        test.add(s.convert("callq 4005a3 <main>"));;
        test.add(s.convert("add $0x10,%rax"));
        test.add(s.convert("mov %rbp,%rax"));
        test.add(s.convert("mov    -0x4(%rbp),%edx"));//offset error 0
        test.add(s.convert("lea    (%rdx,%rax,1),%eax"));
        test.add(s.convert("and 0x6007e0(,%rax,8)"));
        test.add(s.convert("mov -0x0(%rax,%rax,1)"));
        test.add(s.convert("mov %cs:0x0(%rax,%rax,1)"));
        test.add(s.convert("sar    %eax"));

    }
    private static final Map<String, InstructionType> InstructionMap = new HashMap<String, InstructionType>();

    static {
        InstructionMap.put("ADD", InstructionType.ADD);
        InstructionMap.put("AND", InstructionType.AND);
        InstructionMap.put("CALLQ", InstructionType.CALLQ);
        InstructionMap.put("CMP", InstructionType.CMP);
        InstructionMap.put("JO", InstructionType.JO);
        InstructionMap.put("JNO", InstructionType.JNO);
        InstructionMap.put("JAE", InstructionType.JAE);
        InstructionMap.put("JE", InstructionType.JE);
        InstructionMap.put("JNE", InstructionType.JNE);
        InstructionMap.put("JBE", InstructionType.JBE);
        InstructionMap.put("JA", InstructionType.JA);
        InstructionMap.put("JS", InstructionType.JS);
        InstructionMap.put("JNS", InstructionType.JNS);
        InstructionMap.put("JP", InstructionType.JP);
        InstructionMap.put("JNP", InstructionType.JNP);
        InstructionMap.put("JL", InstructionType.JL);
        InstructionMap.put("JGE", InstructionType.JGE);
        InstructionMap.put("JLE", InstructionType.JLE);
        InstructionMap.put("JG", InstructionType.JG);
        InstructionMap.put("JMP", InstructionType.JMP);
        InstructionMap.put("LEA", InstructionType.LEA);
        InstructionMap.put("LEAVE", InstructionType.LEAVE);
        InstructionMap.put("MOV", InstructionType.MOV);
        InstructionMap.put("MOVSX", InstructionType.MOVSX);
        InstructionMap.put("MOVZX", InstructionType.MOVZX);
        InstructionMap.put("NOP", InstructionType.NOP);
        InstructionMap.put("POP", InstructionType.POP);
        InstructionMap.put("PUSH", InstructionType.PUSH);
        InstructionMap.put("RET", InstructionType.RET);
        InstructionMap.put("SAR", InstructionType.SAR);
        InstructionMap.put("SHR", InstructionType.SHR);
        InstructionMap.put("SUB", InstructionType.SUB);
        InstructionMap.put("TEST", InstructionType.TEST);
        InstructionMap.put("XOR", InstructionType.XOR);
    }
}
