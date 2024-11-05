package model;

import java.util.Date;

public class Post {
    private String content;
    private Date date;
    private int expectedLikes;
    private int currentLikes;
    private int previousLikes;
    private int comments;
    private int currentFollowers;
    private int previousFollowers;
    private String campaignName;
    private Influencer influencer;

    // Constructor
    public Post(String content, Date date, int expectedLikes, int currentLikes, int previousLikes, int comments,
                int currentFollowers, int previousFollowers, String campaignName) {
        this.content = content;
        this.date = date;
        this.expectedLikes = expectedLikes;
        this.currentLikes = currentLikes;
        this.previousLikes = previousLikes;
        this.comments = comments;
        this.currentFollowers = currentFollowers;
        this.previousFollowers = previousFollowers;
        this.campaignName = campaignName;
    }

    // Getters and setters for all fields
    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public int getExpectedLikes() {
        return expectedLikes;
    }

    public int getCurrentLikes() {
        return currentLikes;
    }

    public int getPreviousLikes() {
        return previousLikes;
    }

    public int getComments() {
        return comments;
    }

    public int getCurrentFollowers() {
        return currentFollowers;
    }

    public int getPreviousFollowers() {
        return previousFollowers;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public Influencer getInfluencer() {
        return influencer;
    }

    public void setInfluencer(Influencer influencer) {
        this.influencer = influencer;
    }
}