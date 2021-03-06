package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryLogicalAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryLogicalAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sheng
 */
public class InstructionTestUtils {

    /**
     * Append every string in the strArray to all the StringBuilder in sbs.
     *
     * @param sbs
     * @param strArray
     */
    private static void appendAll(List<StringBuilder> sbs, String... strArray) {
        for (StringBuilder sb : sbs) {
            for (String str : strArray) {
                sb.append(str);
            }
        }
    }

    /**
     * Double the contents of sbs, and append str1 and str2 separately.
     *
     * @param sbs
     * @param str1
     * @param str2
     */
    private static void appendDiff(List<StringBuilder> sbs, String str1, String str2) {
        List<StringBuilder> newList = new ArrayList<StringBuilder>();
        for (StringBuilder sb : sbs) {
            // Add str2 to new sb, and add the sb to the list.
            StringBuilder newStringBuilder = new StringBuilder(sb);
            newStringBuilder.append(str2);
            newList.add(newStringBuilder);
            // Add str1 to original sb
            sb.append(str1);
        }
        sbs.addAll(newList);
    }

    /**
     * Append possible formats of the number to the sbs. If the number is a
     * negative such as "0xffffffffffffffff", it will append
     * "0xffffffffffffffff", "0xffffffff", "0xffff" and "0xff". If the number is
     * "0xffffffff12345678", it will append "0xffffffff12345678" and
     * "0x12345678".
     *
     * @param sbs
     * @param number
     */
    private static void appendNumber(List<StringBuilder> sbs, long number) {
        List<String> numberList = new ArrayList<String>();
        numberList.add(String.format("%x", number));
        // Only for negatives.
        if (number < 0) {
            // If its size can be 4 bytes, add the 4-byte format.
            if (((long) (int) number) == number) {
                numberList.add(String.format("%x", (int) number));
                // If its size can be 2 bytes, add the 2-byte format.
                if ((long) (short) number == number) {
                    numberList.add(String.format("%x", (short) number));
                    // If its size can be 1 byte, add the 1-byte format.
                    if ((long) (byte) number == number) {
                        numberList.add(String.format("%x", (byte) number));
                    }
                }
            }
        }
        int size = sbs.size();
        if (numberList.size() > 1) {
            // Append 4-byte, 2-byte and 1-byte formats.
            for (int i = 1; i < numberList.size(); i++) {
                for (int j = 0; j < size; j++) {
                    StringBuilder sb = new StringBuilder(sbs.get(j));
                    sb.append(numberList.get(i));
                    sbs.add(sb);
                }
            }
        }
        // Append 8-byte format.
        for (int j = 0; j < size; j++) {
            sbs.get(j).append(numberList.get(0));
        }
    }

    /**
     * Append all possible objdump strings of the operand to the sbs.
     *
     * @param sbs
     * @param effectiveAddress
     */
    private static void effectiveAddressToPossibleObjdumpStrings(List<StringBuilder> sbs, OperandTypeMemoryEffectiveAddress effectiveAddress) {
        if (effectiveAddress.getBase() == null && effectiveAddress.getIndex() == null) {
            // It is an address number.
            appendDiff(sbs, "0x", "");
            appendNumber(sbs, effectiveAddress.getOffset());
        } else {
            if (effectiveAddress.getOffset() != 0) {
                if (effectiveAddress.getOffset() >= 0) {
                    // No need to use appendNumber()
                    appendAll(sbs, "0x", String.format("%x", effectiveAddress.getOffset()));
                } else if (effectiveAddress.getOffset() < 0) {
                    // No need to use appendNumber()
                    appendAll(sbs, "-0x", String.format("%x", -1 * effectiveAddress.getOffset()));
                }
            } else {
                appendDiff(sbs, "", "0x0");
            }
            appendAll(sbs, "(");
            if (effectiveAddress.getBase() != null) {
                appendAll(sbs, "%", effectiveAddress.getBase().toString().toLowerCase());
            }
            if (effectiveAddress.getIndex() != null) {
                appendAll(sbs, ",%", effectiveAddress.getIndex().toString().toLowerCase());
                appendAll(sbs, ",", String.valueOf(effectiveAddress.getScale()));
            }
            appendAll(sbs, ")");
        }
    }

