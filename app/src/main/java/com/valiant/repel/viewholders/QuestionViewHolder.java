package com.valiant.repel.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.CheckableImageButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.valiant.repel.R;
import com.valiant.repel.models.Question;

import java.util.HashMap;
import java.util.Map;

public class QuestionViewHolder extends RecyclerView.ViewHolder{
    String username;

    public TextView authorView;
    public TextView contentView;
    public TextView dateView;
    public TextView starCountView;

    public QuestionViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.questionAuthor);
        contentView = itemView.findViewById(R.id.questionContent);
        dateView = itemView.findViewById(R.id.questionDate);
        starCountView = itemView.findViewById(R.id.questionStarCount);
    }

    public void bindToPost(Question question, View.OnClickListener starClickListener) {
        // doareee
        if (question.anonimous) {
            username = "Anonimous";
            authorView.setText(username);
        }
        else {
            FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firebaseDatabase
                    .collection("users")
                    .document(question.author);
            documentReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null) {
                                    username = document.getString("username");
                                    authorView.setText(username);
                                }
                            }
                        }
                    });
        }
//        System.out.println(question.starCount);
//        authorView.setText(username);
        contentView.setText(question.content);
        dateView.setText(question.creationDate.toDate().toString());
        if(question.starCount != null) {
            starCountView.setText(question.starCount.toString());
        }
        ImageView starImage = (ImageView) itemView.findViewById(R.id.star_button);
        starImage.setOnClickListener((View v) -> {
            question.starCount++;
            starCountView.setText(question.starCount.toString());
            FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
            Map<String, Object> mp = new HashMap<>();
            mp.put("starCount", question.starCount);

            if (question.uid != null) {
                firebaseDatabase
                        .collection("questions").document(question.uid)
                        .update(mp);
            } else {
                Log.d("MESAJ", "PROBLEMA");
            }
        });
    }
}
