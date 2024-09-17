package main.gospring.repositories;

import main.gospring.dto.post.CreatePostRequest;
import main.gospring.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Post 생성

    // Post 조회

    // Post 삭제

    // Post 수정
}
