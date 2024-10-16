// Campaign.java
public class Campaign {
    private Influencer influencer;
    private Post[] posts;

    // Constructor
    public Campaign(Influencer influencer, Post[] posts) {
        this.influencer = influencer;
        this.posts = posts;
    }

    // Getters
    public Influencer getInfluencer() {
        return influencer;
    }

    public Post[] getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        StringBuilder postsStr = new StringBuilder();
        for (Post post : posts) {
            postsStr.append(post.toString()).append("\n");
        }
        return "Campaign{" +
                "influencer=" + influencer +
                ", posts=\n" + postsStr +
                '}';
    }
}