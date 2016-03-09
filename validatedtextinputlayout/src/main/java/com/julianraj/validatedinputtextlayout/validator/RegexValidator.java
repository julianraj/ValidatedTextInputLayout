package com.julianraj.validatedinputtextlayout.validator;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

public class RegexValidator extends BaseValidator {

    final String mRegex;

    public RegexValidator(@NonNull String pRegex, @NonNull String pErrorMessage) {
        super(pErrorMessage);
        mRegex = pRegex;
    }

    @Override
    public boolean isValid(String pText) {
        return Pattern.compile(mRegex, Pattern.CASE_INSENSITIVE).matcher(pText).find();
    }
}
