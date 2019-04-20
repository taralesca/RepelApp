package com.valiant.repel.viewholders;

import android.view.View;
import android.widget.TextView;

import com.valiant.repel.R;
import com.valiant.repel.models.Question;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionViewHolder extends RecyclerView.ViewHolder{

    public TextView authorView;
    public TextView contentView;
    public TextView dateView;

    public QuestionViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.questionAuthor);
        contentView = itemView.findViewById(R.id.questionContent);
        dateView = itemView.findViewById(R.id.questionDate);
    }

    public void bindToPost(Question question, View.OnClickListener starClickListener) {
        authorView.setText(question.author);
        contentView.setText(question.content);
        dateView.setText(question.creationDate.toDate().toString());
    }
}
