package main.gospring.controllers;

import lombok.RequiredArgsConstructor;
import main.gospring.dto.comment.CreateCommentRequest;
import main.gospring.dto.comment.UpdateCommentRequest;
import main.gospring.model.Comment;
import main.gospring.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Comment 생성 api
    @PostMapping("/api/comment/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody CreateCommentRequest dto) {

        Comment comment = commentService.save(postId, dto);

        return ResponseEntity.status(200).body(comment);
    }

    // Comment 삭제 api
    @DeleteMapping("/api/delete-comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {

        commentService.delete(id);

        return ResponseEntity.status(204).build();
    }


    // Comment 수정 api
    @PutMapping("/api/update-comment/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest dto) {

        Comment comment = commentService.update(id, dto);

        return ResponseEntity.status(201).body(comment);
    }

}
