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
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
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
    private String m_field; //%eax,%ebx 

    public IInstruction convert(String s) {
        this.m_s = s;
        parse();
        AMD64Instruction ret = new AMD64Instruction(InstructionMap.get(m_instructiontype));
        for (IOperand o : parseField()) {
            ret.addOperand(o);
        }
        return ret;
    }

    private List<IOperand> parseField() {
        List<IOperand> ret = new ArrayList<IOperand>();
        //TO DO parser
        ret.add(new OperandRegister(RegisterType.EAX));
        ret.add(new OperandRegister(RegisterType.EBX));
        return ret;
    }

    private void parse() {
        //TO DO
        this.m_instructiontype = m_s;
        this.m_field = m_s;
    }

    public static void parseLine(String line) {
        String effExpr = "(?:(?:(\\-?)0x(\\w+))?\\((?:%(\\w+))?(?:,(?:%(\\w+)),(\\d))?\\))";
        String expr = "^(?:(\\w+)|(?:\\$0x(\\w+))|(?:%(\\w+))|" + effExpr + "|(?:%(\\w+):" + effExpr + "))?$";
        Pattern regex = Pattern.compile(expr);
        Matcher mat = regex.matcher(line);
        if (mat.find()) {
            for (int i = 1; i <= 14; i++) {
                String str = mat.group(i);
                if (str != null && !str.isEmpty()) {
                    System.out.print(str + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 3 5 1 5
        parseLine("");
        parseLine("4005a3");
        parseLine("$0x10");
        parseLine("%rbp");
        parseLine("(%rax)");
        parseLine("-0x20(%rbp)");
        parseLine("0x6007e0(,%rax,8)");
        parseLine("-0x0(%rax,%rax,1)");
        parseLine("%cs:0x0(%rax,%rax,1)");
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
