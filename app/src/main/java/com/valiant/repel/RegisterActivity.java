package com.valiant.repel;

import androidx.appcompat.app.AppCompatActivity;
import util.DataValidation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView emailView;
    private AutoCompleteTextView firstNameView;
    private EditText passwordView;
    private DatePicker birthDatePicker;
    private CheckBox termsAndCondsBox;
    private View progressView;
    private View registerFormView;
    private LoginActivity.UserLoginTask authTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the register form.
        emailView = (AutoCompleteTextView) findViewById(R.id.email);
        passwordView = (EditText) findViewById(R.id.register_password);
        firstNameView = (AutoCompleteTextView) findViewById(R.id.first_name);
        birthDatePicker = (DatePicker) findViewById(R.id.date_picker);
        termsAndCondsBox = (CheckBox) findViewById(R.id.terms_and_conds_box);

        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });



        registerFormView = findViewById(R.id.register_form);
        progressView = findViewById(R.id.register_progress);

    }

    private void attemptRegister() {
        if (authTask != null) {
            return;
        }

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);
        firstNameView.setError(null);
        termsAndCondsBox.setError(null);

        // Store values at the time of the register attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String firstName = firstNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !DataValidation.isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!DataValidation.isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        // Check for a valid first name
        if (TextUtils.isEmpty(firstName)) {
            firstNameView.setError(getString(R.string.error_field_required));
            focusView = firstNameView;
            cancel = true;
        } else if (!DataValidation.isFirstNameValid(firstName)) {
            emailView.setError(getString(R.string.error_invalid_first_name));
            focusView = emailView;
            cancel = true;
        }

        // Check for Terms & Conds agreement
        if(!termsAndCondsBox.isChecked()){
            termsAndCondsBox.setError(getString(R.string.error_check_box_unchecked));
            focusView = termsAndCondsBox;
            cancel = true;
        }

        // Check for valid age
        if(!DataValidation.isAgeValid(birthDatePicker)) {
            focusView = birthDatePicker;
            cancel = true;

        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            authTask = new LoginActivity.UserLoginTask(email, password);
//            authTask.execute((Void) null);
        }
    }

}
