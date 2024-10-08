package main.gospring.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.gospring.dto.comment.CommentListResponse;
import main.gospring.dto.comment.CreateCommentRequest;
import main.gospring.dto.comment.UpdateCommentRequest;
import main.gospring.model.Comment;
import main.gospring.model.Post;
import main.gospring.model.User;
import main.gospring.repositories.CommentRepository;
import main.gospring.repositories.PostRepository;
import main.gospring.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public User getAuthenticatedUser() {

        // 현재 인증된 사용자 정보 가져오기(반복해서 쓸 거 같아 여기 구현)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("인증된 유저가 아닙니다."));
    }

    // Comment 생성
    // content 입력 -> id(자동 생성), content, author 전달
    public Comment save(Long postId, CreateCommentRequest dto){

        // 현재 로그인한 사용자
        User author = getAuthenticatedUser();


        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾지 못했습니다."));


        Comment comment = Comment.builder()
                .author(author)
                .content(dto.getContent())
                .post(post)
                .build();

        return commentRepository.save(comment);
    }

//    // id로 Comment 조회
//    public Comment lookUpComment(Long id){
//
//        Comment comment = commentRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("댓글 조회할 id를 찾지 못했습니다."));
//
//        return comment;
//
//    }

    // Comment 조회
    public List<CommentListResponse> findCommentsByPost(Long postId) {

        // postId로 게시물 찾기
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));


        // post에 달린 댓글 리스트 조회
        List<Comment> comments = commentRepository.findByPost(post);
        
        log.info("댓글 조회 실행");

        // CommentListResponse로 변환하여 반환
        return comments.stream()
                .map(CommentListResponse::new)
                .toList();
    }


    // Comment 삭제
    public void delete(Long id){

        // 현재 로그인한 사용자
        User author = getAuthenticatedUser();

        String username = author.getUsername();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제할 id를 찾지 못했습니다."));

        // 현재 로그인한 사용자와 comment 생성한 사용자가 다를 때
        if(!comment.getAuthor().getUsername().equals(username)){
            throw new IllegalArgumentException("댓글을 작성한 사용자가 아닙니다. (delete)");
        }

        log.info("댓글 삭제 실행");
        
        commentRepository.delete(comment);
    }

    @Transactional
    public Comment update(Long postId, Long commentId, UpdateCommentRequest dto){

        // 현재 로그인한 사용자
        User author = getAuthenticatedUser();

        String username = author.getUsername();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));

        Comment comment = commentRepository.findByPostAndId(post, commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정할 id를 찾지 못했습니다."));

        // 현재 로그인한 사용자와 comment 생성한 사용자가 다를 때
        if(!comment.getAuthor().getUsername().equals(username)){
            throw new IllegalArgumentException("댓글 작성한 사용자가 아닙니다. (update)");
        }

        comment.update(dto.getContent());

        log.info("댓글 수정 실행");

        return comment;
    }
}
