/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.decoder;

import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import edu.syr.bytecast.amd64.impl.parser.IInstructionByteInputStream;
import edu.syr.bytecast.amd64.impl.parser.InstructionByteListInputStream;
import edu.syr.bytecast.amd64.impl.parser.InstructionTestUtils;
import edu.syr.bytecast.amd64.impl.parser.ParserFactory;
import java.io.EOFException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bytecast
 */
public class CMPInstructionDecoderTest {

    public static void assertTrue(IInstruction instruction, Object operand1, Object operand2) {
        if (!instruction.getOperands().get(0).getOperandValue().equals(operand1)) {
            throw new RuntimeException("Assert failed! first operand");
        } else if (!instruction.getOperands().get(1).getOperandValue().equals(operand2)) {
            throw new RuntimeException("Assert failed! second operand equals");
        }

    }
    private static final List<Byte> LEGACY_PREFIX_LIST = Arrays.asList(
            (byte) 0x66, (byte) 0x67, (byte) 0xf0, (byte) 0xf3, (byte) 0xf2,
            (byte) 0x2e, (byte) 0x3Ee, (byte) 0x26, (byte) 0x64, (byte) 0x65,
            (byte) 0x36);

    static IInstructionContext getContext(IInstructionByteInputStream input) throws EOFException {
        IInstructionContext context = new InstructionContextImpl();
        byte b = input.peek();

        // Parse legacy prefixes.
        while (LEGACY_PREFIX_LIST.contains(b)) {
            ParserFactory.getLegacyPrefixParser().parse(context, input);
            b = input.peek();
        }

        // Parse REX prefix
        if (b >= (byte) 0x40 && b <= (byte) 0x4f) {
            ParserFactory.getRexPrefixParser().parse(context, input);
        }

        return context;
    }

    public static void main(String[] args) throws EOFException {
        System.out.println("Testing CALLQInstructionDecoder");

        // Test data.
        //400520:	3b 45 f8             	cmp    -0x8(%rbp),%eax
        //400551:	83 7d ec 03          	cmpl   $0x3,-0x14(%rbp)
        List<Byte> list = Arrays.asList(
                (byte) 0x3b, (byte) 0x45, (byte) 0xf8,
                (byte) 0x83, (byte) 0x7d, (byte) 0xec, (byte) 0x03);
        InstructionByteListInputStream stream = new InstructionByteListInputStream(list, (long) 0x400520);

        CMPInstructionDecoder CMPDcoder = new CMPInstructionDecoder();
        IInstructionContext context = getContext(stream);

        IInstruction callqInstruction = CMPDcoder.decode(context, stream);
        System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) callqInstruction));

        assertTrue(callqInstruction, new OperandTypeMemoryEffectiveAddress(RegisterType.RBP, null, 1, (long) -0x8), RegisterType.EAX);
        stream.updateInstructionAddress();

        callqInstruction = CMPDcoder.decode(context, stream);
        System.out.println(InstructionTestUtils.toObjdumpString((AMD64Instruction) callqInstruction));

        assertTrue(callqInstruction, 3L, new OperandTypeMemoryEffectiveAddress(RegisterType.RBP, null, 1, (long) -0x14));
        stream.updateInstructionAddress();


    }
}
