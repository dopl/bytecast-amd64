package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;

/**
 *
 * @author sheng
 */
public class RegisterInOpcodeParserImpl implements IRegisterInOpcodeParser {

    /**
     * See Table 2-2, Volume 2, AMD64 Manual.
     */
    private static final RegisterType[] RB_ARRAY = {RegisterType.AL,
        RegisterType.CL, RegisterType.DL, RegisterType.BL, RegisterType.AH,
        RegisterType.CH, RegisterType.DH, RegisterType.BH};
    /**
     * See Table 2-2, Volume 2, AMD64 Manual.
     */
    private static final RegisterType[] RW_ARRAY = {RegisterType.AX,
        RegisterType.CX, RegisterType.DX, RegisterType.BX, RegisterType.SP,
        RegisterType.BP, RegisterType.SI, RegisterType.DI};
    /**
     * See Table 2-2, Volume 2, AMD64 Manual.
     */
    private static final RegisterType[] RD_ARRAY = {RegisterType.EAX,
        RegisterType.ECX, RegisterType.EDX, RegisterType.EBX, RegisterType.ESP,
        RegisterType.EBP, RegisterType.ESI, RegisterType.EDI};
    /**
     * See Table 2-2, Volume 2, AMD64 Manual.
     */
    private static final RegisterType[] RQ_ARRAY = {RegisterType.RAX,
        RegisterType.RCX, RegisterType.RDX, RegisterType.RBX, RegisterType.RSP,
        RegisterType.RBP, RegisterType.RSI, RegisterType.RDI};
    /**
     * The parsed register operand.
     */
    private OperandRegister m_register;

    @Override
    public void parse(Type type, int value) {
        switch (type) {
            case RB:
                m_register = new OperandRegister(RB_ARRAY[value]);
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
