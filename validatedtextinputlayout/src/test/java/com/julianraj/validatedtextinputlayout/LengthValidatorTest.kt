package com.julianraj.validatedtextinputlayout

import com.julianraj.validatedtextinputlayout.validator.LengthValidator
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class LengthValidatorTest {

    @Mock
    var validator: LengthValidator? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        //MockitoAnnotations.initMocks(this);
        validator = mock(LengthValidator::class.java, withSettings().defaultAnswer(CALLS_REAL_METHODS))
    }

    @Test
    fun lengthValidator_ReturnsTrue() {
        `when`(validator!!.maxLength).thenReturn(LengthValidator.LENGTH_INDEFINITE)
        `when`(validator!!.minLength).thenReturn(MIN_LENGTH)
        assertThat(validator!!.isValid(CORRECT_SAMPLE), `is`(true))

        `when`(validator!!.maxLength).thenReturn(MAX_LENGTH)
        `when`(validator!!.minLength).thenReturn(LengthValidator.LENGTH_ZERO)
        assertThat(validator!!.isValid(CORRECT_SAMPLE), `is`(true))

        `when`(validator!!.maxLength).thenReturn(MAX_LENGTH)
        `when`(validator!!.minLength).thenReturn(MIN_LENGTH)
        assertThat(validator!!.isValid(CORRECT_SAMPLE), `is`(true))
    }

    @Test
    fun lengthValidator_ReturnsFalse() {
        assertThat(validator!!.isValid(INCORRECT_SAMPLE1), `is`(false))
        assertThat(validator!!.isValid(INCORRECT_SAMPLE2), `is`(false))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    companion object {

        const val MIN_LENGTH = 4
        const val MAX_LENGTH = 10

        const val CORRECT_SAMPLE = "test me"
        const val INCORRECT_SAMPLE1 = "me"
        const val INCORRECT_SAMPLE2 = "test me wrong"
    }
}