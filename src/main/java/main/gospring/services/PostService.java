package main.gospring.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.gospring.dto.post.CreatePostRequest;
import main.gospring.dto.post.UpdatePostRequest;
import main.gospring.model.Post;
import main.gospring.model.User;
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
public class PostService {

    private final PostRepository postRepository;
    // author mapping
    private final UserRepository userRepository;

    public User getAuthenticatedUser() {

        // 현재 인증된 사용자 정보 가져오기(반복해서 쓸 거 같아 여기 구현)
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("PostService getAuthenticatedUser: username not found"));
    }


    // Post 생성
    public Post save(CreatePostRequest dto){

        // Post의 author는 authenticated() User의 username
        // 그러므로 SecurityContextHolder에서 authenticated() User 정보를 가져와 author에 저장
        // 현재 인증된 User 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//
//        User author =  userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("PostService.save error: Not found Username"));

        // 현재 인증된 사용자 가져오기
        User author = getAuthenticatedUser();

        // 지금 인증된 User를 author로 설정
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .build();

        return postRepository.save(post);
    }

    // Post 조회
    public List<Post> lookUpAllPost(){

      List<Post> post = postRepository.findAll();

      return post;
    }

    // Post 조회(id 검색)
    public Post lookUpPost(Long id){

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PostService lookUpPost error: post not found"));

        return post;
    }

    // Post 삭제
    // 작성자 본인만 삭제 가능
    public void delete(Long id){

        // 현재 인증된 사용자 정보 가져오기
        User author = getAuthenticatedUser();

        // post_id로 조회 -> 삭제
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PostService delete: post not found"));

        // post author가 본인인지 확인
        if (!post.getAuthor().getUsername().equals(author.getUsername())) {
            throw new IllegalArgumentException("PostService delete error: username mismatch");
        }

        // 본인이면 삭제
        postRepository.delete(post);
    }

    // Post 수정
    // 작성자 본인만 수정 가능
    @Transactional
    public Post update(Long id, UpdatePostRequest dto){

        // 현재 인증된 사용자 가져오기
        User author = getAuthenticatedUser();


        // post_id로 조회 -> 삭제
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PostService delete: post not found"));

        // post author가 본인인지 확인
        if (!post.getAuthor().getUsername().equals(author.getUsername())) {
            throw new IllegalArgumentException("PostService update error: username mismatch");
        }

        post.update(dto.getTitle(), dto.getContent());

        return post;
    }

}
