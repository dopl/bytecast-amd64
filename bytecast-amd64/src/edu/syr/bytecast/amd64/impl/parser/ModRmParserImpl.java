package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryLogicalAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import java.io.EOFException;

/**
 * A class to parse the ModRM bytes. It will also parse SIB bytes if the ModRM
 * bytes need them.
 *
 * @author sheng
 */
public class ModRmParserImpl implements IModRmParser {

    private int m_mod;
    private int m_reg;
    private int m_rm;
    private int m_extended_reg;
    private int m_extended_rm;
    private OperandRegister m_reg_operand;
    private IOperand m_rm_operand;

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input, RegType reg_type, RmType rm_type) throws EOFException {
        byte b = input.read();
        // Parse bits.
        m_mod = b >> 6 & 3;
        m_reg = b >> 3 & 7;
        m_extended_reg = m_reg + (context.isRexR() ? 0x8 : 0);
        m_rm = b & 7;
        m_extended_rm = m_rm + (context.isRexB() ? 0x8 : 0);
        
        // Parse reg operand
        m_reg_operand = parseRegOperand(reg_type);
        
        // Parse r/m operand
        if (m_mod == 3) {
            // The operand is a register operand. See Table A-35, Volume 3, AMD64 Manual (page 463).
            m_rm_operand = parseRmOperandForRegisterType(rm_type);
        } else {
            RegisterType base;
            RegisterType index = null;
            int scaleFactor = 1;
            long offset = 0;
            if (m_mod == 0 && m_rm == 5) {
                // Parse [rip]+disp32 if rm is 101b.
                base = RegisterType.RIP;
                IDispParser parser = ParserFactory.getDispParser();
                parser.parse(input, IDispParser.Type.DISP32);
                offset = parser.getNumber();
            } else {
                if (m_rm == 4) {
                    // Parse SIB if rm is 100b
                    ISibParser sib_parser = ParserFactory.getSibParser();
                    sib_parser.parse(context, input, m_mod);
                    base = sib_parser.getBaseRegister();
                    index = sib_parser.getIndexRegister();
                    scaleFactor = sib_parser.getScaleFactor();
                } else {
                    // Use register type.
                    base = REG64_ARRAY[m_extended_rm];
                }
                // Parse disp8 or disp32 bytes.
                if (m_mod == 1) {
                    IDispParser parser = ParserFactory.getDispParser();
                    parser.parse(input, IDispParser.Type.DISP8);
                    offset = parser.getNumber();
                } else if (m_mod == 2) {
                    IDispParser parser = ParserFactory.getDispParser();
                    parser.parse(input, IDispParser.Type.DISP32);
                    offset = parser.getNumber();
                }
            }
            
            // Add segment register if any.
            if (context.getSegmentRegister() == null) {
                m_rm_operand = new OperandMemoryEffectiveAddress(base, index, scaleFactor, offset);
            } else {
                m_rm_operand = new OperandMemoryLogicalAddress(context.getSegmentRegister(), base, index, scaleFactor, offset);
            }
        }
    }

    @Override
    public int getReg() {
        return m_reg;
    }

    @Override
    public int getMod() {
        return m_mod;
    }

    @Override
    public int getRm() {
        return m_rm;
    }

    @Override
    public int getExtendedReg() {
        return m_extended_reg;
    }

    @Override
    public int getExtendedRm() {
        return m_extended_rm;
    }

    @Override
    public OperandRegister getRegOperand() {
        return m_reg_operand;
    }

    @Override
    public IOperand getRmOperand() {
        return m_rm_operand;
    }
    /**
     * See Table A-34, Volume 3, AMD64 Manual.
     */
    private static final RegisterType[] REG32_ARRAY = new RegisterType[]{
        RegisterType.EAX, RegisterType.ECX, RegisterType.EDX, RegisterType.EBX,
        RegisterType.ESP, RegisterType.EBP, RegisterType.ESI, RegisterType.EDI,
        RegisterType.R8D, RegisterType.R9D, RegisterType.R10D, RegisterType.R11D,
        RegisterType.R12D, RegisterType.R13D, RegisterType.R14D, RegisterType.R15D};
    /**
     * See Table A-34, Volume 3, AMD64 Manual.
     */
    private static final RegisterType[] REG64_ARRAY = new RegisterType[]{
        RegisterType.RAX, RegisterType.RCX, RegisterType.RDX, RegisterType.RBX,
        RegisterType.RSP, RegisterType.RBP, RegisterType.RSI, RegisterType.RDI,
        RegisterType.R8, RegisterType.R9, RegisterType.R10, RegisterType.R11,
        RegisterType.R12, RegisterType.R13, RegisterType.R14, RegisterType.R15};

    private OperandRegister parseRegOperand(RegType type) {
        switch (type) {
            case REG32:
                return new OperandRegister(REG32_ARRAY[m_extended_reg]);
            case REG64:
                return new OperandRegister(REG64_ARRAY[m_extended_reg]);
            case NONE:
                return null;
            default:
                throw new UnsupportedOperationException("Unsupport");
        }
    }

    private OperandRegister parseRmOperandForRegisterType(RmType type) {
        switch (type) {
            case REG_MEM32:
                return new OperandRegister(REG32_ARRAY[m_extended_rm]);
            case REG_MEM64:
                return new OperandRegister(REG64_ARRAY[m_extended_rm]);
            case NONE:
                return null;
            default:
                throw new UnsupportedOperationException("Unsupport");
        }
    }
}
