package com.julianraj.validatedtextinputlayout.validator;

import android.support.annotation.NonNull;

import com.julianraj.validatedtextinputlayout.ValidatedTextInputLayout;

/**
 * Validator to set length constraints to the associated {@link ValidatedTextInputLayout}
 *
 * @see BaseValidator
 */
public class LengthValidator extends BaseValidator {

    /**
     * Default value for {@link #mMaximumLength}
     * <p>This sets the length to be unlimited.</p>
     */
    public static final int LENGTH_INDEFINITE = -1;

    /**
     * Default value for {@link #mMinimumLength}
     */
    public static final int LENGTH_ZERO = 0;

    /**
     * Minimum length that the value of field must be.
     */
    private int mMinimumLength = LENGTH_ZERO;

    /**
     * Maximum length that the value of field can be.
     */
    private int mMaximumLength = LENGTH_INDEFINITE;

    /**
     * @param pErrorMessage error message to display if validation fails
     */
    public LengthValidator(@NonNull String pErrorMessage) {
        super(pErrorMessage);
    }

    /**
     * @param pMaximumLength maximum length that the value of field can be
     * @param pErrorMessage  error message to display if validation fails
     */
    public LengthValidator(int pMaximumLength, @NonNull String pErrorMessage) {
        super(pErrorMessage);
        mMaximumLength = pMaximumLength;
    }

    /**
     * @param pMinimumLength minimum length that the value of field must be
     * @param pMaximumLength maximum length that the value of field can be
     * @param pErrorMessage  error message to display if validation fails
     */
    public LengthValidator(int pMinimumLength, int pMaximumLength, @NonNull String pErrorMessage) {
        super(pErrorMessage);
        mMinimumLength = pMinimumLength;
        mMaximumLength = pMaximumLength;
    }

    /**
     * @param pErrorMessage error message to display if validation fails
     * param pCallback callback for validation event
     */
    public LengthValidator(@NonNull String pErrorMessage, ValidationCallback pCallback) {
        super(pErrorMessage, pCallback);
    }

    /**
     * @param pMaximumLength maximum length that the value of field can be
     * @param pErrorMessage  error message to display if validation fails
     * @param pCallback callback for validation event
     */
    public LengthValidator(int pMaximumLength, @NonNull String pErrorMessage,
                           ValidationCallback pCallback) {
        super(pErrorMessage, pCallback);
        mMaximumLength = pMaximumLength;
    }

    /**
     * @param pMinimumLength minimum length that the value of field must be
     * @param pMaximumLength maximum length that the value of field can be
     * @param pErrorMessage  error message to display if validation fails
     * @param pCallback callback for validation event
     */
    public LengthValidator(int pMinimumLength, int pMaximumLength, @NonNull String pErrorMessage,
                           ValidationCallback pCallback) {
        super(pErrorMessage, pCallback);
        mMinimumLength = pMinimumLength;
        mMaximumLength = pMaximumLength;
    }

    /**
     * Check if the associated {@link ValidatedTextInputLayout} meets the length constraint
     * associated with it.
     *
     * @param pText value associated with the input field
     * @return validity of the field
     */
    @Override
    public boolean isValid(String pText) {
        int length = pText.length();
        if (getMaximumLength() == LENGTH_INDEFINITE) {
            return length >= getMinimumLength();
        } else {
            return (length >= getMinimumLength() && length <= getMaximumLength());
        }
    }

    /**
     * @param pMinimumLength minimum required length
     */
    public void setMinimumLength(int pMinimumLength) {
        mMinimumLength = pMinimumLength;
    }

    /**
     * @param pMaximumLength maximum valid length
     */
    public void setMaximumLength(int pMaximumLength) {
        mMaximumLength = pMaximumLength;
    }

    public int getMinimumLength() {
        return mMinimumLength;
    }

    public int getMaximumLength() {
        return mMaximumLength;
    }
}
