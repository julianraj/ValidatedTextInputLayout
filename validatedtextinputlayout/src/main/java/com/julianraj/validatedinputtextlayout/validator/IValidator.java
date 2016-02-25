package com.julianraj.validatedinputtextlayout.validator;

/**
 * Created by julian on 2/10/16.
 */
public interface IValidator {
    boolean isValid(String pText);
    void setErrorMessage(String pErrorMessage);
    String getErrorMessage();
}
