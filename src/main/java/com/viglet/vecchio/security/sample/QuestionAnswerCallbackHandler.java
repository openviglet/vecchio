package com.viglet.vecchio.security.sample;

import javax.security.auth.callback.*;
import javax.swing.*;
import java.io.IOException;

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
public class QuestionAnswerCallbackHandler implements CallbackHandler {
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (int i = 0; i < callbacks.length; i++) {
      Callback callback = callbacks[i];
      if (callback instanceof NameCallback) {
        NameCallback nameCallback = (NameCallback) callback;
        String username = prompt(nameCallback.getPrompt());
        nameCallback.setName(username);
      } else if (callback instanceof TextInputCallback) {
        TextInputCallback textInputCallback = (TextInputCallback) callback;
        String answer = prompt(textInputCallback.getPrompt());
        textInputCallback.setText(answer);
      } else
        throw new UnsupportedCallbackException(callback, "Not a valid callback type for this handle.");
    }
  }

  private String prompt(String text) {
    return JOptionPane.showInputDialog(null, text);
  }
}
