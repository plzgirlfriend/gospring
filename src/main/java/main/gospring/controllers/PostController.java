package main.gospring.controllers;

import lombok.RequiredArgsConstructor;
import main.gospring.dto.post.CreatePostRequest;
import main.gospring.dto.post.PostListResponse;
import main.gospring.dto.post.UpdatePostRequest;
import main.gospring.model.Post;
import main.gospring.services.PostService;
import main.gospring.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    private final UserService userService;
    
    // Post 생성 
    @PostMapping("/api/post")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest dto) {

        Post post = postService.save(dto);

        return ResponseEntity.status(201).body(post);
    }

    // Post 조회 
    @GetMapping("/api/posts")
    public ResponseEntity<List<PostListResponse>> getAllPosts() {

        List<PostListResponse> postListResponse = postService.lookUpAllPost()
                .stream()
                .map(PostListResponse::new)
                .toList();

        return ResponseEntity.status(201).body(postListResponse);
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {

        Post post = postService.lookUpPost(id);

        return ResponseEntity.status(201).body(post);
    }

    // Post 삭제
    @DeleteMapping("/api/delete-post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {

        postService.delete(id);

        return ResponseEntity.status(204).build();
    }

    // Post 수정
    @PutMapping("/api/update-post/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest dto) {

        Post post = postService.update(id, dto);

        return ResponseEntity.status(200).body(post);
    }
}
