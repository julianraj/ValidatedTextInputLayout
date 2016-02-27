package com.julianraj.validatedinputtextlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.julianraj.validatedinputtextlayout.validator.IValidator;
import com.julianraj.validatedinputtextlayout.validator.LengthValidator;
import com.julianraj.validatedinputtextlayout.validator.RequiredValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 2/25/16.
 */
public class ValidatedTextInputLayout extends TextInputLayout {
    List<IValidator> mValidators;
    private boolean mAutoValidate = false;
    private boolean mAutoTrimValue = false;

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

    private void initializeCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .ValidatedInputTextLayout, 0, 0);

        try {
            mAutoTrimValue = typedArray.getBoolean(R.styleable.ValidatedInputTextLayout_autoTrim,
                    false);
            mAutoValidate = typedArray.getBoolean(R.styleable
                    .ValidatedInputTextLayout_autoValidate, false);

            if (typedArray.getBoolean(R.styleable.ValidatedInputTextLayout_isRequired, false)) {
                String errorMessage = typedArray.getString(R.styleable
                        .ValidatedInputTextLayout_requiredValidationMessage);
                if (errorMessage == null)
                    errorMessage = context.getString(R.string.default_required_validation_message);
                addValidator(new RequiredValidator(errorMessage));
            }

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
        } finally {
            typedArray.recycle();
        }
    }

    private void initialize() {
        mValidators = new ArrayList<>();
        this.post(new Runnable() {
            @Override
            public void run() {
                if (!getEditText().isInEditMode())
                    initializeTextWatcher();
            }
        });
    }

    private void initializeTextWatcher() {
        getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mAutoValidate) validate();
                else setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void clearValidators() {
        mValidators.clear();
        setErrorEnabled(false);
    }

    public void addValidator(IValidator pValidator) {
        mValidators.add(pValidator);
        setErrorEnabled(true);
    }

    public void autoValidate(boolean flag) {
        mAutoValidate = flag;
    }

    public void autoTrimValue(boolean flag) {
        mAutoTrimValue = flag;
    }

    public boolean validate() {
        boolean status = true;
        String text = getValue();
        for (IValidator validator : mValidators) {
            if (!validator.isValid(text)) {
                setError(validator.getErrorMessage());
                status = false;
                break;
            } else {
                setError(null);
            }
        }
        return status;
    }

    public String getValue() {
        if (mAutoTrimValue) return getEditText().getText().toString().trim();
        else return getEditText().getText().toString();
    }
}