package main.gospring.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import main.gospring.model.Post;

@Getter
@NoArgsConstructor
public class PostListResponse {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostListResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor().getUsername();
    }
}
