package com.julianraj.validatedtextinputlayoutsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.julianraj.validatedtextinputlayout.validator.BaseValidator
import com.julianraj.validatedtextinputlayout.validator.DependencyValidator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { submitForm() }

        password.isCounterEnabled = true
        val dependencyValidator = DependencyValidator(password,
                DependencyValidator.Companion.DependencyType.TYPE_EQUAL,
                "Confirmation password does not match the password.")

        confirmationPassword.addValidator(dependencyValidator)

        custom.addValidator(object : BaseValidator("Only accepts the word 'Valid'",
                { status ->
                    if (!status)
                        Toast.makeText(this@MainActivity, "Your validation callback was called.",
                                Toast.LENGTH_SHORT).show()
                }) {
            override fun isValid(text: String): Boolean {
                return text.equals("valid", ignoreCase = true)
            }
        })
    }

    private fun submitForm() {
        if (validateFields()) {
            Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .show()
        } else {
            Snackbar.make(fab, "Check form for errors", Snackbar.LENGTH_LONG)
                    .setAction("Retry") { submitForm() }
                    .show()
        }
    }

    private fun validateFields(): Boolean {
        var flag = true
        if (!username.validate()) {
            flag = false
        }
        if (!password.validate()) flag = false
        if (!email.validate()) flag = false
        if (!confirmationPassword.validate()) flag = false
        if (!custom.validate()) flag = false
        return flag
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
