package com.viglet.vecchio.security.sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.logging.Logger;

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
public class EchoFileAction implements PrivilegedAction {
  protected Logger logger = Logger.getAnonymousLogger();
  private String fileName;


  public EchoFileAction(String fileName) {
    this.fileName = fileName;
  }

  public Object run() {
    display(fileName);
    return null;
  }

  public static void display(String fileName) {
    try {
      String line = null;
      BufferedReader br =null;
      try {
      FileReader fr = new FileReader(fileName);

        br = new BufferedReader(fr);
        while ((line = br.readLine()) != null) {
          System.out.println(line);
        }
      } finally {
        if (br!=null) br.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
