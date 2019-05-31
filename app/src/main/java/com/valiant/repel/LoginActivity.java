package com.valiant.repel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextInputEditText emailView;
    private TextInputEditText passwordView;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
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
                            FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            DocumentReference documentReference = firebaseDatabase
                                    .collection("users")
                                    .document(user.getUid());
                            documentReference
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document != null) {
                                                    final String userClass = document.getString("class");
                                                    if (userClass != null && userClass.equals("admin")) {
                                                        toMainAdminActivity();
                                                    } else {
                                                        toMainUserActivity();
                                                    }
                                                }
                                            }
                                        }
                                    });                        } else {
                            authFailedToast().show();
                        }

//                        if (!task.isSuccessful()) {
                        // TODO: Display login failure message
//                        }
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
        final String email = emailView.getText().toString();
        final String password = passwordView.getText().toString();
        if(!email.equals("") && !password.equals("")) {
            signIn(email, password);
        }
    }

    private Toast authFailedToast() {
        return Toast.makeText(this,
                getString(R.string.auth_failed_toast),
                Toast.LENGTH_SHORT);
    }

    private void toRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void toMainUserActivity() {
        startActivity(new Intent(this, MainUserActivity.class));
    }

    private void toMainAdminActivity() {
        startActivity(new Intent(this, MainAdminActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        // TODO: Clean up this mess:
        if (currentUser != null) {
            FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            DocumentReference documentReference = firebaseDatabase
                    .collection("users")
                    .document(user.getUid());
            documentReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                               if (task.isSuccessful()) {
                                   DocumentSnapshot document = task.getResult();
                                   if (document != null) {
                                       final String userClass = document.getString("class");
                                       if (userClass != null && userClass.equals("admin")) {
                                           toMainAdminActivity();
                                       } else {
                                           toMainUserActivity();
                                       }
                                   }
                               }
                           }
                       });

        }
    }

}
