package com.valiant.repel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.valiant.repel.models.Question;

import javax.annotation.Nullable;

public class QuestionDetailActivity
extends BaseActivity
implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_QUESTION_KEY = "question_key";

    private FirebaseFirestore databaseReference;
    private DocumentReference questionReference;
    private DocumentReference answersReference;
    private EventListener questionListener;
    private String questionKey;

    private TextView authorView;
    private TextView contentView;
    private TextView dateView;
//    private EditText answerField;
//    private Button answerButton;
//    private RecyclerView answersRecycler;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
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
                                username = document.getString("username");
                                TextView usernameTextView = (TextView) findViewById(R.id.usernameView);
                                usernameTextView.setText(username);
                            }
                        }
                    }
                });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get question key from intent
        questionKey = getIntent().getStringExtra(EXTRA_QUESTION_KEY);
        if (questionKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_QUESTION_KEY");
        }

        // Initialize Database
        databaseReference = FirebaseFirestore.getInstance();
        questionReference = databaseReference.collection("questions").document(questionKey);
        answersReference = databaseReference.collection("answers").document(questionKey);


        // Initialize Views
        authorView = findViewById(R.id.questionDetailAuthor);
        contentView = findViewById(R.id.questionDetailContent);
        dateView = findViewById(R.id.questionDetailDate);
//        answerField = findViewById(R.id.fieldAnswerText);
//        answerButton = findViewById(R.id.buttonQuestionAnswer);
//        answersRecycler = findViewById(R.id.recyclerQuestionAnswers);
//
//        answerButton.setOnClickListener(this);
//        answersRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the question
        // [START question_value_event_listener]
        EventListener questionEventListener = new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                Question question = documentSnapshot.toObject(Question.class);
                // [START_EXCLUDE]
                authorView.setText(question.author);
                contentView.setText(question.content);
                dateView.setText(question.creationDate.toString());
            }
        };
        questionReference.addSnapshotListener(questionEventListener);
        // [END question_value_event_listener]

        // Keep copy of question listener so we can remove it when app stops
        questionListener = questionEventListener;

        // Listen for comments
//        adapter = new AnswerAdapter(this, answersReference);
//        answersRecycler.setAdapter(adapter);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // Remove post value event listener
////        if (questionListener != null) {
////            questionReference.removeEventListener(questionListener);
////        }
//
//        // Clean up comments listener
////        adapter.cleanupListener();
//    }

    @Override
    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.buttonPostComment) {
//            postComment();
//        }
    }

    private void toLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            toLoginActivity();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
