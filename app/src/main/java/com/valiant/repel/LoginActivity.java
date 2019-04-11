package com.valiant.repel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private AutoCompleteTextView emailView;
    private EditText passwordView;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        getUIElements();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearErrorMessages();
                attemptLogin();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearErrorMessages();
                toRegisterActivity();
            }
        });
    }

    private void signIn(String email, String password) {
        // TODO: Stronger validation for login
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                             Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            toMainActivity();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            authFailedToast().show();
//                            updateUI(null);
                        }

                        if (!task.isSuccessful()) {
                            // TODO: Display login failure message
                        }
                    }
                });
    }

    private void getUIElements() {
        loginButton = findViewById(R.id.email_sign_in_button);
        registerButton = findViewById(R.id.register_button);
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);
    }

    private void clearErrorMessages() {
        emailView.setError(null);
        passwordView.setError(null);
    }

    private void attemptLogin() {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        signIn(email, password);
    }

    private Toast authFailedToast() {
        return Toast.makeText(LoginActivity.this,
                getString(R.string.auth_failed_toast),
                Toast.LENGTH_SHORT);
    }

    private void toRegisterActivity() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void toMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }
    @Override
    public void onStart() {
        super.onStart();
        // TODO: Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }






}
