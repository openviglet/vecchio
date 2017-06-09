package com.viglet.vecchio.security.jaas;

import com.viglet.vecchio.security.XMLSecurityDomain;
import com.viglet.vecchio.security.UnknownUserException;
import com.viglet.vecchio.security.InvalidCredentials;

import javax.security.auth.spi.LoginModule;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.security.auth.callback.*;
import java.util.logging.Logger;
import java.util.Map;
import java.io.IOException;
import java.security.Principal;

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
public class XMLLoginModule implements LoginModule {
  protected Logger logger = Logger.getAnonymousLogger();
  private Subject subject;
  private CallbackHandler callbackHandler;
  private XMLSecurityDomain domain;
  private boolean authenticated;
  private String username;
  private XMLUserPrincipal namePrincipal;



    
public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
                       Map<String, ?> options) {
  this.subject = subject;
  this.callbackHandler = callbackHandler;
  String realmName = (String) options.get("realm");
  domain = XMLSecurityDomain.getInstance(realmName);
}

public boolean login() throws LoginException {
  authenticated = false;
  Callback[] cb = new Callback[1];
  NameCallback nameCallback = new NameCallback("name: ");
  cb[0] = nameCallback;

  try {
    callbackHandler.handle(cb);
    username = nameCallback.getName();
    String question = null;
    try {
      question = domain.getQuestion(username);
    } catch (UnknownUserException e) {
      // ask dummy question
      question = "What is your mother's maiden name";
    }
    TextInputCallback answerCallBack = new TextInputCallback("answer this question: "+ question);
    cb[0] = answerCallBack;
    callbackHandler.handle(cb);
    String answer = answerCallBack.getText();

    domain.authenticate(username,answer);
    authenticated = true;

  } catch (IOException e) {
    throw new LoginException(e.getMessage());
  } catch (UnsupportedCallbackException e) {
    throw new LoginException(e.getMessage());
  } catch (UnknownUserException e) {
    throw new LoginException(e.getMessage());
  } catch (InvalidCredentials invalidCredentials) {
     authenticated = false;
  }
  return authenticated;
}

public boolean commit() throws LoginException {
  if (authenticated){
     namePrincipal = new XMLUserPrincipal(username);
     subject.getPrincipals().add(namePrincipal);
  }
  this.username = null;
  return authenticated;
}

  public boolean abort() throws LoginException {
    this.username = null;
    subject.getPrincipals().remove(namePrincipal);
    return authenticated;
  }

  public boolean logout() throws LoginException {
    if (authenticated) {
      subject.getPrincipals().remove(namePrincipal);
    }
    username = null;
    authenticated = false;
    return true;
  }
}
