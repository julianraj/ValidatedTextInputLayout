package com.julianraj.validatedinputtextlayout.validator;

/**
 * Created by julian on 2/10/16.
 */
public class RequiredValidator extends BaseValidator {

    public RequiredValidator(String pErrorMessage) {
        super(pErrorMessage);
    }

    @Override
    public boolean isValid(String pText) {
        return !pText.isEmpty();
    }
}
