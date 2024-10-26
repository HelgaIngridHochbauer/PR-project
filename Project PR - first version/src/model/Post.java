package model;

import java.util.Date;

public class Post implements Analyticable {
    private String content;
    private Date date;
    private int expectedLikes;
    private int currentLikes;
    private int previousLikes;
    private int currentFollowers;
    private int previousFollowers;

    public Post(String content, Date date, int expectedLikes, int currentLikes, int previousLikes, int currentFollowers, int previousFollowers) {
        this.content = content;
        this.date = date;
        this.expectedLikes = expectedLikes;
        this.currentLikes = currentLikes;
        this.previousLikes = previousLikes;
        this.currentFollowers = currentFollowers;
        this.previousFollowers = previousFollowers;
    }

    // Getters
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

    public int getCurrentFollowers() {
        return currentFollowers;
    }

    public int getPreviousFollowers() {
        return previousFollowers;
    }

    @Override
    public void analyze() {
        // Placeholder for the analytics method
        System.out.println("Analyzing engagement for post: " + content);
    }

    @Override
    public double calculateEngagementGrowth(int currentLikes, int previousLikes) {
        if (previousLikes == 0) {
            return 100.0;
        }
        return ((double) (currentLikes - previousLikes) / previousLikes) * 100;
    }

    @Override
    public double analyzeFollowerGrowth(int currentFollowers, int previousFollowers) {
        if (previousFollowers == 0) {
            return 100.0;
        }
        return ((double) (currentFollowers - previousFollowers) / previousFollowers) * 100;
    }
}