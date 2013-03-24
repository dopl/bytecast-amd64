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

/**
 *
 * @author sheng
 */
public class InstructionTestUtils {

    private static StringBuilder effectiveAddressToObjdumpString(OperandTypeMemoryEffectiveAddress effectiveAddress, boolean forcedOffset) {
        StringBuilder sb = new StringBuilder();
        if (effectiveAddress.getBase() == null && effectiveAddress.getIndex() == null) {
            // It is an address number.
            sb.append(String.format("%x", effectiveAddress.getOffset()));
        } else {
            if (effectiveAddress.getOffset() != 0 || forcedOffset) {
                if (effectiveAddress.getOffset() >= 0) {
                    sb.append("0x").append(String.format("%x", effectiveAddress.getOffset()));
                } else if (effectiveAddress.getOffset() < 0) {
                    sb.append("-0x").append(String.format("%x", -1 * effectiveAddress.getOffset()));
                }
            }
            sb.append("(");
            if (effectiveAddress.getBase() != null) {
                sb.append("%").append(effectiveAddress.getBase().toString().toLowerCase());
            }
            if (effectiveAddress.getIndex() != null) {
                sb.append(",%").append(effectiveAddress.getIndex().toString().toLowerCase()).append(",").append(effectiveAddress.getScale());
            }
            sb.append(")");
        }
        return sb;
    }

    public static String toObjdumpOperands(IInstruction ins) {
        StringBuilder sb = new StringBuilder();

        // Build operands.
        for (IOperand operand : ins.getOperands()) {
            if (operand.getOperandType() == OperandType.CONSTANT || operand.getOperandType() == OperandType.NUMBER) {
                sb.append("$0x").append(String.format("%x", operand.getOperandValue()));
            } else if (operand.getOperandType() == OperandType.REGISTER) {
                sb.append("%").append(operand.getOperandValue().toString().toLowerCase());
            } else if (operand.getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS) {
                OperandTypeMemoryEffectiveAddress effectiveAddress = (OperandTypeMemoryEffectiveAddress) operand.getOperandValue();
                sb.append(effectiveAddressToObjdumpString(effectiveAddress, false));
            } else if (operand.getOperandType() == OperandType.MEMORY_LOGICAL_ADDRESS) {
                OperandTypeMemoryLogicalAddress logicalAddress = (OperandTypeMemoryLogicalAddress) operand.getOperandValue();
                sb.append("%").append(logicalAddress.getSegment().toString().toLowerCase()).append(":");
                sb.append(effectiveAddressToObjdumpString(logicalAddress.getEffectiveAddress(), true));
            } else if (operand.getOperandType() == OperandType.SECTION_NAME) {
                sb.append("<").append(operand.getOperandValue()).append(">");
            } else {
                sb.append("UNKNOWN");
            }
            sb.append(",");
        }

        return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
    }

    /**
     * Return the objdump format string of the instruction.
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

    public static void main(String[] args) {
        System.out.println("Testing InstructionTestUtils");
        AMD64Instruction ins;

        // Test 400542:	55                   	push   %rbp
        ins = new AMD64Instruction(InstructionType.PUSH);
        //ins.setInstructionMemoryAddress(0x400542);
        ins.addOperand(new OperandRegister(RegisterType.RBP));
        System.out.println(toObjdumpString(ins));

        // Test 40054a:	89 7d ec             	mov    %edi,-0x14(%rbp)
        ins = new AMD64Instruction(InstructionType.MOV);
        //ins.setInstructionMemoryAddress(0x40054a);
        ins.addOperand(new OperandRegister(RegisterType.EDI));
        ins.addOperand(new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x14));
        System.out.println(toObjdumpString(ins));

        // Test :	48 8b 00             	mov    (%rax),%rax
        ins = new AMD64Instruction(InstructionType.MOV);
        //ins.setInstructionMemoryAddress(0x40055f);
        ins.addOperand(new OperandMemoryEffectiveAddress(RegisterType.RAX, null, 1, 0));
        ins.addOperand(new OperandRegister(RegisterType.RAX));
        System.out.println(toObjdumpString(ins));

        // Test 400493:	ff 14 c5 e0 07 60 00 	callq  *0x6007e0(,%rax,8)
        ins = new AMD64Instruction(InstructionType.CALLQ);
        //ins.setInstructionMemoryAddress(0x400493);
        ins.addOperand(new OperandMemoryEffectiveAddress(null, RegisterType.RAX, 8, 0x6007e0));
        System.out.println(toObjdumpString(ins));

        // Test 400480:	73 24                	jae    4004a6
        ins = new AMD64Instruction(InstructionType.JAE);
        //ins.setInstructionMemoryAddress(0x400480);
        ins.addOperand(new OperandMemoryEffectiveAddress(null, null, 1, 0x4004a6));
        System.out.println(toObjdumpString(ins));

        // Test 4004b4:	66 66 66 2e 0f 1f 84 	nopw   %cs:0x0(%rax,%rax,1)
        //      4004bb:	00 00 00 00 00 
        ins = new AMD64Instruction(InstructionType.NOP);
        //ins.setInstructionMemoryAddress(0x4004b4);
        ins.addOperand(new OperandMemoryLogicalAddress(RegisterType.CS, RegisterType.RAX, RegisterType.RAX, 1, 0x0));
        System.out.println(toObjdumpString(ins));

        // Test 4004d8:	bf f0 07 60 00       	mov    $0x6007f0,%edi
        ins = new AMD64Instruction(InstructionType.MOV);
       // ins.setInstructionMemoryAddress(0x4004d8);
        ins.addOperand(new OperandConstant(0x6007f0L));
        ins.addOperand(new OperandRegister(RegisterType.EDI));
        System.out.println(toObjdumpString(ins));

        // Test 4019e1:	48 83 e2 f8          	and    $0xfffffffffffffff8,%rdx
        ins = new AMD64Instruction(InstructionType.AND);
        //ins.setInstructionMemoryAddress(0x4019e1);
        ins.addOperand(new OperandConstant(0xfffffffffffffff8L));
        ins.addOperand(new OperandRegister(RegisterType.RDX));
        System.out.println(toObjdumpString(ins));

    }
}
