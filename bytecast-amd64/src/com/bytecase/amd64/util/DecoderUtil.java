/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecase.amd64.util;
import com.bytecast.amd64.api.constants.RegisterType;
import java.util.*;

/**
 *
 * @author chenqian
 */
public class DecoderUtil {
    
    // Input is "c4" which Hex is "11000100" 
    // Output will be ret[0] = "100" ; ret[1] = "000"
    public static List<String> DecodeHexToBinary(String hexstring){
        List<String> ret = new ArrayList<String>();
        int temp = Integer.parseInt(hexstring,16);
        String t = Integer.toBinaryString(temp);
        ret.add(t.substring(5));
        ret.add(t.substring(2,5));
        return ret;
    }
    
     // Input is "c4" which Hex is "11000100" 
     // Output will be ret[0] = "4" ; ret[1] = "0"
     public static List<String> DecodeHexToOctal(String hexstring){
        List<String> ret = new ArrayList<String>();
        int temp = Integer.parseInt(hexstring,16);
        String t = Integer.toBinaryString(temp);
        temp = Integer.parseInt(t.substring(5),2);
        ret.add(Integer.toString(temp));
        temp = Integer.parseInt(t.substring(2,5),2);
        ret.add(Integer.toString(temp));        
        return ret;
    }
     
    public static RegisterType CastRegister(String num){
        switch(Integer.parseInt(num)) {
            case 0 : return RegisterType.RAX;
            case 1 : return RegisterType.RCX;
            case 2 : return RegisterType.RDX;
            case 3 : return RegisterType.RBX;
            case 4 : return RegisterType.RSP;
            case 5 : return RegisterType.RBP;    
            case 6 : return RegisterType.RSI;  
            case 7 : return RegisterType.RDI;
            default : return null;
        }
    }
}
