package users;

import java.util.List;

import util.Entity;

public class User extends Entity {

    String username;
    String userID;
    String password;
    String mail;
    Integer privilege;

    List<Integer> createdAnswers;
    List<Integer> votedAnswers;
    List<Integer> createdQuestions;
    List<Integer> starredQuestions;

    public User(String username, String userID, String password, String mail, Integer privilege) {
        this.username = username;
        this.userID = userID;
        this.password = password;
        this.mail = mail;
        this.privilege = privilege;
    }

    public Boolean create() {
        /// TODO
        return false;
    }
    public Boolean read() {
        /// TODO
        return false;
    }
    public Boolean update() {
        /// TODO
        return false;
    }
    public Boolean delete() {
        /// TODO
        return false;
    }

}
