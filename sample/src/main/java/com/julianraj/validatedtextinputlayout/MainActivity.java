package com.julianraj.validatedtextinputlayout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.julianraj.validatedinputtextlayout.ValidatedTextInputLayout;
import com.julianraj.validatedinputtextlayout.validator.LengthValidator;
import com.julianraj.validatedinputtextlayout.validator.RequiredValidator;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ValidatedTextInputLayout mUsernameInput;
    ValidatedTextInputLayout mPasswordInput;

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
        mUsernameInput.addValidator(new RequiredValidator("This field is required"));
        mUsernameInput.addValidator(new LengthValidator(4, 8, "The length must be between 4 and 8"));
        mUsernameInput.setCounterEnabled(true);
        mUsernameInput.setCounterMaxLength(8);
        mUsernameInput.autoTrimValue(true);


        mPasswordInput = ((ValidatedTextInputLayout) findViewById(R.id.password));
        mPasswordInput.addValidator(new RequiredValidator("This field is required"));
        mPasswordInput.addValidator(new LengthValidator(6, LengthValidator.LENGTH_INDEFINITE,
                "The password must be of at-least 6 characters"));
        mPasswordInput.setCounterEnabled(true);
        mPasswordInput.autoValidate(true);
    }

    private void submitForm(){
        if(validateFields()) {
            Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .show();
        }else{
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

    private boolean validateFields(){
        boolean flag = true;
        if(!mUsernameInput.validate()) flag = false;
        if(!mPasswordInput.validate()) flag = false;
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
