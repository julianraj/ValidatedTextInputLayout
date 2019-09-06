package com.julianraj.validatedtextinputlayout.validator

import com.julianraj.validatedtextinputlayout.ValidatedTextInputLayout

import java.util.regex.Pattern

/**
 * Validator to check if value satisfies given regular expression
 *
 * @param regex regular expression to check against
 * @param errorMessage Error message that will be displayed if validation fails.
 * @param callback callback with validation result
 *
 * @see BaseValidator
 */
class RegexValidator(private val regex: String,
                     errorMessage: String,
                     callback: ((Boolean)-> Unit)? = null) : BaseValidator(errorMessage, callback) {

    /**
     * Check if the associated [ValidatedTextInputLayout] has correct value.
     *
     * @param text value associated with the input field
     * @return validity of the field
     */
    override fun isValid(text: String): Boolean {
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(text).find()
    }
}
