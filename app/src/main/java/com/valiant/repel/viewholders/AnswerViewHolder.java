package com.valiant.repel.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.valiant.repel.R;
import com.valiant.repel.models.Answer;

import java.util.HashMap;
import java.util.Map;

public class AnswerViewHolder extends RecyclerView.ViewHolder{
    String username;

    public TextView authorView;
    public TextView contentView;
    public TextView dateView;
    public TextView voteCountView;

    public AnswerViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.answerAuthor);
        contentView = itemView.findViewById(R.id.answerContent);
        dateView = itemView.findViewById(R.id.answerDate);
        voteCountView = itemView.findViewById(R.id.answerVoteCount);
    }

    public void bindToPost(Answer answer, View.OnClickListener voteClickListener) {
        // doareee
        if (answer.anonimous) {
            username = "Anonimous";
            authorView.setText(username);
        }
        else {
            FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firebaseDatabase
                    .collection("users")
                    .document(answer.author);
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
//        System.out.println(answer.voteCount);
//        authorView.setText(username);
        contentView.setText(answer.content);
        dateView.setText(answer.creationDate.toDate().toString());
        if(answer.voteCount != null) {
            voteCountView.setText(answer.voteCount.toString());
        }
        ImageView voteImage = (ImageView) itemView.findViewById(R.id.vote_button);
        voteImage.setOnClickListener((View v) -> {
            answer.voteCount++;
            voteCountView.setText(answer.voteCount.toString());
            FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
            Map<String, Object> mp = new HashMap<>();
            mp.put("voteCount", answer.voteCount);

            if (answer.uid != null) {
                firebaseDatabase
                        .collection("questions/" + answer.questionUID + "/answers").document(answer.uid)
                        .update(mp);
            } else {
                Log.d("MESAJ", "PROBLEMA");
            }
        });
    }
}


