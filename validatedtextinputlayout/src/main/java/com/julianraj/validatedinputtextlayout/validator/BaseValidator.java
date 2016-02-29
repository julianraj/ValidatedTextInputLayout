package com.julianraj.validatedinputtextlayout.validator;

import android.support.annotation.NonNull;

import com.julianraj.validatedinputtextlayout.ValidatedTextInputLayout;

/**
 * Base Validator class to inherit from for custom validation.
 */
public abstract class BaseValidator implements IValidator {

    /**
     * Error message that will be displayed if validation fails.
     * <p>This field must be set via constructor of the Custom Validation class.</p>
     */
    protected String mErrorMessage;

    /**
     * Sole constructor. (For invocation by subclass constructors)
     *
     * @param pErrorMessage error message to display if validation fails
     */
    public BaseValidator(@NonNull String pErrorMessage) {
        setErrorMessage(pErrorMessage);
    }

    /**
     * Check if the associated {@link ValidatedTextInputLayout} is valid or not.
     *
     * @param pText value associated with the input field
     * @return validity of the field
     */
    @Override
    public abstract boolean isValid(String pText);

    /**
     * Sets the error message to display if the validation fails.
     *
     * @param pErrorMessage error message to display if validation fails
     */
    @Override
    public void setErrorMessage(@NonNull String pErrorMessage) {
        mErrorMessage = pErrorMessage;
    }

    /**
     * Get the error message associated with the {@link BaseValidator}
     *
     * @return error message
     */
    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
