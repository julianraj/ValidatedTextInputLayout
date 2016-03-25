package com.julianraj.validatedtextinputlayout.validator;

import com.julianraj.validatedtextinputlayout.ValidatedTextInputLayout;

/**
 * Validator to set input field as required.
 * @see BaseValidator
 */
public class RequiredValidator extends BaseValidator {

    /**
     * @param pErrorMessage error message to display if validation fails
     */
    public RequiredValidator(String pErrorMessage) {
        super(pErrorMessage);
    }

    /**
     * Check if the associated {@link ValidatedTextInputLayout} is empty or not.
     *
     * @param pText value associated with the input field
     * @return validity of the field
     */
    @Override
    public boolean isValid(String pText) {
        return !pText.isEmpty();
    }
}
