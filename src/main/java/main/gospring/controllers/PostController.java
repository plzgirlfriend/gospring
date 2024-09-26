package main.gospring.controllers;

import lombok.RequiredArgsConstructor;
import main.gospring.dto.post.CreatePostRequest;
import main.gospring.dto.post.PostListResponse;
import main.gospring.dto.post.PostResponse;
import main.gospring.dto.post.UpdatePostRequest;
import main.gospring.model.Post;
import main.gospring.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;

//    @GetMapping("/api/current-user")
//    public ResponseEntity<CurrentUserResponse> getCurrentUser() {
//
//        User currentUser = postService.getAuthenticatedUser();
//
//        CurrentUserResponse currentUserResponse = CurrentUserResponse.builder()
//                .username(currentUser.getUsername())
//                .build();
//
//        return ResponseEntity.status(200).body(currentUserResponse);
//    }
    
    // Post 생성 api
    @PostMapping("/api/post")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest dto) {

        Post post = postService.save(dto);

        return ResponseEntity.status(201).body(post);
    }

    // Post 조회 api
    @GetMapping("/api/posts")
    public ResponseEntity<List<PostListResponse>> getAllPosts() {

        List<PostListResponse> postListResponse = postService.lookUpAllPost()
                .stream()
                .map(PostListResponse::new)
                .toList();

        return ResponseEntity.status(201).body(postListResponse);
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {

        Post post = postService.lookUpPost(id);

        // User author의 Lazy Loading이기 때문에 dto로 전달해보자
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getUsername())
                .build();

        return ResponseEntity.status(201).body(postResponse);
    }

    // Post 삭제 api
    @DeleteMapping("/api/delete-post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {

        postService.delete(id);

        return ResponseEntity.status(204).build();
    }

    // Post 수정 api
    @PutMapping("/api/update-post/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest dto) {

        Post post = postService.update(id, dto);

        return ResponseEntity.status(200).body(post);
    }
}