    /**
     * Return all possible objdump operands strings of the instruction.
     *
     * @param ins
     * @return
     */
    public static List<String> toPossibleObjdumpOperandsStrings(IInstruction ins) {
        List<StringBuilder> sbs = new ArrayList<StringBuilder>();
        sbs.add(new StringBuilder());

        // Build operands.
        for (IOperand operand : ins.getOperands()) {
            if (operand.getOperandType() == OperandType.CONSTANT || operand.getOperandType() == OperandType.NUMBER) {
                appendAll(sbs, "$0x");
                appendNumber(sbs, (Long) operand.getOperandValue());
                appendAll(sbs, ",");
            } else if (operand.getOperandType() == OperandType.REGISTER) {
                appendAll(sbs, "%", operand.getOperandValue().toString().toLowerCase(), ",");
            } else if (operand.getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS) {
                OperandTypeMemoryEffectiveAddress effectiveAddress = (OperandTypeMemoryEffectiveAddress) operand.getOperandValue();
                effectiveAddressToPossibleObjdumpStrings(sbs, effectiveAddress);
                appendAll(sbs, ",");
            } else if (operand.getOperandType() == OperandType.MEMORY_LOGICAL_ADDRESS) {
                OperandTypeMemoryLogicalAddress logicalAddress = (OperandTypeMemoryLogicalAddress) operand.getOperandValue();
                appendAll(sbs, "%", logicalAddress.getSegment().toString().toLowerCase(), ":");
                effectiveAddressToPossibleObjdumpStrings(sbs, logicalAddress.getEffectiveAddress());
                appendAll(sbs, ",");
            } else if (operand.getOperandType() == OperandType.SECTION_NAME) {
                // Secion name is optional
                appendDiff(sbs, "<" + operand.getOperandValue() + ">,", "");
            } else {
                appendAll(sbs, "UNKNOWN,");
            }
        }

        // Convert StringBuilder to String
        List<String> ret = new ArrayList<String>();
        for (StringBuilder sb : sbs) {
            ret.add(sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1));
        }

