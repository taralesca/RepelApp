package com.valiant.repel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TopQuestionsFragment extends QuestionListFragment {
//    private static final String TAG = "RecentQuestionsFragment";

    public TopQuestionsFragment() {}

    @Override
    public Query getQuery(FirebaseFirestore databaseReference) {
        Query questionsQuery = databaseReference
                .collection("questions")
                .orderBy("starCount")
                .limit(100);
        return  questionsQuery;
    }
}
