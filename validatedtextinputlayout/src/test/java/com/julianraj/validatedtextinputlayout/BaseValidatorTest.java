package com.julianraj.validatedtextinputlayout;

import com.julianraj.validatedtextinputlayout.validator.BaseValidator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BaseValidatorTest {

    BaseValidator mValidator;

    public static final String ERROR_MESSAGE = "I am error message.";

    @Before
    public void setUp() throws Exception {
        mValidator = new BaseValidator(ERROR_MESSAGE) {
            @Override
            public boolean isValid(String text) {
                return false;
            }
        };
    }

    @Test
    public void baseValidator_Constructor() {
        assertThat(mValidator.getErrorMessage(), is(ERROR_MESSAGE));
    }
}