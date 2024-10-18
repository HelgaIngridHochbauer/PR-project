package model;

public class Influencer {
    private String name;
    private String platform;
    private int followers;

    // Constructor
    public Influencer(String name, String platform, int followers) {
        this.name = name;
        this.platform = platform;
        this.followers = followers;
    }

    // Getters
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
    public String toString() {
        return "model.Influencer{" +
                "name='" + name + '\'' +
                ", platform='" + platform + '\'' +
                ", followers=" + followers +
                '}';
    }
}