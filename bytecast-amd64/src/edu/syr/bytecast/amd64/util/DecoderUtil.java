/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.util;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hapan
 */
public class DecoderUtil {
  
  public static int getRegField(byte num) {
    return (num >> 3 & 0x07);
  }
  
  public static int getRmField(byte num) {
    return (num & 0x07);
  }
  
  public static RegisterType getRegister(int num) {
    switch(num) {
      case 0:
        return RegisterType.RAX;
      case 1:
        return RegisterType.RCX;
      case 2:
        return RegisterType.RDX;
      case 3:
        return RegisterType.RBX;
      case 4:
        return RegisterType.RSP;
      case 5:
        return RegisterType.RBP;
      case 6:
        return RegisterType.RSI;
      case 7:
        return RegisterType.RDI;
      default:
        return null;
    }
  }
  
      // Input is "c4" which Hex is "11000100" 
    // Output will be ret[0] = "100" ; ret[1] = "000"
    public static List<String> DecodeHexToBinary(Byte hexbyte){
        List<String> ret = new ArrayList<String>();
        String t = Integer.toBinaryString(hexbyte);
        ret.add(t.substring(5));
        ret.add(t.substring(2,5));
        return ret;
    }
    
     // Input is "c4" which Hex is "11000100" 
     // Output will be ret[0] = "4" ; ret[1] = "0"
     public static List<String> DecodeHexToOctal(Byte hexbyte){
        List<String> ret = new ArrayList<String>();
        String t = Integer.toBinaryString(hexbyte);
        t = t.substring(24); //get last 8 digits.
        int temp = Integer.parseInt(t.substring(5),2);
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
