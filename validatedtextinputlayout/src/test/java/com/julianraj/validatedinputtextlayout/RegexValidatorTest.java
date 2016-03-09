package com.julianraj.validatedinputtextlayout;

import com.julianraj.validatedinputtextlayout.validator.RegexValidator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RegexValidatorTest {

    public static final String REGEX = "^[a-z]+@[0-9]{2,}$";
    public static final String CORRECT_SAMPLE1 = "asd@123";
    public static final String CORRECT_SAMPLE2 = "asdASD@12";
    public static final String INCORRECT_SAMPLE1 = "012@1";
    public static final String INCORRECT_SAMPLE2 = "as@1";

    RegexValidator mValidator;

    @Before
    public void setUp() throws Exception {
        mValidator = new RegexValidator(REGEX, "error message.");
    }

    @Test
    public void regexValidator_ReturnsTrue() {
        assertThat(mValidator.isValid(CORRECT_SAMPLE1), is(true));
        assertThat(mValidator.isValid(CORRECT_SAMPLE2), is(true));
    }

    @Test
    public void regexValidator_ReturnsFalse() {
        assertThat(mValidator.isValid(INCORRECT_SAMPLE1), is(false));
        assertThat(mValidator.isValid(INCORRECT_SAMPLE2), is(false));
    }

    @After
    public void tearDown() throws Exception {
    }
}