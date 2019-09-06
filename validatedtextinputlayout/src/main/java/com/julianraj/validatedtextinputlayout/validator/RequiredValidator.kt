package com.julianraj.validatedtextinputlayout.validator

import com.julianraj.validatedtextinputlayout.ValidatedTextInputLayout

/**
 * Validator to set input field as required.
 *
 * @param errorMessage Error message that will be displayed if validation fails.
 * @param callback callback with validation result
 *
 * @see BaseValidator
 */
class RequiredValidator(errorMessage: String,
                        callback: ValidationCallback? = null
) : BaseValidator(errorMessage, callback) {

    /**
     * Check if the associated [ValidatedTextInputLayout] is empty or not.
     *
     * @param text value associated with the input field
     * @return validity of the field
     */
    override fun isValid(text: String): Boolean {
        return text.isNotEmpty()
    }
}
