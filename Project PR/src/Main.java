// Main.java
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Sample data
        Influencer influencer = new Influencer("Alice", "Instagram", 1000000);
        Post[] posts = {
                new Post("First post", new Date(), 10000),
                new Post("Second post", new Date(), 15000),
                new Post("Third post", new Date(), 20000)
        };
        Campaign campaign = new Campaign(influencer, posts);

        // Using program arguments
        if (args.length > 0) {
            switch (args[0]) {
                case "followerGrowth":
                    if (args.length > 2) {
                        int initialFollowers = Integer.parseInt(args[1]);
                        int currentFollowers = Integer.parseInt(args[2]);
                        double growth = CampaignFunctions.analyzeFollowerGrowth(initialFollowers, currentFollowers);
                        System.out.println("Follower Growth: " + growth + "%");
                    } else {
                        System.out.println("Please provide initial and current followers: followerGrowth <initial> <current>");
                    }
                    break;
                case "campaignEngagement":
                    int totalLikes = CampaignFunctions.analyzeCampaignEngagement(campaign);
                    System.out.println("Total Campaign Engagement: " + totalLikes);
                    break;
                case "postEngagement":
                    if (args.length > 1) {
                        int postIndex = Integer.parseInt(args[1]);
                        if (postIndex >= 0 && postIndex < posts.length) {
                            int postLikes = CampaignFunctions.analyzePostEngagement(posts[postIndex]);
                            System.out.println("Post Engagement: " + postLikes);
                        } else {
                            System.out.println("Invalid post index");
                        }
                    } else {
                        System.out.println("Please provide a post index: postEngagement <index>");
                    }
                    break;
                default:
                    System.out.println("Invalid argument!");
            }
        } else {
            System.out.println("Please provide a function to call: followerGrowth, campaignEngagement, or postEngagement");
        }
    }
}