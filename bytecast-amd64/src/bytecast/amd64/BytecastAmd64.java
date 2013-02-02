/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bytecast.amd64;

import com.bytecase.amd64.util.DecoderUtil;
import com.bytecast.amd64.api.constants.InstructionType;
import com.bytecast.amd64.api.instruction.IInstruction;
import com.bytecast.amd64.impl.decoder.DecoderFactory;
import com.bytecast.amd64.impl.instruction.AMD64Instruction;

/**
 *
 * @author harsh
 */
public class BytecastAmd64 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Byte b = (byte)0xc4;
        for(String s :DecoderUtil.DecodeHexToOctal(b))
        {
            System.out.println(DecoderUtil.CastRegister(s));
        }
        
        DecoderFactory.getInstructionDecoder(InstructionType.ADD);
        
    }
    
    public BytecastAmd64()
    {
        
    }
    
    
   
    
}