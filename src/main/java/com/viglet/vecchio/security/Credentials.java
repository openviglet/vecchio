package com.viglet.vecchio.security;

import java.io.Serializable;
import java.util.Arrays;
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
public class Credentials implements Serializable {
  protected Logger logger = Logger.getAnonymousLogger();

  private String question;
  private byte[] answerDigest;

  public String getQuestion() {
    return question;
  }

  public Credentials(String question, byte[] answerDigest) {
    this.question = question;
    this.answerDigest = answerDigest;
  }

  public boolean isValid(String answer) {
    byte[] answerToCheck = AnswerDigester.digest(answer);


    return Arrays.equals(this.answerDigest, answerToCheck);

  }

  public byte[] getAnser() {
    return answerDigest;

  }

  public static Credentials getInstance(String question, String answer) throws InvalidCredentials {
    if (question==null || answer==null){
      throw new InvalidCredentials("question and anser must have a value");
    }
    byte[] b = AnswerDigester.digest(answer);
    return new Credentials(question,b);
  }


}
