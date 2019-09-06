package com.julianraj.validatedtextinputlayout.validator

import com.julianraj.validatedtextinputlayout.ValidatedTextInputLayout

/**
 * Dependency Validator which checks dependency on other ValidatedTextInputLayout
 *
 * @param dependsOn Dependent Field
 * @param dependencyType Type of dependency between the fields (must be one of #TYPE_EQUAL
 * or TYPE_REQUIRED_IF_EXISTS)
 * @param errorMessage Error message that will be displayed if validation fails.
 * @param callback callback with validation result
 */
class DependencyValidator(private val dependsOn: ValidatedTextInputLayout,
                          private val dependencyType: DependencyType,
                          errorMessage: String,
                          callback: ValidationCallback? = null) :
        BaseValidator(errorMessage, callback) {

    override fun isValid(text: String): Boolean {
        return when (dependencyType) {
            DependencyType.TYPE_EQUAL -> text == dependsOn.value
            DependencyType.TYPE_EQUAL_IGNORE_CASE -> text.equals(dependsOn.value, true)
            DependencyType.TYPE_REQUIRED_IF_EXISTS -> {
                if (dependsOn.value.isEmpty()) true else text.isNotEmpty()
            }
        }
    }

    companion object {
        enum class DependencyType {
            /**
             * Denotes the value of the field must be equal to that of dependent field.
             */
            TYPE_EQUAL,
            /**
             * Denotes the value of the field must be equal to that of dependent field but
             * ingores the text-case.
             */
            TYPE_EQUAL_IGNORE_CASE,
            /**
             * Denotes the field to be required only if the value of dependent field is present.
             *
             * i.e. if dependentField.getValue() is not empty.
             */
            TYPE_REQUIRED_IF_EXISTS
        }
    }
}
