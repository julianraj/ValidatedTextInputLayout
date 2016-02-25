package com.julianraj.validatedinputtextlayout.validator;

/**
 * Created by julian on 2/10/16.
 */
public abstract class BaseValidator implements IValidator {

    public String mErrorMessage;

    public BaseValidator(String pErrorMessage) {
        setErrorMessage(pErrorMessage);
    }

    @Override
    public boolean isValid(String pText) {
        return false;
    }

    @Override
    public void setErrorMessage(String pErrorMessage) {
        mErrorMessage = pErrorMessage;
    }

    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
