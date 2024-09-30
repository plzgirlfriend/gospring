package main.gospring.dto.comment;

import lombok.Getter;
import main.gospring.model.Comment;

@Getter
public class CommentListResponse {

    private Long id;
    private String author;
    private String content;

    public CommentListResponse(Comment comment) {

        this.id = comment.getId();
        this.author = comment.getAuthor().getUsername();
        this.content = comment.getContent();
    }
}
