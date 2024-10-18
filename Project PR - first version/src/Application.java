import model.*;
import java.util.Date;

public class Application {
    private InputDevice inputDevice;
    private OutputDevice outputDevice;
    private Influencer influencer;
    private Post[] posts;
    private Campaign campaign;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample data
        influencer = new Influencer("Alice", "Instagram", 1000000);
        posts = new Post[]{
                new Post("First post", new Date(), 10000),
                new Post("Second post", new Date(), 15000),
                new Post("Third post", new Date(), 20000)
        };
        campaign = new Campaign(influencer, posts);
    }

    public void run() {
        String[] args = inputDevice.getArguments();
        processArguments(args);
    }

    public void processArguments(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "followerGrowth":
                    handleFollowerGrowth(args);
                    break;
                case "campaignEngagement":
                    handleCampaignEngagement();
                    break;
                case "postEngagement":
                    handlePostEngagement(args);
                    break;
                default:
                    outputDevice.displayMessage("Invalid argument!");
            }
        } else {
            outputDevice.displayMessage("Please provide a function to call: followerGrowth, campaignEngagement, or postEngagement");
        }
    }

    private void handleFollowerGrowth(String[] args) {
        if (args.length > 2) {
            int initialFollowers = Integer.parseInt(args[1]);
            int currentFollowers = Integer.parseInt(args[2]);
            double growth = CampaignFunctions.analyzeFollowerGrowth(initialFollowers, currentFollowers);
            outputDevice.displayMessage("Follower Growth: " + growth + "%");
        } else {
            outputDevice.displayMessage("Please provide initial and current followers: followerGrowth <initial> <current>");
        }
    }

    private void handleCampaignEngagement() {
        int totalLikes = CampaignFunctions.analyzeCampaignEngagement(campaign);
        outputDevice.displayMessage("Total Campaign Engagement: " + totalLikes);
    }

    private void handlePostEngagement(String[] args) {
        if (args.length > 1) {
            int postIndex = Integer.parseInt(args[1]);
            if (postIndex >= 0 && postIndex < posts.length) {
                int postLikes = CampaignFunctions.analyzePostEngagement(posts[postIndex]);
                outputDevice.displayMessage("Post Engagement: " + postLikes);
            } else {
                outputDevice.displayMessage("Invalid post index");
            }
        } else {
            outputDevice.displayMessage("Please provide a post index: postEngagement <index>");
        }
    }
}