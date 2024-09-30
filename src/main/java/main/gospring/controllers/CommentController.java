package main.gospring.controllers;

import lombok.RequiredArgsConstructor;
import main.gospring.dto.comment.CommentListResponse;
import main.gospring.dto.comment.CreateCommentRequest;
import main.gospring.dto.comment.CreateCommentResponse;
import main.gospring.dto.comment.UpdateCommentRequest;
import main.gospring.model.Comment;
import main.gospring.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Comment 생성 api
    @PostMapping("/api/comment/{postId}")
    public ResponseEntity<CreateCommentResponse> addComment(@PathVariable Long postId, @RequestBody CreateCommentRequest dto) {

        Comment comment = commentService.save(postId, dto);

        CreateCommentResponse createCommentResponse = CreateCommentResponse.builder()
                .comment(comment).build();

        return ResponseEntity.status(200).body(createCommentResponse);
    }

    // Comments 조회 api
    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<List<CommentListResponse>> getAllComments(@PathVariable Long postId) {

        List<CommentListResponse> commentListResponses = commentService.findCommentsByPost(postId);

        return ResponseEntity.status(200).body(commentListResponses);
    }

    // Comment 삭제 api
    @DeleteMapping("/api/delete-comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {

        commentService.delete(id);

        return ResponseEntity.status(204).build();
    }


    // Comment 수정 api
    @PutMapping("/api/update-comment/{postId}/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody UpdateCommentRequest dto) {

        Comment comment = commentService.update(postId, commentId, dto);

        return ResponseEntity.status(201).body(comment);
    }

}
