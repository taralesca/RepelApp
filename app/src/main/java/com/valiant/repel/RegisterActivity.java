package com.valiant.repel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private AutoCompleteTextView emailView;
    private AutoCompleteTextView firstNameView;
    private EditText passwordView;
    private CheckBox termsAndConditionsBox;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        getUIElements();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearErrorMessages();
                attemptRegister();
            }
        });
    }

    private void createAccount(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, getOnCompleteListener());
    }

    private OnCompleteListener<AuthResult> getOnCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
//                     Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                            updateUI(user);
                } else {
//                     If sign in fails, display a message to the user.
                    authFailedToast().show();
//                            updateUI(null);
                }
            }
        };
    }

    private void getUIElements() {
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.register_password);
        firstNameView = findViewById(R.id.first_name);
        termsAndConditionsBox = findViewById(R.id.terms_and_conds_box);
        registerButton = findViewById(R.id.register_button);
    }

    private Toast authFailedToast() {
        return Toast.makeText(RegisterActivity.this,
                getString(R.string.auth_failed_toast),
                Toast.LENGTH_SHORT);
    }

    private void clearErrorMessages() {
        emailView.setError(null);
        passwordView.setError(null);
        firstNameView.setError(null);
        termsAndConditionsBox.setError(null);
    }

    private void attemptRegister() {

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        createAccount(email, password);
    }

}
