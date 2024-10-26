package model;

public class CampaignFunctions {

    public static int analyzeCampaignEngagement(Campaign campaign) {
        int totalLikes = 0;
        for (Post post : campaign.getPosts()) {
            totalLikes += post.getExpectedLikes();
        }
        return totalLikes;
    }

    public static int analyzePostEngagement(Post post) {
        return post.getExpectedLikes();
    }
}