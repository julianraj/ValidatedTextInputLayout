package com.julianraj.validatedtextinputlayout;

import com.julianraj.validatedtextinputlayout.validator.RequiredValidator;

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
public class RequiredValidatorTest {

    @Mock
    RequiredValidator mValidator;

    public static final String CORRECT_SAMPLE = "test string";
    public static final String INCORRECT_SAMPLE1 = "";

    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        mValidator = mock(RequiredValidator.class, withSettings().defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    public void requiredValidator_ReturnsTrue() {
        assertThat(mValidator.isValid(CORRECT_SAMPLE), is(true));
    }

    @Test
    public void requiredValidator_ReturnsFalse() {
        assertThat(mValidator.isValid(INCORRECT_SAMPLE1), is(false));
    }

    @After
    public void tearDown() throws Exception {
    }
}