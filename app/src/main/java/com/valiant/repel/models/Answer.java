package com.valiant.repel.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Answer {
    public String uid;
    public String author;
    public String content;
    public Boolean anonimous;
    public Timestamp creationDate;
    public Timestamp modificationDate;
    public Integer voteCount;
    public HashMap<String, Object> votes;
    public String questionUID;

    public Answer() {}

    public Answer(String uid, String author, String content, Boolean anonimous, Timestamp creationDate, Timestamp modificationDate, Integer voteCount, HashMap<String, Object> votes, String questionUID) {
        this.uid = uid;
        this.author = author;
        this.content = content;
        this.anonimous = anonimous;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.voteCount = voteCount;
        this.votes = votes;
        this.questionUID = questionUID;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("content", content);
        result.put("anonimous", anonimous);
        result.put("creationDate", creationDate);
        result.put("modificationDate", modificationDate);
        result.put("voteCount", voteCount);
        result.put("votes", votes);

        return result;
    }

}
