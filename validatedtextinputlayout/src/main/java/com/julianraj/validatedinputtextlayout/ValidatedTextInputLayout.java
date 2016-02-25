package com.julianraj.validatedinputtextlayout;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.julianraj.validatedinputtextlayout.validator.IValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 2/25/16.
 */
public class ValidatedTextInputLayout extends TextInputLayout{
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
    }

    public ValidatedTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        mValidators = new ArrayList<>();
        this.post(new Runnable() {
            @Override
            public void run() {
                if(!getEditText().isInEditMode())
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
            }else{
                setError(null);
            }
        }
        return status;
    }

    public String getValue(){
        if(mAutoTrimValue) return getEditText().getText().toString().trim();
        else return getEditText().getText().toString();
    }
}