package com.valiant.repel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RecentQuestionsFragment extends QuestionListFragment {
//    private static final String TAG = "RecentQuestionsFragment";

    public RecentQuestionsFragment() {}

    @Override
    public Query getQuery(FirebaseFirestore databaseReference) {
        Query questionsQuery = databaseReference
                .collection("DashboardFragment")
                .orderBy("creationDate")
                .limit(100);
        return  questionsQuery;
    }
}
