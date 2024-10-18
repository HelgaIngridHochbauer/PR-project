package model;

public class CampaignFunctions {

    public static double analyzeFollowerGrowth(int initialFollowers, int currentFollowers) {
        if (initialFollowers == 0) {
            return 0.0; // Avoid division by zero
        }
        return ((double) (currentFollowers - initialFollowers) / initialFollowers) * 100;
    }

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