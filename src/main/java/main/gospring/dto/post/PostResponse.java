package main.gospring.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String author;

    @Builder
    public PostResponse(Long id, String title, String content, String author) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
