package com.julianraj.validatedtextinputlayoutsample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.julianraj.validatedtextinputlayout.ValidatedTextInputLayout;
import com.julianraj.validatedtextinputlayout.validator.BaseValidator;
import com.julianraj.validatedtextinputlayout.validator.DependencyValidator;
import com.julianraj.validatedtextinputlayout.validator.ValidationCallback;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ValidatedTextInputLayout mUsernameInput;
    ValidatedTextInputLayout mPasswordInput;
    ValidatedTextInputLayout mConfPasswordInput;
    ValidatedTextInputLayout mEmailInput;
    ValidatedTextInputLayout mCustomInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        mUsernameInput = ((ValidatedTextInputLayout) findViewById(R.id.username));

        mPasswordInput = ((ValidatedTextInputLayout) findViewById(R.id.password));
        mPasswordInput.setCounterEnabled(true);

        mConfPasswordInput = ((ValidatedTextInputLayout) findViewById(R.id.conf_password));
        mConfPasswordInput.addValidator(new DependencyValidator(mPasswordInput,
                DependencyValidator.TYPE_EQUAL, "Confirmation password does not match the " +
                "password."));

        mEmailInput = ((ValidatedTextInputLayout) findViewById(R.id.email));

        mCustomInput = ((ValidatedTextInputLayout) findViewById(R.id.custom));
        ValidationCallback mCallback = new ValidationCallback() {
            @Override
            public void onValidation(boolean status) {
                if (!status)
                    Toast.makeText(MainActivity.this, "Your validation callback was called.",
                            Toast.LENGTH_SHORT).show();
            }
        };
        mCustomInput.addValidator(new BaseValidator("Only accepts the word 'Valid'", mCallback) {
            @Override
            public boolean isValid(String pText) {
                return pText.equalsIgnoreCase("valid");
            }
        });
    }

    private void submitForm() {
        if (validateFields()) {
            Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .show();
        } else {
            Snackbar.make(fab, "Check form for errors", Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            submitForm();
                        }
                    })
                    .show();
        }
    }

    private boolean validateFields() {
        boolean flag = true;
        if (!mUsernameInput.validate()) {
            flag = false;
        }
        if (!mPasswordInput.validate()) flag = false;
        if (!mEmailInput.validate()) flag = false;
        if (!mConfPasswordInput.validate()) flag = false;
        if (!mCustomInput.validate()) flag = false;
        return flag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
