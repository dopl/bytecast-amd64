package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;

/**
 *
 * @author sheng
 */
public class RegisterInOpcodeParserImpl implements IRegisterInOpcodeParser {

    /**
     * See Table 2-2, Volume 3, AMD64 Manual.
     */
    private static final RegisterType[] RB_NO_REX_ARRAY = {RegisterType.AL,
        RegisterType.CL, RegisterType.DL, RegisterType.BL, RegisterType.AH, // This line is different.
        RegisterType.CH, RegisterType.DH, RegisterType.BH, RegisterType.R8B,
        RegisterType.R9B, RegisterType.R10B, RegisterType.R11B, RegisterType.R12B,
        RegisterType.R13B, RegisterType.R14B, RegisterType.R15B};
    /**
     * See Table A-34, Volume 3, AMD64 Manual, and Figure 2-3, Volume 3, AMD64
     * Manual.
     */
    private static final RegisterType[] RB_WITH_REX_ARRAY = new RegisterType[]{
        RegisterType.AL, RegisterType.CL, RegisterType.DL, RegisterType.BL,
        RegisterType.SPL, RegisterType.BPL, RegisterType.SIL, RegisterType.DIL, // This line is different.
        RegisterType.R8B, RegisterType.R9B, RegisterType.R10B, RegisterType.R11B,
        RegisterType.R12B, RegisterType.R13B, RegisterType.R14B, RegisterType.R15B};
    /**
     * See Table 2-2, Volume 3, AMD64 Manual.
     */
    private static final RegisterType[] RW_ARRAY = {RegisterType.AX,
        RegisterType.CX, RegisterType.DX, RegisterType.BX, RegisterType.SP,
        RegisterType.BP, RegisterType.SI, RegisterType.DI, RegisterType.R8W,
        RegisterType.R9W, RegisterType.R10W, RegisterType.R11W, RegisterType.R12W,
        RegisterType.R13W, RegisterType.R14W, RegisterType.R15W};
    /**
     * See Table 2-2, Volume 3, AMD64 Manual.
     */
    private static final RegisterType[] RD_ARRAY = {RegisterType.EAX,
        RegisterType.ECX, RegisterType.EDX, RegisterType.EBX, RegisterType.ESP,
        RegisterType.EBP, RegisterType.ESI, RegisterType.EDI, RegisterType.R8D,
        RegisterType.R9D, RegisterType.R10D, RegisterType.R11D, RegisterType.R12D,
        RegisterType.R13D, RegisterType.R14D, RegisterType.R15D};
    /**
     * See Table 2-2, Volume 3, AMD64 Manual.
     */
    private static final RegisterType[] RQ_ARRAY = {RegisterType.RAX,
        RegisterType.RCX, RegisterType.RDX, RegisterType.RBX, RegisterType.RSP,
        RegisterType.RBP, RegisterType.RSI, RegisterType.RDI, RegisterType.R8,
        RegisterType.R9, RegisterType.R10, RegisterType.R11, RegisterType.R12,
        RegisterType.R13, RegisterType.R14, RegisterType.R15};
    /**
     * The parsed register operand.
     */
    private OperandRegister m_register;

    @Override
    public void parse(IInstructionContext context, Type type, int value) {
        if (value < 0 || value > 7) {
            throw new IllegalArgumentException("The value should range from 0 to 7 (included).");
        }
        value += context.isRexB() ? 8 : 0;
        switch (type) {
            case RB:
                m_register = new OperandRegister(context.hasRex()
                        ? RB_WITH_REX_ARRAY[value]
                        : RB_NO_REX_ARRAY[value]);
                break;
            case RW:
                m_register = new OperandRegister(RW_ARRAY[value]);
                break;
            case RD:
                m_register = new OperandRegister(RD_ARRAY[value]);
                break;
            case RQ:
                m_register = new OperandRegister(RQ_ARRAY[value]);
                break;
            default:
                throw new RuntimeException("Unknown type");
        }
    }

    @Override
    public OperandRegister getRegisterOperand() {
        return m_register;
    }
}
