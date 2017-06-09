package com.viglet.vecchio.security.sample;

import javax.security.auth.Subject;
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
public class PolicyTester extends LoginTester {
  protected static Logger logger = Logger.getAnonymousLogger();
  private final String FILENAME = "resources\\JenniferFile.txt";

  public static void main(String[] args) {
    PolicyTester tester = new PolicyTester();


    tester.run();

  }

  public void run() {
    System.out.print("First try to open file without login ...");
    try {
      EchoFileAction.display(FILENAME);
    } catch (Exception e) {
      System.out.println("FAILED.." + e.getMessage());
    }

    System.out.print("Now with login ...");
    try {

      
      Subject subject = login();
      String fileName = "resources\\JenniferFile.txt";
   //   PrivilegedAction action = new Echo FileAction(fileName);
   //   Subject.doAsPrivileged(subject, action, null);


    } catch (Exception e) {
      System.out.println("FAILED.." + e.getMessage());
      e.printStackTrace(System.out);
    }
  }


}
