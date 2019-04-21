package com.valiant.repel.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Question {

    public String uid;
    public String author;
    public String content;
    public Boolean anonimous;
    public Timestamp creationDate;
    public Timestamp modificationDate;

    public Question() {}

    public Question(String uid, String author, String content, Boolean anonimous, Timestamp creationDate, Timestamp modificationDate) {
        this.uid = uid;
        this.author = author;
        this.content = content;
        this.anonimous = anonimous;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
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

        return result;
    }

}
