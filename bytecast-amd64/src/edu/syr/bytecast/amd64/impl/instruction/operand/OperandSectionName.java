/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.impl.instruction.operand;

import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;

/**
 *
 * @author bytecast
 */
public class OperandSectionName implements IOperand{

    private String sectionname;

    public OperandSectionName(String sectionname) {
        this.sectionname = sectionname;
    }
       
    @Override
    public OperandType getOperandType() {
        return OperandType.SECTION_NAME;
    }

    @Override
    public Object getOperandValue() {
        return this.sectionname;
    }
    
}
