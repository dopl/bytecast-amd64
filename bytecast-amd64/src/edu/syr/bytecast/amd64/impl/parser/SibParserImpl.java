package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class SibParserImpl implements ISibParser {

    private static final RegisterType[] REGISTER64_ARRAY = {RegisterType.RAX,
        RegisterType.RCX, RegisterType.RDX, RegisterType.RBX, RegisterType.RSP,
        RegisterType.RBP, RegisterType.RSI, RegisterType.RDI, RegisterType.R8,
        RegisterType.R9, RegisterType.R10, RegisterType.R11, RegisterType.R12,
        RegisterType.R13, RegisterType.R14, RegisterType.R15};
    private static final RegisterType[] REGISTER32_ARRAY = {RegisterType.EAX,
        RegisterType.ECX, RegisterType.EDX, RegisterType.EBX, RegisterType.ESP,
        RegisterType.EBP, RegisterType.ESI, RegisterType.EDI, RegisterType.R8D,
        RegisterType.R9D, RegisterType.R10D, RegisterType.R11D, RegisterType.R12D,
        RegisterType.R13D, RegisterType.R14D, RegisterType.R15D};
    private int scare;
    private int index;
    private int base;
    private int extended_index;
    private int extended_base;
    private RegisterType indexRegister;
    private RegisterType baseRegister;

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input, int mod) throws EOFException {
        byte b = input.read();
        // Parse bits.
        scare = b >> 6 & 3;
        index = b >> 3 & 7;
        extended_index = index + (context.isRexX() ? 0x8 : 0);
        base = b & 7;
        extended_base = base + (context.isRexB() ? 0x8 : 0);

        // Parse index register
        if (index != 4 || context.isRexX()) {
            // Address size override prefix may change register size.
            indexRegister = context.getAddressSize() == IInstructionContext.OperandOrAddressSize.SIZE_32
                    ? REGISTER32_ARRAY[extended_index]
                    : REGISTER64_ARRAY[extended_index];
        }

        // Parse base register
        if (base == 5 && mod == 0) {
            // No base register.
        } else {
            // Address size override prefix may change register size.
            baseRegister = context.getAddressSize() == IInstructionContext.OperandOrAddressSize.SIZE_32
                    ? REGISTER32_ARRAY[extended_base]
                    : REGISTER64_ARRAY[extended_base];
        }
        // Parsing base disp8 or disp 32 should be done outside!
    }

    @Override
    public RegisterType getBaseRegister() {
        return baseRegister;
    }

    @Override
    public RegisterType getIndexRegister() {
        return indexRegister;
    }

    @Override
    public int getScaleFactor() {
        if (scare == 0) {
            return 1;
        } else if (scare == 1) {
            return 2;
        } else if (scare == 2) {
            return 4;
        } else if (scare == 3) {
            return 8;
        }
        throw new RuntimeException("Unknown scare!");
    }

    @Override
    public int getScale() {
        return scare;
    }

    @Override
    public int getBase() {
        return base;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
