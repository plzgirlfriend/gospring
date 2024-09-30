package main.gospring.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    // Post, Comment relationship
    // comment가 남아있을 때 post를 삭제하면 같이 comment도 같이 삭제
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

//    @OneToMany
//    @JoinColumn()
//    private Comment comment;

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
