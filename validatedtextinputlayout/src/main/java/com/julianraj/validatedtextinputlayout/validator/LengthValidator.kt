package com.julianraj.validatedtextinputlayout.validator

import com.julianraj.validatedtextinputlayout.ValidatedTextInputLayout

/**
 * Validator to set length constraints to the associated [ValidatedTextInputLayout]
 *
 * @param minimumLength Minimum length that the value of field must be.
 * @param maximumLength Maximum length that the value of field can be.
 * @param errorMessage Error message that will be displayed if validation fails.
 * @param callback callback with validation result
 *
 * @see BaseValidator
 */
class LengthValidator(private val minimumLength: Int = LENGTH_ZERO,
                      private val maximumLength: Int = LENGTH_INDEFINITE,
                      errorMessage: String,
                      callback: ValidationCallback? = null) : BaseValidator(errorMessage, callback) {

    val minLength: Int
        get() {
            return minimumLength
        }
    val maxLength: Int
        get() {
            return maximumLength
        }

    /**
     * Check if the associated [ValidatedTextInputLayout] meets the length constraint
     * associated with it.
     *
     * @param text value associated with the input field
     * @return validity of the field
     */
    override fun isValid(text: String): Boolean {
        val length = text.length
        return if (maxLength == LENGTH_INDEFINITE) {
            length >= minLength
        } else {
            length in minLength..maxLength
        }
    }

    companion object {
        /**
         * Default value for [maximumLength]
         *
         * This sets the length to be unlimited.
         */
        const val LENGTH_INDEFINITE = -1

        /**
         * Default value for [minimumLength]
         */
        const val LENGTH_ZERO = 0
    }
}
