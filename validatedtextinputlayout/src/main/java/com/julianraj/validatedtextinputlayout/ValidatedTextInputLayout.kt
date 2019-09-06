package com.julianraj.validatedtextinputlayout

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet

import com.google.android.material.textfield.TextInputLayout
import com.julianraj.validatedtextinputlayout.validator.BaseValidator
import com.julianraj.validatedtextinputlayout.validator.LengthValidator
import com.julianraj.validatedtextinputlayout.validator.RegexValidator
import com.julianraj.validatedtextinputlayout.validator.RequiredValidator

import java.util.ArrayList

/**
 * Extension of Android Design Library's [TextInputLayout]
 *
 * This class enable you to add validation to the TextInputLayout
 *
 * @author Julian Raj Manandhar
 */
class ValidatedTextInputLayout : TextInputLayout {
    private var validators: MutableList<BaseValidator>? = null
    /**
     * @return if auto-validation is enabled
     */
    var isAutoValidated = false
        private set
    /**
     * @return if auto-trimming input field value is enabled
     */
    var isAutoTrimEnabled = false
        private set

    /**
     * Flag to always enable error  for the [TextInputLayout]
     *
     * Enabled by default.
     *
     * Disabling will remove the error space below the Field once the validate method returns
     * true.
    * */
    private var errorAlwaysEnabled = true

    /**
     * Return a String value of the input field.
     *
     * This method will remove white spaces if auto-trimming is enabled.
     *
     * @return the value of the input field
     * @see .autoTrimValue
     */
    val value: String
        get() = if (isAutoTrimEnabled)
            editText!!.text.toString().trim { it <= ' ' }
        else
            editText!!.text.toString()

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
        initializeCustomAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
        initializeCustomAttrs(context, attrs)
    }

    private fun initialize() {
        if (!isInEditMode) {
            validators = ArrayList()
            this.post {
                if (!editText!!.isInEditMode)
                    initializeTextWatcher()
            }
        }
    }

    private fun initializeCustomAttrs(context: Context, attrs: AttributeSet) {
        if (!isInEditMode) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable
                    .ValidatedInputTextLayout, 0, 0)

            try {
                isAutoTrimEnabled = typedArray.getBoolean(R.styleable.ValidatedInputTextLayout_autoTrim,
                        false)
                isAutoValidated = typedArray.getBoolean(R.styleable
                        .ValidatedInputTextLayout_autoValidate, false)
                errorAlwaysEnabled = typedArray.getBoolean(R.styleable
                        .ValidatedInputTextLayout_errorAlwaysEnabled, true)

                initRequiredValidation(context, typedArray)
                initLengthValidation(context, typedArray)
                initRegexValidation(context, typedArray)
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun initRequiredValidation(context: Context, typedArray: TypedArray) {
        if (typedArray.getBoolean(R.styleable.ValidatedInputTextLayout_isRequired, false)) {
            var errorMessage = typedArray.getString(R.styleable
                    .ValidatedInputTextLayout_requiredValidationMessage)
            if (errorMessage == null)
                errorMessage = context.getString(R.string.default_required_validation_message)
            addValidator(RequiredValidator(errorMessage))
        }
    }

    private fun initLengthValidation(context: Context, typedArray: TypedArray) {
        val minLength = typedArray.getInteger(R.styleable.ValidatedInputTextLayout_minLength,
                LengthValidator.LENGTH_ZERO)
        val maxLength = typedArray.getInteger(R.styleable.ValidatedInputTextLayout_maxLength,
                LengthValidator.LENGTH_INDEFINITE)

        if (!(minLength == LengthValidator.LENGTH_ZERO && maxLength == LengthValidator
                        .LENGTH_INDEFINITE)) {
            var errorMessage = typedArray.getString(R.styleable
                    .ValidatedInputTextLayout_lengthValidationMessage)
            if (errorMessage == null) {
                if (minLength == LengthValidator.LENGTH_ZERO) {
                    errorMessage = context.getString(R.string
                            .default_required_length_message_max, maxLength)
                } else if (maxLength == LengthValidator.LENGTH_INDEFINITE) {
                    errorMessage = context.getString(R.string
                            .default_required_length_message_min, minLength)
                } else {
                    errorMessage = context.getString(R.string
                            .default_required_length_message_min_max, minLength, maxLength)
                }
            }
            addValidator(LengthValidator(minLength, maxLength, errorMessage))
        }
    }

    private fun initRegexValidation(context: Context, typedArray: TypedArray) {
        val regex = typedArray.getString(R.styleable.ValidatedInputTextLayout_regex)
        if (regex != null) {
            var errorMessage = typedArray.getString(R.styleable
                    .ValidatedInputTextLayout_regexValidationMessage)
            if (errorMessage == null)
                errorMessage = context.getString(R.string.default_regex_validation_message)
            addValidator(RegexValidator(regex, errorMessage))
        }
    }

    private fun initializeTextWatcher() {
        editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (isAutoValidated)
                    validate()
                else
                    error = null
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    /**
     * Clears all the validators associated with the [ValidatedTextInputLayout].
     */
    fun clearValidators() {
        validators!!.clear()
        isErrorEnabled = false
    }

    /**
     * Associates new Validator with the [ValidatedTextInputLayout].
     *
     * @param pValidator new validator to be associated with the input field
     */
    fun addValidator(pValidator: BaseValidator) {
        validators!!.add(pValidator)
    }

    /**
     * Enable or disable auto-validation for the [ValidatedTextInputLayout].
     *
     * @param flag flag to enable or disable auto-validation
     */
    fun autoValidate(flag: Boolean) {
        isAutoValidated = flag
    }

    /**
     * Enable or disable auto-trimming of the value of the input field for the
     * [ValidatedTextInputLayout].
     *
     * Enabling will remove any leading and trailing white space from the value of field.
     *
     * Caution: You may not want to enable this in case of password fields.
     *
     * @param flag flag to enable or disable auto-trimming of value
     */
    fun autoTrimValue(flag: Boolean) {
        isAutoTrimEnabled = flag
    }

    /**
     * Return a boolean which can be used to check the validity of the field.
     *
     * @return boolean indicating if the field is valid or not.
     */
    fun validate(): Boolean {
        var status = true
        val text = value
        for (validator in validators!!) {
            if (!validator.validate(text)) {
                isErrorEnabled = true
                error = validator.errorMessage
                status = false
                break
            } else {
                error = null
            }
        }
        if (status && !errorAlwaysEnabled) isErrorEnabled = false
        return status
    }
}