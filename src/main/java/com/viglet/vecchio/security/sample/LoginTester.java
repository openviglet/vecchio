package com.viglet.vecchio.security.sample;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.Principal;
import java.util.Iterator;
import java.util.Set;
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
public class LoginTester {
  protected Logger logger = Logger.getAnonymousLogger();


  public static void main(String[] args) throws LoginException {
      LoginTester tester = new LoginTester();
    Subject subject = tester.login();
    System.out.println("Subject with principals:");
    Set<Principal> principals = subject.getPrincipals();
    for (Iterator<Principal> iterator = principals.iterator(); iterator.hasNext();) {
      Principal principal = iterator.next();
      System.out.println("\t"+principal.getName());
    }
   

  }

  protected  Subject login() throws LoginException {
    LoginContext loginContext;
    loginContext= new LoginContext("CourseDemo", new QuestionAnswerCallbackHandler());
    loginContext.login();
    Subject subject = loginContext.getSubject();
    

    return subject;
  }

}
