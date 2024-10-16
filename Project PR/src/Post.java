import java.util.Date;

public class Post {
    private String content;
    private Date date;
    private int expectedLikes;

    // Constructor
    public Post(String content, Date date, int expectedLikes) {
        this.content = content;
        this.date = date;
        this.expectedLikes = expectedLikes;
    }

    // Getters
    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public int getExpectedLikes() {
        return expectedLikes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", expectedLikes=" + expectedLikes +
                '}';
    }
}