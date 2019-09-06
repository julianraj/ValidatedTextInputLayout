package com.julianraj.validatedtextinputlayout

import com.julianraj.validatedtextinputlayout.validator.RegexValidator

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class RegexValidatorTest {

    lateinit var mValidator: RegexValidator

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mValidator = RegexValidator(REGEX, "error message.")
    }

    @Test
    fun regexValidator_ReturnsTrue() {
        assertThat(mValidator.isValid(CORRECT_SAMPLE1), `is`(true))
        assertThat(mValidator.isValid(CORRECT_SAMPLE2), `is`(true))
    }

    @Test
    fun regexValidator_ReturnsFalse() {
        assertThat(mValidator.isValid(INCORRECT_SAMPLE1), `is`(false))
        assertThat(mValidator.isValid(INCORRECT_SAMPLE2), `is`(false))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    companion object {

        const val REGEX = "^[a-z]+@[0-9]{2,}$"
        const val CORRECT_SAMPLE1 = "asd@123"
        const val CORRECT_SAMPLE2 = "asdASD@12"
        const val INCORRECT_SAMPLE1 = "012@1"
        const val INCORRECT_SAMPLE2 = "as@1"
    }
}