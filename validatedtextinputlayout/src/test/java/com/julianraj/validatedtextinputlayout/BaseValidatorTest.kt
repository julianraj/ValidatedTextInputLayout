package com.julianraj.validatedtextinputlayout

import com.julianraj.validatedtextinputlayout.validator.BaseValidator

import org.junit.Before
import org.junit.Test

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class BaseValidatorTest {

    lateinit var validator: BaseValidator

    @Before
    @Throws(Exception::class)
    fun setUp() {
        validator = object : BaseValidator(ERROR_MESSAGE) {
            override fun isValid(text: String): Boolean {
                return false
            }
        }
    }

    @Test
    fun baseValidator_Constructor() {
        assertThat(validator.errorMessage, `is`(ERROR_MESSAGE))
    }

    companion object {
        const val ERROR_MESSAGE = "I am error message."
    }
}