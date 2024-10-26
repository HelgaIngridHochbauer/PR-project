package model;

public class Influencer {
    private String name;
    private String platform;
    private int followers;

    public Influencer(String name, String platform, int followers) {
        this.name = name;
        this.platform = platform;
        this.followers = followers;
    }
    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public int getFollowers() {
        return followers;
    }
    // Getters and toString() method
}