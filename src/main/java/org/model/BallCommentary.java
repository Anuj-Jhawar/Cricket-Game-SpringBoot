package org.model;

public class BallCommentary {
    int batsmanId;
    int bowlerId;
    String commentaryText;
    public BallCommentary(int batsmanID,int bowlerId,String commentaryText){
        this.batsmanId = batsmanID;
        this.bowlerId = bowlerId;
        this.commentaryText = commentaryText;
    }
}
