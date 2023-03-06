package org.project.model;

import lombok.Data;

@Data
public class BallCommentary {

    private int batsmanId;
    private int bowlerId;
    private String commentaryText;

    public BallCommentary(int batsmanID, int bowlerId, String commentaryText) {
        this.batsmanId = batsmanID;
        this.bowlerId = bowlerId;
        this.commentaryText = commentaryText;
    }
}
