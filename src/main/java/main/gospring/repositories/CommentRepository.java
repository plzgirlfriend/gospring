package main.gospring.repositories;

import main.gospring.model.Comment;
import main.gospring.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

        // Post에 해당하는 모든 댓글을 조회
        List<Comment> findByPost(Post post);

        // Post의 특정 id Comment 찾기
        Optional<Comment> findByPostAndId(Post post, Long id);
}
