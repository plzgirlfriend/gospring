package main.gospring.dto.comment;

import lombok.Builder;
import lombok.Getter;
import main.gospring.model.Comment;

@Getter
public class CreateCommentResponse {

    private Long id;
    private Long postId;
    private String content;
    private String author;

    @Builder
    public CreateCommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor().getUsername();
    }
}
