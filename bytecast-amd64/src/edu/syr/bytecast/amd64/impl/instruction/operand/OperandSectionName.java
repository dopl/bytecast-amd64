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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.sectionname != null ? this.sectionname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OperandSectionName other = (OperandSectionName) obj;
        if ((this.sectionname == null) ? (other.sectionname != null) : !this.sectionname.equals(other.sectionname)) {
            return false;
        }
        return true;
    }
    
}
