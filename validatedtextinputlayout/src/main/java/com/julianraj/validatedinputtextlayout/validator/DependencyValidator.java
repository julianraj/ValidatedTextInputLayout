package com.julianraj.validatedinputtextlayout.validator;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.julianraj.validatedinputtextlayout.ValidatedTextInputLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by julian on 3/10/16.
 */
public class DependencyValidator extends BaseValidator {

    /**
     * Denotes the value of the field must be equal to that of dependent field.
     */
    public static final int TYPE_EQUAL = 0;
    /**
     * Denotes the field to be required only if the value of dependent field is present.
     * <p>i.e. if dependentField.getValue() is not empty.</p>
     */
    public static final int TYPE_REQUIRED_IF_EXISTS = 1;

    /**
     * Dependent Field
     */
    final private ValidatedTextInputLayout mDependstOn;

    /**
     * Denotes the dependency type for the field.
     */
    final private int mDependencyType;


    /**
     * @param pDependsOn field for dependency
     * @param pDependencyType type of dependency between the fields (must be one of #TYPE_EQUAL
     *                        or TYPE_REQUIRED_IF_EXISTS)
     * @param pErrorMessage   error message to display if validation fails
     */
    public DependencyValidator(@NonNull ValidatedTextInputLayout pDependsOn, @DependencyType int
            pDependencyType, @NonNull String pErrorMessage) {
        super(pErrorMessage);
        mDependstOn = pDependsOn;
        mDependencyType = pDependencyType;
    }

    @Override
    public boolean isValid(String pText) {
        if (mDependencyType == TYPE_EQUAL) {
            return pText.equals(mDependstOn.getValue());
        } else if (mDependencyType == TYPE_REQUIRED_IF_EXISTS) {
            return mDependstOn.getValue().isEmpty() ? true : !pText.isEmpty();
        }
        return false;
    }

    @IntDef({TYPE_EQUAL, TYPE_REQUIRED_IF_EXISTS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DependencyType {
    }
}
