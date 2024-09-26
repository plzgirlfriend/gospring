package main.gospring.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Table(name = "goSpring_comment")
@Entity
@Getter
@RequiredArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    // 근데 이게 뭐지? ManyToOne은 알겠는데 나중에 test해보자
    // Post에서는 몰랐는데 join하니까 import에 선언 안 해줘도 상관 없는 건가?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_author", nullable = false)
    private User author;

    // 어떤 post에 달리는지도 알아야함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    @Builder
    public Comment(String content, User author, Post post) {

        this.content = content;
        this.author = author;
        this.post = post;
    }

    // update
    public void update(String content) {
        this.content = content;
    }
}
