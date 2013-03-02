package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.InstructionContextImpl;
import java.io.EOFException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sheng
 */
public class ModRmParserImplTest {

    public static void assertTrue(boolean b) {
        if (!b) {
            throw new RuntimeException("Assert failed!");
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
        System.out.println("Testing ModRmParserImpl");

        IModRmParser instance = ParserFactory.getModRmParser();

        // Test 48 89 e5             	mov    %rsp,%rbp
        IInstructionByteInputStream input = new InstructionByteListInputStream(Arrays.asList((byte) 0x48, (byte) 0x89,(byte) 0xe5), 0L);
        IInstructionContext context = getContext(input);
        input.skip(1);
        instance.parse(context, input, IModRmParser.RegType.REG64, IModRmParser.RmType.REG_MEM64);
        assertTrue(RegisterType.RSP==instance.getRegOperand().getOperandValue());
        assertTrue(RegisterType.RBP==instance.getRmOperand().getOperandValue());
        
        
    }
}
