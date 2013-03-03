package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import java.io.EOFException;

/**
 *
 * @author sheng
 */
public class SibParserImpl implements ISibParser {

    private static final RegisterType[] REGISTER_ARRAY = {RegisterType.RAX,
        RegisterType.RCX, RegisterType.RDX, RegisterType.RBX, RegisterType.RSP,
        RegisterType.RBP, RegisterType.RSI, RegisterType.RDI, RegisterType.R8,
        RegisterType.R9, RegisterType.R10, RegisterType.R11, RegisterType.R12,
        RegisterType.R13, RegisterType.R14, RegisterType.R15};
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
        if (index != 4) {
            indexRegister = REGISTER_ARRAY[extended_index];
        }
        
        // Parse base register
        if (base == 5 && mod == 0) {
            // No base register.
        } else {
            baseRegister = REGISTER_ARRAY[extended_base];
        }
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
}
