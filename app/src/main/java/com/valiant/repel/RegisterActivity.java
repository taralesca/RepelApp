package com.valiant.repel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseDatabase;

    private AutoCompleteTextView emailView;
    private AutoCompleteTextView usernameView;
    private EditText passwordView;
    private CheckBox termsAndConditionsBox;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseFirestore.getInstance();

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


                    Map<String, Object> userData = new HashMap<>();
                    userData.put("username", usernameView.getText().toString());
                    firebaseDatabase
                        .collection("users").document(user.getUid())
                        .set(userData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("RegisterActivity","User successfully added!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("RegisterActivity", "Error adding user", e);
                            }
                        });


                } else {
//                     If sign in fails, display a message to the user.
                    authFailedToast(task.getException().getMessage()).show();
//                            updateUI(null);
                }
            }
        };
    }

    private void getUIElements() {
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.register_password);
        usernameView = findViewById(R.id.username);
        termsAndConditionsBox = findViewById(R.id.terms_and_conds_box);
        registerButton = findViewById(R.id.register_button);
    }

    private Toast authFailedToast(String message) {
        return Toast.makeText(RegisterActivity.this,
//                getString(R.string.auth_failed_toast),
                message,
                Toast.LENGTH_LONG);
    }

    private void clearErrorMessages() {
        emailView.setError(null);
        passwordView.setError(null);
        usernameView.setError(null);
        termsAndConditionsBox.setError(null);
    }

    private void attemptRegister() {

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        createAccount(email, password);
    }

}
