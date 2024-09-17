package main.gospring.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Table(name = "goSpring_post")
@Entity
@Getter
@RequiredArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    // username in User table
    // authenticated() user가 Post를 다룸
    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(name = "post_author", nullable = false)
    @JoinColumn(name = "post_author", nullable = false)
    private User author;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_content", nullable = false)
    private String content;

    @Builder
    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // update에 사용
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
