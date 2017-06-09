package com.viglet.vecchio.security;

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
public class UsernameInUseException extends Exception {
  protected Logger logger = Logger.getAnonymousLogger();


  public UsernameInUseException() {
  }

  public UsernameInUseException(String message) {
    super(message);
  }

  public UsernameInUseException(String message, Throwable cause) {
    super(message, cause);
  }

  public UsernameInUseException(Throwable cause) {
    super(cause);
  }
}
