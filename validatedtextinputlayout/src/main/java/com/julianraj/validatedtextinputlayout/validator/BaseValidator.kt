package com.julianraj.validatedtextinputlayout.validator

import com.julianraj.validatedtextinputlayout.ValidatedTextInputLayout

/**
 * Base Validator class to inherit from for custom validation.
 *
 * @param errorMessage Error message that will be displayed if validation fails.
 * @param callback callback with validation result
 *
 */
abstract class BaseValidator(val errorMessage: String,
                             val callback: ValidationCallback? = null) {

    /**
     * Validate the associated [ValidatedTextInputLayout].
     * Also call the callback method if it is provided
     *
     * @param text value associated with the input field
     *
     * @return validity of the field
     */
    fun validate(text: String): Boolean {
        val status = isValid(text)

        callback?.onValidation(status)

        return status
    }

    /**
     * Check if the associated [ValidatedTextInputLayout] is valid or not.
     *
     * @param text value associated with the input field
     * @return validity of the field
     */
    abstract fun isValid(text: String): Boolean
}
