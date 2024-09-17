package main.gospring.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.gospring.model.Post;
import main.gospring.model.User;

@Getter
@NoArgsConstructor
public class PostListResponse {

    private String title;
    private String content;
    private User author;

    public PostListResponse(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
    }
}
