package com.valiant.repel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;

public class PostQuestionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String username;

    private TextInputEditText contentView;
    private CheckBox anonimityBox;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);

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
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getUIElements();

        setTitle("Post question");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearErrorMessages();
                toMainAdminActivity();
                attemptQuestionPost();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private void toLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }
    private void toMainAdminActivity() {
        startActivity(new Intent(this, MainAdminActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            toLoginActivity();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUIElements() {
        contentView = findViewById(R.id.question_content);
        anonimityBox = findViewById(R.id.question_anonimity_box);
        submitButton = findViewById(R.id.question_submit);
    }

    private void clearErrorMessages() {
        contentView.setError(null);
        anonimityBox.setError(null);
        submitButton.setError(null);
    }

    private void attemptQuestionPost() {

        String content = contentView.getText().toString();
        Boolean anonimity = anonimityBox.isChecked();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String authorUid = firebaseAuth.getCurrentUser().getUid();
        

        createQuestion(content, anonimity, authorUid);
    }

    private void createQuestion(String content, Boolean anonimity, String authorUid) {
        Timestamp ts = Timestamp.now();
        HashMap<String, Object> data = new HashMap<>();
        data.put("author", authorUid);
        data.put("content", content);
        data.put("anonimous", anonimity);
        data.put("creationDate", ts);
        data.put("modificationDate", ts);
        data.put("starCount", 0);
        data.put("stars", new HashMap<>());

        FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseDatabase.collection("questions").add(data);
    }
}
