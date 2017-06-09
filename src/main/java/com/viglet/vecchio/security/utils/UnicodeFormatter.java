package com.viglet.vecchio.security.utils;


/**
 * <p/>
 * About this class
 * </p>
 * This component and its source code representation are copyright protected
 * and proprietary to EDC4IT Ltd.
 * This component and source code may be used for instructional and evaluation
 * purposes only. No part of this component or its source code may be sold,
 * transferred, or publicly posted, nor may it be used in a commercial or
 * production environment, without the express written consent of EDC4IT Ltd.
 * <p/>
 * Copyright (c) 2004 EDC4IT Ltd.
 *
 * @author EDC4IT Ltd
 * @version $Revision: 6 $
 */
public class UnicodeFormatter {

  public static byte[] hexToBytes(String hexString) {
    byte[] bts = new byte[hexString.length() / 2];
    for (int i = 0; i < bts.length;i++) {
      String s = hexString.substring(i*2, (i*2) + 2);
      byte b = (byte) Integer.parseInt(s, 16);
      bts[i] = b;
    }
    return bts;
  }

  static public String bytesToHex(byte[] bytes) {
    // Returns hex String representation of byte b
    char hexDigit[] = {
      '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    StringBuilder builder = new StringBuilder();
    ;
    for (int i = 0; i < bytes.length; i++) {
      byte b = bytes[i];
      char[] array = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
      builder.append(array);
    }


    return builder.toString();
  }


} // class
