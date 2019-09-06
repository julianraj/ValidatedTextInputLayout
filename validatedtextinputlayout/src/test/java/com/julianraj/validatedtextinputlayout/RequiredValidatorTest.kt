package com.julianraj.validatedtextinputlayout

import com.julianraj.validatedtextinputlayout.validator.RequiredValidator

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.mockito.Mockito.CALLS_REAL_METHODS
import org.mockito.Mockito.mock
import org.mockito.Mockito.withSettings

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class RequiredValidatorTest {

    @Mock
    var validator: RequiredValidator? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        //MockitoAnnotations.initMocks(this);
        validator = mock(RequiredValidator::class.java, withSettings().defaultAnswer(CALLS_REAL_METHODS))
    }

    @Test
    fun requiredValidator_ReturnsTrue() {
        assertThat(validator!!.isValid(CORRECT_SAMPLE), `is`(true))
    }

    @Test
    fun requiredValidator_ReturnsFalse() {
        assertThat(validator!!.isValid(INCORRECT_SAMPLE1), `is`(false))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    companion object {
        const val CORRECT_SAMPLE = "test string"
        const val INCORRECT_SAMPLE1 = ""
    }
}