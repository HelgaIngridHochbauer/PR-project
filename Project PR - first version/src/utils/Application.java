package utils;

import model.Post;
import java.util.Date;
import java.util.List;
import model.PostScheduler;
import model.GrowthAnalytics;

public class Application {
    private final InputDevice inputDevice;
    private final OutputDevice outputDevice;
    private final PostScheduler postScheduler;
    private final GrowthAnalytics growthAnalytics;
    private int currentFollowers;
    private int previousFollowers;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
        this.postScheduler = new PostScheduler();
        this.growthAnalytics = new GrowthAnalytics();
        this.currentFollowers = 0;
        this.previousFollowers = 0;
    }

    public void run() {
        // Menu to choose between creating a post, viewing the calendar, calculating follower growth, or exiting
        boolean running = true;
        while (running) {
            outputDevice.displayMessage("Choose an option:\n1. Create Post\n2. View Calendar\n3. Calculate Follower Growth\n4. Exit");
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
                    running = false;
                    break;
                default:
                    outputDevice.displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    private void createPost() {
        // Get inputs for Post object
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
        outputDevice.displayMessage("Enter post date (yyyy-mm-dd): ");
        String dateString = inputDevice.readLine();

        // Parse the date
        Date postDate;
        try {
            postDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (java.text.ParseException e) {
            outputDevice.displayMessage("Invalid date format. Post not created.");
            return;
        }

        // Create a Post object using the provided inputs
        Post post = new Post(content, postDate, expectedLikes, currentLikes, previousLikes, currentFollowers, previousFollowers);
        postScheduler.schedulePost(post);

        outputDevice.displayMessage("Post scheduled successfully.");
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
                outputDevice.displayMessage("Post: " + post.getContent() + " on " + post.getDate());
            }
        }
    }

    private void calculateFollowerGrowth() {
        outputDevice.displayMessage("Enter current followers: ");
        currentFollowers = inputDevice.readInt();
        inputDevice.readLine();  // Clear buffer
        outputDevice.displayMessage("Enter previous followers: ");
        previousFollowers = inputDevice.readInt();

        double growth = growthAnalytics.analyzeFollowerGrowth(currentFollowers, previousFollowers); // Adjusted here
        outputDevice.displayMessage("Follower Growth: " + growth + "%");
        inputDevice.readLine(); // Clear buffer for next input if needed
    }

    public void processArguments(String[] args) {
        // Assuming args are provided in sequence
        // [content, expectedLikes, currentLikes, previousLikes, date]
        if (args.length != 5) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }

        String content = args[0];
        int expectedLikes = Integer.parseInt(args[1]);
        int currentLikes = Integer.parseInt(args[2]);
        int previousLikes = Integer.parseInt(args[3]);
        String dateString = args[4];

        Date postDate;
        try {
            postDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (java.text.ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }

        // Create a Post object using the provided inputs
        Post post = new Post(content, postDate, expectedLikes, currentLikes, previousLikes, currentFollowers, previousFollowers);
        postScheduler.schedulePost(post);

        outputDevice.displayMessage("Post scheduled successfully.");
    }
}