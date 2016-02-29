package com.julianraj.validatedinputtextlayout;

import com.julianraj.validatedinputtextlayout.validator.LengthValidator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class LengthValidatorTest {

    @Mock
    LengthValidator mValidator;

    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 10;

    public static final String CORRECT_SAMPLE = "test me";
    public static final String INCORRECT_SAMPLE1 = "me";
    public static final String INCORRECT_SAMPLE2 = "test me wrong";

    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        mValidator = mock(LengthValidator.class, withSettings().defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    public void lengthValidator_ReturnsTrue() {
        mValidator.setMaximumLength(LengthValidator.LENGTH_INDEFINITE);
        mValidator.setMinimumLength(MIN_LENGTH);
        assertThat(mValidator.isValid(CORRECT_SAMPLE), is(true));

        mValidator.setMaximumLength(MAX_LENGTH);
        mValidator.setMinimumLength(LengthValidator.LENGTH_ZERO);
        assertThat(mValidator.isValid(CORRECT_SAMPLE), is(true));

        mValidator.setMaximumLength(MAX_LENGTH);
        mValidator.setMinimumLength(MIN_LENGTH);
        assertThat(mValidator.isValid(CORRECT_SAMPLE), is(true));
    }

    @Test
    public void lengthValidator_ReturnsFalse() {
        assertThat(mValidator.isValid(INCORRECT_SAMPLE1), is(false));
        assertThat(mValidator.isValid(INCORRECT_SAMPLE2), is(false));
    }

    @After
    public void tearDown() throws Exception {
    }
}