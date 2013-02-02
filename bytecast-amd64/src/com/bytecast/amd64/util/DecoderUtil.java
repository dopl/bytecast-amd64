/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytecast.amd64.util;

import com.bytecast.amd64.api.constants.RegisterType;

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
  
}
