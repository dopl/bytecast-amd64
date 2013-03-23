package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;

/**
 *
 * @author sheng
 */
public class InstructionTestUtils {

    /**
     * Return the objdump format string of the instruction.
     *
     * @return
     */
    public static String toObjdumpString(AMD64Instruction ins) {
        StringBuilder sb = new StringBuilder();

        // Build address:
        sb.append(String.format("%x", ins.getInstructionMemoryAddress())).append(":\t");

        // Build instruction name.
        sb.append(ins.getInstructiontype().toString().toLowerCase()).append("\t");

        // Build operands.
        for (IOperand operand : ins.getOperands()) {
            if (operand.getOperandType() == OperandType.CONSTANT || operand.getOperandType() == OperandType.NUMBER) {
                sb.append("$").append(String.format("%x", operand.getOperandValue()));
            } else if (operand.getOperandType() == OperandType.REGISTER) {
                sb.append("%").append(operand.getOperandValue().toString().toLowerCase());
            } else if (operand.getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS) {
                OperandTypeMemoryEffectiveAddress effectiveAddress = (OperandTypeMemoryEffectiveAddress) operand.getOperandValue();
                if (effectiveAddress.getOffset() != 0) {
                    if (effectiveAddress.getOffset() > 0) {
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
            } else if(operand.getOperandType()== OperandType.SECTION_NAME) {
                sb.append("<").append(operand.getOperandValue()).append(">");
            } else {
                sb.append("UNKNOWN");
            }
            sb.append(",");
        }

        return sb.substring(0, sb.length() - 1);
    }

    public static void main(String[] args) {
        System.out.println("Testing InstructionTestUtils");
        AMD64Instruction ins;

        // Test 400542:	55                   	push   %rbp
        ins = new AMD64Instruction(InstructionType.PUSH);
        ins.setInstructionMemoryAddress(0x400542);
        ins.addOperand(new OperandRegister(RegisterType.RBP));
        System.out.println(toObjdumpString(ins));

        // Test 40054a:	89 7d ec             	mov    %edi,-0x14(%rbp)
        ins = new AMD64Instruction(InstructionType.MOV);
        ins.setInstructionMemoryAddress(0x40054a);
        ins.addOperand(new OperandRegister(RegisterType.EDI));
        ins.addOperand(new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x14));
        System.out.println(toObjdumpString(ins));

        // Test :	48 8b 00             	mov    (%rax),%rax
        ins = new AMD64Instruction(InstructionType.MOV);
        ins.setInstructionMemoryAddress(0x40055f);
        ins.addOperand(new OperandMemoryEffectiveAddress(RegisterType.RAX, null, 1, 0));
        ins.addOperand(new OperandRegister(RegisterType.RAX));
        System.out.println(toObjdumpString(ins));

        // Test 400493:	ff 14 c5 e0 07 60 00 	callq  *0x6007e0(,%rax,8)
        ins = new AMD64Instruction(InstructionType.CALLQ);
        ins.setInstructionMemoryAddress(0x400493);
        ins.addOperand(new OperandMemoryEffectiveAddress(null, RegisterType.RAX, 8, 0x6007e0));
        System.out.println(toObjdumpString(ins));

    }
}
