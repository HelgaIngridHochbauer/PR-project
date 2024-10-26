package model;

import model.Post;
import java.util.Date;
import java.util.List;

public interface Schedulable {
    void schedulePost(Post post);
    List<Post> getPostsByMonth(int month, int year);
}