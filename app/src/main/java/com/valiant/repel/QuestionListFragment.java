package com.valiant.repel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.valiant.repel.models.Question;
import com.valiant.repel.viewholders.QuestionViewHolder;

public abstract class QuestionListFragment extends Fragment {
    private static final String TAG = "QuestionListFragment";

    private FirebaseFirestore database;

    private FirestoreRecyclerAdapter<Question, QuestionViewHolder> adapter;
    private RecyclerView recycler;
    private LinearLayoutManager manager;

    public QuestionListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_questions, container, false);

        database = FirebaseFirestore.getInstance();

        recycler = rootView.findViewById(R.id.questions_view);
        recycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        manager = new LinearLayoutManager(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recycler.setLayoutManager(manager);

        // Set up FirebaseRecyclerAdapter with the Query
        /*
        Query questionsQuery = database
                .collection("DashboardFragment")
                .orderBy("creationDate");
        */
        final Query questionsQuery = getQuery(database);

        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(questionsQuery, Question.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Question, QuestionViewHolder>(options) {
            @Override
            public QuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new QuestionViewHolder(inflater.inflate(R.layout.item_question, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull QuestionViewHolder viewHolder, int position, @NonNull Question model) {
//                final FirebaseFirestore questionRef = database.collection("DashboardFragment").get(position);

                // Set click listener for the whole post view
                final String postKey = getSnapshots().getSnapshot(position).getReference().getId();
                System.out.println(postKey);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), QuestionDetailActivity.class);
                        intent.putExtra(QuestionDetailActivity.EXTRA_QUESTION_KEY, postKey);
                        startActivity(intent);
                    }
                });

                /*// Determine if the current user has liked this post and set UI accordingly
                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }*/

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        /*// Need to write to both places the post is stored
                        DatabaseReference globalPostRef = database.child("posts").child(postRef.getKey());
                        DatabaseReference userPostRef = database.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                        onStarClicked(globalPostRef);
                        onStarClicked(userPostRef);*/
                    }
                });
            }
        };
        recycler.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(FirebaseFirestore databaseReference);
}
