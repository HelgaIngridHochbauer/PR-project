package utils;

import model.Influencer;
import model.Post;
import model.GrowthAnalytics;
import model.PostScheduler;

import java.util.*;
import java.util.stream.Collectors;

public class Application {
    private final InputDevice inputDevice;
    private final OutputDevice outputDevice;
    private final PostScheduler postScheduler;
    private final GrowthAnalytics growthAnalytics;
    private int currentFollowers;
    private int previousFollowers;
    private final List<Influencer> influencers;
    private final Map<String, List<Post>> campaigns; // Map of campaign name to list of posts
    private Comparator<Influencer> currentComparator;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
        this.postScheduler = new PostScheduler();
        this.growthAnalytics = new GrowthAnalytics(0, 0, 0, 0); // Initialized with default values
        this.currentFollowers = 0;
        this.previousFollowers = 0;
        this.influencers = new ArrayList<>(); // Initialize influencers list
        this.campaigns = new HashMap<>(); // Initialize campaigns map
        this.currentComparator = InfluencerComparators.BY_NAME; // Default sorting by name
    }

    public void processArguments(String[] args) {
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "createpost":
                    createPost();
                    break;
                case "viewcalendar":
                    viewCalendar();
                    break;
                case "calculatefollowergrowth":
                    calculateFollowerGrowth();
                    break;
                case "compareengagement":
                    compareEngagement();
                    break;
                case "comparefollowergrowth":
                    compareFollowerGrowth();
                    break;
                case "addinfluencer":
                    addInfluencer();
                    break;
                case "listinfluencers":
                    listInfluencers();
                    break;
                case "setsortcriterion":
                    setSortCriterion();
                    break;
                case "groupinfluencersbyplatform":
                    groupInfluencersByPlatform();
                    break;
                case "groupandsortinfluencers":
                    groupAndSortInfluencers();
                    break;
                case "groupinfluencersbycelebritylevel":
                    groupInfluencersByCelebrityLevel();
                    break;
                case "grouppostsbycampaign":
                    groupPostsByCampaign();
                    break;
                default:
                    outputDevice.displayMessage("Unknown command line argument: " + arg);
            }
        }
    }

    public void run() {
        boolean running = true;
        while (running) {
            outputDevice.displayMessage("Choose an option:\n" +
                    "1. Create Post\n" +
                    "2. View Calendar\n" +
                    "3. Calculate Follower Growth\n" +
                    "4. Compare Engagement\n" +
                    "5. Compare Follower Growth\n" +
                    "6. Add Influencer\n" +
                    "7. List Influencers\n" +
                    "8. Set Sort Criterion\n" +
                    "9. Group Influencers by Platform\n" +
                    "10. Group and Sort Influencers\n" +
                    "11. Group Influencers by Celebrity Level\n" +
                    "12. Group Posts by Campaign\n" +
                    "13. Exit");
            int choice = inputDevice.readInt();
            inputDevice.readLine();  // Clear buffer

            switch (choice) {
                case 1:
                    createPost();
                    break;
                case 2:
                    viewCalendar();
                    break;
                case 3:
                    calculateFollowerGrowth();
                    break;
                case 4:
                    compareEngagement();
                    break;
                case 5:
                    compareFollowerGrowth();
                    break;
                case 6:
                    addInfluencer();
                    break;
                case 7:
                    listInfluencers();
                    break;
                case 8:
                    setSortCriterion();
                    break;
                case 9:
                    groupInfluencersByPlatform();
                    break;
                case 10:
                    groupAndSortInfluencers();
                    break;
                case 11:
                    groupInfluencersByCelebrityLevel();
                    break;
                case 12:
                    groupPostsByCampaign();
                    break;
                case 13:
                    running = false;
                    break;
                default:
                    outputDevice.displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    private void createPost() {
        if (influencers.isEmpty()) {
            outputDevice.displayMessage("No influencers available. Please add an influencer first.");
            return;
        }

        // List available influencers
        outputDevice.displayMessage("Select an influencer by entering the corresponding number:");
        for (int i = 0; i < influencers.size(); i++) {
            Influencer influencer = influencers.get(i);
            outputDevice.displayMessage((i + 1) + ". " + influencer.getName() + " (" + influencer.getPlatform() + ")");
        }

        int influencerIndex = inputDevice.readInt() - 1;
        inputDevice.readLine();  // Clear buffer

        if (influencerIndex < 0 || influencerIndex >= influencers.size()) {
            outputDevice.displayMessage("Invalid selection. Post not created.");
            return;
        }

        Influencer selectedInfluencer = influencers.get(influencerIndex);

        outputDevice.displayMessage("Enter content of the post: ");
        String content = inputDevice.readLine();
        outputDevice.displayMessage("Enter expected likes: ");
        int expectedLikes = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter current likes: ");
        int currentLikes = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter previous likes: ");
        int previousLikes = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter number of comments: ");
        int comments = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter post date (yyyy-mm-dd): ");
        String dateString = inputDevice.readLine();

        outputDevice.displayMessage("Enter campaign name: ");
        String campaignName = inputDevice.readLine();

        // Parse the date
        Date postDate;
        try {
            postDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (java.text.ParseException e) {
            outputDevice.displayMessage("Invalid date format. Post not created.");
            return;
        }

        // Create a Post object using the provided inputs and associate it with the selected influencer
        Post post = new Post(content, postDate, expectedLikes, currentLikes, previousLikes, comments, currentFollowers, previousFollowers, campaignName);
        post.setInfluencer(selectedInfluencer);
        postScheduler.schedulePost(post);

        campaigns.computeIfAbsent(campaignName, k -> new ArrayList<>()).add(post); // Add post to the campaign

        outputDevice.displayMessage("Post scheduled successfully.");
    }

    private void groupPostsByCampaign() {
        outputDevice.displayMessage("Posts grouped by campaign:");
        for (Map.Entry<String, List<Post>> entry : campaigns.entrySet()) {
            outputDevice.displayMessage("Campaign: " + entry.getKey());
            for (Post post : entry.getValue()) {
                outputDevice.displayMessage(
                        String.format("  Post: %s by %s on %s",
                                post.getContent(),
                                post.getInfluencer().getName(),
                                post.getDate()
                        )
                );
            }
        }
    }

    private void viewCalendar() {
        outputDevice.displayMessage("Enter month and year (e.g., 10 2024 for October 2024): ");
        int month = inputDevice.readInt();
        int year = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        List<Post> posts = postScheduler.getPostsByMonth(month, year);
        if (posts.isEmpty()) {
            outputDevice.displayMessage("No posts scheduled for this month.");
        } else {
            for (Post post : posts) {
                outputDevice.displayMessage("Post: " + post.getContent() + " by " + post.getInfluencer().getName() + " on " + post.getDate());
            }
        }
    }

    private void calculateFollowerGrowth() {
        outputDevice.displayMessage("Enter current followers: ");
        currentFollowers = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter previous followers: ");
        previousFollowers = inputDevice.readInt();

        double growth = growthAnalytics.analyzeFollowerGrowth(currentFollowers, previousFollowers);
        outputDevice.displayMessage("Follower Growth: " + growth + "%");
    }

    private void compareEngagement() {
        outputDevice.displayMessage("Enter current likes for GA1: ");
        int currentLikes1 = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter comments for GA1: ");
        int comments1 = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter current likes for GA2: ");
        int currentLikes2 = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter comments for GA2: ");
        int comments2 = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        GrowthAnalytics ga1 = new GrowthAnalytics(currentLikes1, comments1, 0, 0);
        GrowthAnalytics ga2 = new GrowthAnalytics(currentLikes2, comments2, 0, 0);

        int comparisonResult = ga1.compareTo(ga2);

        if (comparisonResult < 0) {
            outputDevice.displayMessage("GA1 has less engagement than GA2.");
        } else if (comparisonResult > 0) {
            outputDevice.displayMessage("GA1 has more engagement than GA2.");
        } else {
            outputDevice.displayMessage("GA1 and GA2 have the same engagement.");
        }
    }

    private void compareFollowerGrowth() {
        outputDevice.displayMessage("Enter current followers for GA1: ");
        int currentFollowers1 = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter previous followers for GA1: ");
        int previousFollowers1 = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter current followers for GA2: ");
        int currentFollowers2 = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        outputDevice.displayMessage("Enter previous followers for GA2: ");
        int previousFollowers2 = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        GrowthAnalytics ga1 = new GrowthAnalytics(0, 0, currentFollowers1, previousFollowers1);
        GrowthAnalytics ga2 = new GrowthAnalytics(0, 0, currentFollowers2, previousFollowers2);

        int comparisonResult = ga1.compareTo(ga2);

        if (comparisonResult < 0) {
            outputDevice.displayMessage("GA1 has less follower growth than GA2.");
        } else if (comparisonResult > 0) {
            outputDevice.displayMessage("GA1 has more follower growth than GA2.");
        } else {
            outputDevice.displayMessage("GA1 and GA2 have the same follower growth.");
        }
    }

    private void addInfluencer() {
        outputDevice.displayMessage("Enter the name of the influencer: ");
        String name = inputDevice.readLine();

        outputDevice.displayMessage("Enter the platform: ");
        String platform = inputDevice.readLine();

        outputDevice.displayMessage("Enter the number of followers: ");
        int followers = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        Influencer influencer = new Influencer(name, platform, followers);
        influencers.add(influencer);

        outputDevice.displayMessage("Influencer added successfully.");
    }

    private void listInfluencers() {
        Collections.sort(influencers, currentComparator);

        if (influencers.isEmpty()) {
            outputDevice.displayMessage("No influencers to display.");
        } else {
            outputDevice.displayMessage("Influencers:");
            for (Influencer influencer : influencers) {
                outputDevice.displayMessage(
                        String.format("Name: %s, Platform: %s, Followers: %d",
                                influencer.getName(),
                                influencer.getPlatform().isEmpty() ? "N/A" : influencer.getPlatform(),
                                influencer.getFollowers()
                        )
                );
            }
        }
    }

    private void setSortCriterion() {
        outputDevice.displayMessage("Choose a sort criterion:\n" +
                "1. Sort by Name\n" +
                "2. Sort Descendingly by Number of Followers\n" +
                "3. Sort Ascendingly by Number of Followers\n");
        int choice = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer

        switch (choice) {
            case 1:
                currentComparator = InfluencerComparators.BY_NAME;
                break;
            case 2:
                currentComparator = InfluencerComparators.BY_FOLLOWERS_DESCENDING;
                break;
            case 3:
                currentComparator = InfluencerComparators.BY_FOLLOWERS_ASCENDING;
                break;
            default:
                outputDevice.displayMessage("Invalid choice. Keeping previous sort criterion.");
        }

        outputDevice.displayMessage("Sort criterion updated successfully.");
    }

    private void groupInfluencersByPlatform() {
        Map<String, List<Influencer>> groupedByPlatform = influencers.stream()
                .collect(Collectors.groupingBy(Influencer::getPlatform));

        outputDevice.displayMessage("Influencers grouped by platform:");
        for (Map.Entry<String, List<Influencer>> entry : groupedByPlatform.entrySet()) {
            outputDevice.displayMessage("Platform: " + (entry.getKey().isEmpty() ? "N/A" : entry.getKey()));
            for (Influencer influencer : entry.getValue()) {
                outputDevice.displayMessage(
                        String.format("  Name: %s, Followers: %d",
                                influencer.getName(),
                                influencer.getFollowers()
                        )
                );
            }
        }
    }

    private void groupAndSortInfluencers() {
        // Group by platform first
        Map<String, List<Influencer>> groupedByPlatform = influencers.stream()
                .collect(Collectors.groupingBy(Influencer::getPlatform));

        outputDevice.displayMessage("Influencers grouped and sorted by platform (ascending followers):");

        for (Map.Entry<String, List<Influencer>> entry : groupedByPlatform.entrySet()) {
            List<Influencer> sortedList = entry.getValue().stream()
                    .sorted(InfluencerComparators.BY_FOLLOWERS_ASCENDING)
                    .collect(Collectors.toList());

            outputDevice.displayMessage("Platform: " + (entry.getKey().isEmpty() ? "N/A" : entry.getKey()));
            for (Influencer influencer : sortedList) {
                outputDevice.displayMessage(
                        String.format("  Name: %s, Followers: %d",
                                influencer.getName(),
                                influencer.getFollowers()
                        )
                );
            }
        }
    }

    private void groupInfluencersByCelebrityLevel() {
        Map<String, List<Influencer>> celebrityLevels = new HashMap<>();
        celebrityLevels.put("A-list", new ArrayList<>());
        celebrityLevels.put("B-list", new ArrayList<>());
        celebrityLevels.put("C-list", new ArrayList<>());

        for (Influencer influencer : influencers) {
            if (influencer.getFollowers() > 100000) {
                celebrityLevels.get("A-list").add(influencer);
            } else if (influencer.getFollowers() > 10000) {
                celebrityLevels.get("B-list").add(influencer);
            } else {
                celebrityLevels.get("C-list").add(influencer);
            }
        }

        // Use a predetermined order to display the groups
        List<String> order = Arrays.asList("A-list", "B-list", "C-list");

        outputDevice.displayMessage("Influencers grouped by celebrity level:");

        for (String key : order) {
            outputDevice.displayMessage(key + ":");
            for (Influencer influencer : celebrityLevels.get(key)) {
                outputDevice.displayMessage(
                        String.format("  Name: %s, Platform: %s, Followers: %d",
                                influencer.getName(),
                                influencer.getPlatform().isEmpty() ? "N/A" : influencer.getPlatform(),
                                influencer.getFollowers()
                        )
                );
            }
        }
    }
}