        return ret;
    }

    /**
     * Return an objdump operands string of the instruction. Use {@link #toPossibleObjdumpOperandsStrings(edu.syr.bytecast.amd64.api.instruction.IInstruction)
     * } to get all situations.
     *
     * @param ins
     * @return
     */
    public static String toObjdumpOperands(IInstruction ins) {
        return toPossibleObjdumpOperandsStrings(ins).get(0);
    }

    /**
     * Return the objdump format string of the instruction. Use {@link #toPossibleObjdumpStrings(edu.syr.bytecast.amd64.api.instruction.IInstruction)
     * } to get all situations.
     *
     * @param ins
     * @return
     */
    public static String toObjdumpString(IInstruction ins) {
        StringBuilder sb = new StringBuilder();

        // Build address:
        //sb.append(String.format("%x:\t", ins.getInstructionMemoryAddress()));

        // Build instruction name.
        sb.append(ins.getInstructiontype().toString().toLowerCase()).append("\t");

        // Build operands
        sb.append(toObjdumpOperands(ins));
        return sb.toString();
    }

    /**
     * Return all possible objdump strings of the instruction.
     *
     * @param ins
     * @return
     */
    public static List<String> toPossibleObjdumpStrings(IInstruction ins) {
        String instructionName = ins.getInstructiontype().toString().toLowerCase() + ("\t");
        List<String> strs = toPossibleObjdumpOperandsStrings(ins);
        List<String> ret = new ArrayList<String>();
        for (String str : strs) {
            ret.add(instructionName + str);
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println("Testing InstructionTestUtils");
        AMD64Instruction ins;

        // Test 400542:	55                   	push   %rbp
        ins = new AMD64Instruction(InstructionType.PUSH);
        //ins.setInstructionMemoryAddress(0x400542);
        ins.addOperand(new OperandRegister(RegisterType.RBP));
        System.out.println("Except: push\t%rbp");
        for (String str : toPossibleObjdumpStrings(ins)) {
            System.out.println("Got:    " + str);
        }
        System.out.println();

        // Test 40054a:	89 7d ec             	mov    %edi,-0x14(%rbp)
        ins = new AMD64Instruction(InstructionType.MOV);
        //ins.setInstructionMemoryAddress(0x40054a);
        ins.addOperand(new OperandRegister(RegisterType.EDI));
        ins.addOperand(new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x14));
        System.out.println("Except: mov\t%edi,-0x14(%rbp)");
        for (String str : toPossibleObjdumpStrings(ins)) {
            System.out.println("Got:    " + str);
        }
        System.out.println();

        // Test :	48 8b 00             	mov    (%rax),%rax
        ins = new AMD64Instruction(InstructionType.MOV);
        //ins.setInstructionMemoryAddress(0x40055f);
        ins.addOperand(new OperandMemoryEffectiveAddress(RegisterType.RAX, null, 1, 0));
        ins.addOperand(new OperandRegister(RegisterType.RAX));
        System.out.println("Except: mov\t(%rax),%rax");
        for (String str : toPossibleObjdumpStrings(ins)) {
            System.out.println("Got:    " + str);
        }
        System.out.println();

        // Test 400493:	ff 14 c5 e0 07 60 00 	callq  *0x6007e0(,%rax,8)
        ins = new AMD64Instruction(InstructionType.CALLQ);
        //ins.setInstructionMemoryAddress(0x400493);
        ins.addOperand(new OperandMemoryEffectiveAddress(null, RegisterType.RAX, 8, 0x6007e0));
        System.out.println("Except: callq\t*0x6007e0(,%rax,8)");
        for (String str : toPossibleObjdumpStrings(ins)) {
            System.out.println("Got:    " + str);
        }
        System.out.println();

        // Test 400480:	73 24                	jae    4004a6
        ins = new AMD64Instruction(InstructionType.JAE);
        //ins.setInstructionMemoryAddress(0x400480);
        ins.addOperand(new OperandMemoryEffectiveAddress(null, null, 1, 0x4004a6));
        System.out.println("Except: jae\t4004a6");
        for (String str : toPossibleObjdumpStrings(ins)) {
            System.out.println("Got:    " + str);
        }
        System.out.println();

        // Test 4004b4:	66 66 66 2e 0f 1f 84 	nopw   %cs:0x0(%rax,%rax,1)
        //      4004bb:	00 00 00 00 00 
        ins = new AMD64Instruction(InstructionType.NOP);
        //ins.setInstructionMemoryAddress(0x4004b4);
        ins.addOperand(new OperandMemoryLogicalAddress(RegisterType.CS, RegisterType.RAX, RegisterType.RAX, 1, 0x0));
        System.out.println("Except: nopw\t%cs:0x0(%rax,%rax,1)");
        for (String str : toPossibleObjdumpStrings(ins)) {
            System.out.println("Got:    " + str);
        }
        System.out.println();

        // Test 4004d8:	bf f0 07 60 00       	mov    $0x6007f0,%edi
        ins = new AMD64Instruction(InstructionType.MOV);
        // ins.setInstructionMemoryAddress(0x4004d8);
        ins.addOperand(new OperandConstant(0x6007f0L));
        ins.addOperand(new OperandRegister(RegisterType.EDI));
        System.out.println("Except: mov\t$0x6007f0,%edi");
        for (String str : toPossibleObjdumpStrings(ins)) {
            System.out.println("Got:    " + str);
        }
        System.out.println();

        // Test 4019e1:	48 83 e2 f8          	and    $0xfffffffffffffff8,%rdx
        ins = new AMD64Instruction(InstructionType.AND);
        //ins.setInstructionMemoryAddress(0x4019e1);
        ins.addOperand(new OperandConstant(0xfffffffffffffff8L));
        ins.addOperand(new OperandRegister(RegisterType.RDX));
        System.out.println("Except: and\t$0xfffffffffffffff8,%rdx");
        for (String str : toPossibleObjdumpStrings(ins)) {
            System.out.println("Got:    " + str);
        }
        System.out.println();

    }
}
