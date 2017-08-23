package com.julianraj.validatedtextinputlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.julianraj.validatedtextinputlayout.validator.BaseValidator;
import com.julianraj.validatedtextinputlayout.validator.IValidator;
import com.julianraj.validatedtextinputlayout.validator.LengthValidator;
import com.julianraj.validatedtextinputlayout.validator.RegexValidator;
import com.julianraj.validatedtextinputlayout.validator.RequiredValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of Android Design Library's {@link TextInputLayout}
 * <p>This class enable you to add validation to the TextInputLayout
 *
 * @author Julian Raj Manandhar
 */
public class ValidatedTextInputLayout extends TextInputLayout {
    private List<BaseValidator> mValidators;
    private boolean mAutoValidate = false;
    private boolean mAutoTrimValue = false;
    private boolean mErrorAlwaysEnabled = true;

    public ValidatedTextInputLayout(Context context) {
        super(context);
        initialize();
    }

    public ValidatedTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
        initializeCustomAttrs(context, attrs);
    }

    public ValidatedTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
        initializeCustomAttrs(context, attrs);
    }

    private void initialize() {
        if (!isInEditMode()) {
            mValidators = new ArrayList<>();
            this.post(new Runnable() {
                @Override
                public void run() {
                    if (!getEditText().isInEditMode())
                        initializeTextWatcher();
                }
            });
        }
    }

    private void initializeCustomAttrs(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                    .ValidatedInputTextLayout, 0, 0);

            try {
                mAutoTrimValue = typedArray.getBoolean(R.styleable.ValidatedInputTextLayout_autoTrim,
                        false);
                mAutoValidate = typedArray.getBoolean(R.styleable
                        .ValidatedInputTextLayout_autoValidate, false);
                mErrorAlwaysEnabled = typedArray.getBoolean(R.styleable
                        .ValidatedInputTextLayout_errorAlwaysEnabled, true);

                initRequiredValidation(context, typedArray);
                initLengthValidation(context, typedArray);
                initRegexValidation(context, typedArray);
            } finally {
                typedArray.recycle();
            }
        }
    }

    private void initRequiredValidation(Context context, TypedArray typedArray) {
        if (typedArray.getBoolean(R.styleable.ValidatedInputTextLayout_isRequired, false)) {
            String errorMessage = typedArray.getString(R.styleable
                    .ValidatedInputTextLayout_requiredValidationMessage);
            if (errorMessage == null)
                errorMessage = context.getString(R.string.default_required_validation_message);
            addValidator(new RequiredValidator(errorMessage));
        }
    }

    private void initLengthValidation(Context context, TypedArray typedArray) {
        int minLength = typedArray.getInteger(R.styleable.ValidatedInputTextLayout_minLength,
                LengthValidator.LENGTH_ZERO);
        int maxLength = typedArray.getInteger(R.styleable.ValidatedInputTextLayout_maxLength,
                LengthValidator.LENGTH_INDEFINITE);

        if (!(minLength == LengthValidator.LENGTH_ZERO && maxLength == LengthValidator
                .LENGTH_INDEFINITE)) {
            String errorMessage = typedArray.getString(R.styleable
                    .ValidatedInputTextLayout_lengthValidationMessage);
            if (errorMessage == null) {
                if (minLength == LengthValidator.LENGTH_ZERO) {
                    errorMessage = context.getString(R.string
                            .default_required_length_message_max, maxLength);
                } else if (maxLength == LengthValidator.LENGTH_INDEFINITE) {
                    errorMessage = context.getString(R.string
                            .default_required_length_message_min, minLength);
                } else {
                    errorMessage = context.getString(R.string
                            .default_required_length_message_min_max, minLength, maxLength);
                }
            }
            addValidator(new LengthValidator(minLength, maxLength, errorMessage));
        }
    }

    private void initRegexValidation(Context context, TypedArray typedArray) {
        String regex = typedArray.getString(R.styleable.ValidatedInputTextLayout_regex);
        if (regex != null) {
            String errorMessage = typedArray.getString(R.styleable
                    .ValidatedInputTextLayout_regexValidationMessage);
            if (errorMessage == null)
                errorMessage = context.getString(R.string.default_regex_validation_message);
            addValidator(new RegexValidator(regex, errorMessage));
        }
    }

    private void initializeTextWatcher() {
        getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isAutoValidated()) validate();
                else setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Clears all the validators associated with the {@link ValidatedTextInputLayout}.
     */
    public void clearValidators() {
        mValidators.clear();
        setErrorEnabled(false);
    }

    /**
     * Associates new {@link IValidator} with the {@link ValidatedTextInputLayout}.
     *
     * @param pValidator new validator to be associated with the input field
     */
    public void addValidator(BaseValidator pValidator) {
        mValidators.add(pValidator);
    }

    /**
     * Enable or disable auto-validation for the {@link ValidatedTextInputLayout}.
     *
     * @param flag flag to enable or disable auto-validation
     */
    public void autoValidate(boolean flag) {
        mAutoValidate = flag;
    }

    /**
     * @return if auto-validation is enabled
     */
    public boolean isAutoValidated() {
        return mAutoValidate;
    }

    /**
     * Enable or disable auto-trimming of the value of the input field for the
     * {@link ValidatedTextInputLayout}.
     * <p>Enabling will remove any leading and trailing white space from the value of field.</p>
     * <p>Caution: You may not want to enable this in case of password fields.</p>
     *
     * @param flag flag to enable or disable auto-trimming of value
     */
    public void autoTrimValue(boolean flag) {
        mAutoTrimValue = flag;
    }

    /**
     * Flag to always enable error  for the {@link TextInputLayout}
     * <p>Enabled by default.</p>
     * <p>Disabling will remove the error space below the Field once the validate method returns
     * true.
     * .</p>
     *
     * @param errorAlwaysEnabled flag to set error-space as always enabled
     */
    public void setErrorAlwaysEnabled(boolean errorAlwaysEnabled) {
        mErrorAlwaysEnabled = errorAlwaysEnabled;
    }

    /**
     * @return if auto-trimming input field value is enabled
     */
    public boolean isAutoTrimEnabled() {
        return mAutoTrimValue;
    }

    /**
     * Return a boolean which can be used to check the validity of the field.
     *
     * @return boolean indicating if the field is valid or not.
     */
    public boolean validate() {
        boolean status = true;
        String text = getValue();
        for (BaseValidator validator : mValidators) {
            if (!validator.validate(text)) {
                setErrorEnabled(true);
                setError(validator.getErrorMessage());
                status = false;
                break;
            } else {
                setError(null);
            }
        }
        if (status && !mErrorAlwaysEnabled) setErrorEnabled(false);
        return status;
    }

    /**
     * Return a String value of the input field.
     * <p>This method will remove white spaces if auto-trimming is enabled.</p>
     *
     * @return the value of the input field
     * @see #autoTrimValue(boolean)
     */
    public String getValue() {
        if (isAutoTrimEnabled()) return getEditText().getText().toString().trim();
        else return getEditText().getText().toString();
    }
}