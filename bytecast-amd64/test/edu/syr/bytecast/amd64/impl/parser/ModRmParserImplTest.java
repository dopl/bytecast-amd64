package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryLogicalAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.io.EOFException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheng
 */
public class ModRmParserImplTest {

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

    @Test
    public void comprehensiveTest() throws EOFException {
        System.out.println("Testing ModRmParserImpl");

        IModRmParser instance = ParserFactory.getModRmParser();

        // Test 48 89 e5             	mov    %rsp,%rbp
        IInstructionByteInputStream input = new InstructionByteListInputStream(Arrays.asList((byte) 0x48, (byte) 0x89, (byte) 0xe5), 0L);
        IInstructionContext context = getContext(input);
        input.skip(1);
        instance.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
        assertTrue(RegisterType.RSP == instance.getRegOperand().getOperandValue());
        assertTrue(RegisterType.RBP == instance.getRmOperand().getOperandValue());
        assertEquals(4, instance.getReg());
        assertEquals(5, instance.getRm());
        assertEquals(4, instance.getExtendedReg());
        assertEquals(5, instance.getExtendedRm());
        assertEquals(3, instance.getMod());

        // Test 4005d1:	4c 8d 25 f4 01 20 00 	lea    0x2001f4(%rip),%r12        # 6007cc <__init_array_end>
        input = new InstructionByteListInputStream(Arrays.asList((byte) 0x4c, (byte) 0x8d, (byte) 0x25, (byte) 0xf4, (byte) 0x01, (byte) 0x20, (byte) 0x00), 0x4005d1L);
        context = getContext(input);
        input.skip(1);
        instance.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
        assertTrue(new OperandTypeMemoryEffectiveAddress(RegisterType.RIP, null, 1, 0x2001f4).equals(instance.getRmOperand().getOperandValue()));
        assertTrue(RegisterType.R12 == instance.getRegOperand().getOperandValue());

        // Test 4005c0:	48 89 6c 24 d8       	mov    %rbp,-0x28(%rsp)
        input = new InstructionByteListInputStream(ParserTestUtils.stringToByteList("48 89 6c 24 d8"), 0x4005c0L);
        context = getContext(input);
        input.skip(1);
        instance.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
        assertTrue(new OperandTypeMemoryEffectiveAddress(RegisterType.RSP, null, 1, -0x28).equals(instance.getRmOperand().getOperandValue()));
        assertTrue(RegisterType.RBP == instance.getRegOperand().getOperandValue());

        // Test 400619:	41 ff 14 dc          	callq  *(%r12,%rbx,8)
        input = new InstructionByteListInputStream(ParserTestUtils.stringToByteList("41 ff 14 dc"), 0x400619L);
        context = getContext(input);
        input.skip(1);
        instance.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
        assertTrue(new OperandTypeMemoryEffectiveAddress(RegisterType.R12, RegisterType.RBX, 8, 0).equals(instance.getRmOperand().getOperandValue()));

        // Test   4004b4:	66 66 66 2e 0f 1f 84 	nopw   %cs:0x0(%rax,%rax,1)
        //        4004bb:	00 00 00 00 00 
        input = new InstructionByteListInputStream(ParserTestUtils.stringToByteList("66 66 66 2e 0f 1f 84 00 00 00 00 00"), 0x4004b4L);
        context = getContext(input);
        input.skip(2);
        instance.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
        assertEquals(new OperandTypeMemoryLogicalAddress(RegisterType.CS, RegisterType.RAX, RegisterType.RAX, 1, 0), instance.getRmOperand().getOperandValue());

        // Test 4004ee:	8b 45 f8             	mov    -0x8(%rbp),%eax
        input = new InstructionByteListInputStream(ParserTestUtils.stringToByteList("8b 45 f8"), 0x4004b4L);
        context = getContext(input);
        input.skip(1);
        instance.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM64);
        assertTrue(new OperandTypeMemoryEffectiveAddress(RegisterType.RBP, null, 1, -8).equals(instance.getRmOperand().getOperandValue()));
        assertTrue(RegisterType.EAX == instance.getRegOperand().getOperandValue());

        // Test 400616:	44 89 ef             	mov    %r13d,%edi
        input = new InstructionByteListInputStream(ParserTestUtils.stringToByteList("44 89 ef"), 0x4004b4L);
        context = getContext(input);
        input.skip(1);
        instance.parse(context, input, IModRmParser.RegType.REG32, IModRmParser.RmType.REG_MEM32);
        assertTrue(RegisterType.EDI == instance.getRmOperand().getOperandValue());
        assertTrue(RegisterType.R13D == instance.getRegOperand().getOperandValue());

        // special tests
        input = new InstructionByteListInputStream(ParserTestUtils.stringToByteList("44 89 e5"), 0x4004b4L);
        context = getContext(input);
        input.skip(1);
        instance.parse(context, input, IModRmParser.RegType.SEG_REG, IModRmParser.RmType.NONE);
        assertEquals(RegisterType.FS, instance.getRegOperand().getOperandValue());

        input = new InstructionByteListInputStream(ParserTestUtils.stringToByteList("44 89 f5"), 0x4004b4L);
        context = getContext(input);
        input.skip(1);
        try {
            instance.parse(context, input, IModRmParser.RegType.SEG_REG, IModRmParser.RmType.NONE);
            fail();
        } catch (RuntimeException ex) {
        }
    }

    @Test
    public void fileInputTest() throws IOException, URISyntaxException {
        AutoTest.testInputStream(getClass().getResourceAsStream("ModRmParserImplTest.txt"));
    }
}
