package com.viglet.vecchio.security.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.viglet.vecchio.security.Credentials;
import com.viglet.vecchio.security.InvalidCredentials;

import java.util.HashMap;
import java.util.Map;
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
public class RealmXMLParser extends DefaultHandler {
  protected static Logger logger = Logger.getAnonymousLogger();
  Map<String, Credentials> realm = new HashMap<String, Credentials>();
  private String current;
  private String username;
  private String question;
  private byte[] digestedAnswer;
  private String answer;
  private int users;


  public Map<String, Credentials> getRealm() {
    return realm;
  }

  public void startDocument() throws SAXException {
    realm = new HashMap<String, Credentials>();
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    current = qName;

  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    if ("user".equals(qName)) {
      try {
        users++;
        Credentials credentials;

        if (digestedAnswer != null)
          credentials = new Credentials(question, digestedAnswer);
        else
          credentials = Credentials.getInstance(question, answer);

        realm.put(username, credentials);
      } catch (InvalidCredentials ic) {
         logger.warning("Not able to add user (# "+users+ ") with name: " +username+" to realm. " + ic.getMessage());
      }
      question =null;
      answer =null;
      digestedAnswer =null;
      username = null;

    }
  }

  public void characters(char ch[], int start, int length) throws SAXException {
    String value = new String(ch, start, length);
    if (value.trim().equals("")) {
      return;
    }
    if ("username".equals(current))
      username = value;
    else if ("question".equals(current))
      question = value;
    else if ("answer-digest".equals(current))
      try {
        digestedAnswer = com.viglet.vecchio.security.utils.UnicodeFormatter.hexToBytes(value);
      } catch (NumberFormatException e) {
        throw new NumberFormatException("Digested answer not valid, maybe used tag for plain text?");
      }
    else if ("answer".equals(current))
      answer = value;


  }
}
