package com.julianraj.validatedinputtextlayout.validator;

/**
 * Created by julian on 2/10/16.
 */
public class LengthValidator extends BaseValidator {
    public static final int LENGTH_INDEFINITE = -1;
    public static final int LENGTH_ZERO = 0;

    int mMinimumLength = LENGTH_ZERO, mMaximumLength = LENGTH_INDEFINITE;

    public LengthValidator(int pMaximumLength, String pErrorMessage) {
        super(pErrorMessage);
        mMaximumLength = pMaximumLength;
    }

    public LengthValidator(int pMinimumLength, int pMaximumLength, String pErrorMessage) {
        super(pErrorMessage);
        mMinimumLength = pMinimumLength;
        mMaximumLength = pMaximumLength;
    }

    @Override
    public boolean isValid(String pText) {
        int length = pText.length();
        if(mMaximumLength == LENGTH_INDEFINITE) {
            return length >= mMinimumLength;
        }else{
            return (length >= mMinimumLength && length <= mMaximumLength);
        }
    }
}
