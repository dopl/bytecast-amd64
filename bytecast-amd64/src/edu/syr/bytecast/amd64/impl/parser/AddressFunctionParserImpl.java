/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.parser;

import edu.syr.bytecast.amd64.impl.dictionary.AMD64Dictionary;
import edu.syr.bytecast.amd64.impl.instruction.IInstructionContext;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandSectionName;
import java.io.EOFException;
import java.util.Dictionary;

/**
 *
 * @author bytecast
 */
public class AddressFunctionParserImpl implements IAddressFunctionParser {

    private OperandSectionName m_sn;
    private OperandMemoryEffectiveAddress m_mfa;
    private long m_address;

    @Override
    public void parse(IInstructionContext context, IInstructionByteInputStream input) throws EOFException {
        //AMD64Dictionary amd64dic = AMD64Dictionary.getInstance();
        int offset;
        if (context.getAddressSize() == IInstructionContext.OperandOrAddressSize.SIZE_32) {
            offset = 3;

        } else if (context.getAddressSize() == IInstructionContext.OperandOrAddressSize.SIZE_64) {
            offset = 5;
        } else {
            throw new RuntimeException("Unsupported AddressSize in AddressFunctionParser");
        }
        AddressCalc(input,offset);
        m_mfa = new OperandMemoryEffectiveAddress(null,null,1,m_address);
        m_sn = new OperandSectionName(AMD64Dictionary.getInstance().getFunctionNameFromAddress(m_address));
        //m_sn = new OperandSectionName("SectionName");
    }

    private void AddressCalc(IInstructionByteInputStream input, int offset) throws EOFException {
        long address = input.getInstructionAddress();
        long ret = 0;
        byte[] tmp = new byte[offset-1];
        for (int i = 0; i < offset - 1; i++) {
            tmp[i] = input.read();            
        }  
        for (int i = offset -2; i >= 0 ; --i) {
            ret = ret << 8;
            ret += (0x00000000000000FFL & tmp[i]);
        }
        m_address = address + offset + (int)ret;
    }

    @Override
    public OperandMemoryEffectiveAddress getAddressOperand() {
        return m_mfa;
    }

    @Override
    public OperandSectionName getSectionNameOperand() {
        return m_sn;
    }
}
