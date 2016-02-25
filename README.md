ValidatedTextInputLayout
========================

> Android library for text inputs with support for validation.
>> Extended from android design library's _TextInputLayout_.


## Demo ##  
![Basic](./images/demo.gif)

## Features ##
 - **AutoValidation**  
 Validate the input field as the text changes.  
    `mInput.autoValidate(true);`  
 If `false` you need to call the `validate()` method explicitly for validation.  
 
 - **Autotrim**  
 `mInput.getValue()` will return the trimmed value equivalent to `String.trim()` method    
    `mInput.autoTrimValue(true);`  
    
 - **Add Validators**  
 You can add multiple validators to a single input field.  
     `mInput.addValidator(/* Your first Validator class goes here */);`  
     `mInput.addValidator(/* Your second Validator class goes here */);`  
 
 - **Clear Validators**  
 Removes all the validators associated with the input field.  
    `mInput.clearValidators();`  
    
 - **Default Available Validators**  
    + **RequiredValidator**  
    Validates the input field as required. i.e. empty value is not valid.  
        `mInput.addValidator(new RequiredValidator("Your error message"));`  
        
    + **LengthValidator**  
    Validates the input field for minimum and maximum length specified.  
        `mInput.addValidator(new LengthValidator(8 /* Max Length */, "Your error message"));`  
        OR  
        `mInput.addValidator(new LengthValidator(4 /* Min Length */, *8 /* Max Length */, "Your error message"));`  
 
 - **Custom Validators**  
 You can create your own validators to use with ValidatedTextInputLayout just by extending the `BaseValidator` class.  
 You need to call the `super()` method with the desired message and override `isValid()` method to return true or false;    
 
 Example: Validator class to check if field value contains  character sequence "xyz"  
  
        public class MyValidator extends BaseValidator {
            public MyValidator(String pErrorMessage){
                super(pErrorMessage);
            }
            
            @Override
            public boolean isValid(String pText){
                return pText.contains("xyz");
            }
        }


**Thanks for using :D**  