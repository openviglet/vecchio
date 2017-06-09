package com.viglet.vecchio.security;


import com.viglet.vecchio.security.xml.RealmXMLWriter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

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
public class XMLSecurityDomain {
  private static Logger logger = Logger.getAnonymousLogger();
  private static final Map<String, XMLSecurityDomain> domains = new HashMap<String, XMLSecurityDomain>();
  private Map<String, Credentials> realm = new HashMap<String, Credentials>();
  private static File file;


  private XMLSecurityDomain(Map<String, Credentials> realm, String name) {
    this.realm = realm;
  }

  public static XMLSecurityDomain getInstance(){
    return getInstance(null);
  }
  public static XMLSecurityDomain getInstance(String name) {

    if (name==null)
    name = "default.xml";

    XMLSecurityDomain domainXML = domains.get(name);
    if (domainXML == null) {
      String baseDir = System.getProperty("security.securitydomain.realmdir");

      file = new File(baseDir,name);

      Map<String, Credentials> realm;
      if (file.exists())
        realm = RealmXMLWriter.open(file);
      else
        realm = new HashMap<String, Credentials>();

      domainXML = new XMLSecurityDomain(realm, name);
      domains.put("name", domainXML);
    }
    return domainXML;
  }


  public void addUser(String username, String question, String answer) throws UsernameInUseException, InvalidCredentials {
    if (realm.containsKey(username))
      throw new UsernameInUseException(username + " is already in use");
      Credentials credentials = Credentials.getInstance(question, answer);
      realm.put(username, credentials);
  }

  public void authenticate(String userName, String answer) throws InvalidCredentials, UnknownUserException {
    Credentials credentials = getCredentials(userName);
    if (!credentials.isValid(answer))
      throw new InvalidCredentials("Incorrect answer");
  }


  public String getQuestion(String userName) throws UnknownUserException {
    Credentials credentials = getCredentials(userName);

    return credentials.getQuestion();
  }

  private Credentials getCredentials(String userName) throws UnknownUserException {
    Credentials credentials = realm.get(userName);
    if (credentials == null) {
      throw new UnknownUserException(userName + " is not a valid username");
    }
    return credentials;
  }

  { // static piece to register a JVM ShutdownHook to close all domains
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        Collection<XMLSecurityDomain> xmlSecurityDomains = domains.values();
        Iterator<XMLSecurityDomain> iterator = xmlSecurityDomains.iterator();
        while (iterator.hasNext()) {
          XMLSecurityDomain xmlSecurityDomain = iterator.next();
          xmlSecurityDomain.close();
        }
      }
    });
  }

  private void close() {
    try {
      RealmXMLWriter.write(realm, file);
    } catch (IOException e) {
      logger.log(Level.SEVERE,"cannot write realm file " + file, e );
    }

  }
}
