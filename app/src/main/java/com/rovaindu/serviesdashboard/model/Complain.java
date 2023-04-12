package com.rovaindu.serviesdashboard.model;

import java.io.Serializable;
import java.util.List;

public class Complain implements Serializable {


    private int id;
    private int userID;
    private String title;
    private String content;
    private long created_at;
    private List<ComplainComments> complainComments;
    private int likes;
    private int comments;


    public Complain()
    {

    }

    public Complain(int id, int userID, String title, String content, long created_at, List<ComplainComments> complainComments, int likes, int comments) {
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.complainComments = complainComments;
        this.likes = likes;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public List<ComplainComments> getComplainComments() {
        return complainComments;
    }

    public void setComplainComments(List<ComplainComments> complainComments) {
        this.complainComments = complainComments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}