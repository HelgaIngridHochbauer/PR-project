package model;

public class Influencer implements Comparable<Influencer> {
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

    @Override
    public int compareTo(Influencer other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        // Custom string representation, adjusted for readability
        return String.format("Name: %s, Platform: %s, Followers: %d", name, platform, followers);
    }
}