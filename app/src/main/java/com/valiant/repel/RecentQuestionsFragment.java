package com.valiant.repel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.valiant.repel.models.Question;
import com.valiant.repel.viewholders.QuestionViewHolder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecentQuestionsFragment extends QuestionListFragment {
//    private static final String TAG = "RecentQuestionsFragment";

    public RecentQuestionsFragment() {}

    @Override
    public Query getQuery(FirebaseFirestore databaseReference) {
        Query questionsQuery = databaseReference
                .collection("questions")
                .orderBy("creationDate")
                .limit(100);
        return  questionsQuery;
    }
}
