package main.gospring.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import main.gospring.Jwt.TokenProvider;
import main.gospring.dto.user.*;
import main.gospring.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import main.gospring.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import main.gospring.model.User;
import java.util.List;

// @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/")
    public String index() {
        return "인증 성공!";
    }


    // Use userService.save
    @PostMapping("/api/signup")
    public ResponseEntity<SignupUserResponse> signup(@RequestBody SignupUserRequest signupUserRequest) {

        User user = userService.save(signupUserRequest);

        SignupUserResponse signupUserResponse = SignupUserResponse.builder()
                .username(user.getUsername())
                .build();

        return ResponseEntity.status(200).body(signupUserResponse);
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest loginUserRequest) {

           // User 검증
           User user = userServiceImpl.loadUserByUsername(loginUserRequest.getUsername());


            // password 검증
            if (user != null && userService.validatePassword(loginUserRequest.getPassword(), user.getPassword())) {
                // password 일치 => generateToken
                String token = tokenProvider.generateToken(user);

                LoginUserResponse loginResponse = LoginUserResponse.builder()
                        .username(user.getUsername())
                        .token(token)
                        .build();

                log.info("token: {}", token);

                return ResponseEntity.status(200).body(loginResponse);
            } else {
                // 비밀번호가 틀리면 오류 응답
                return ResponseEntity.status(401).body(new LoginUserResponse("LoginResponse Error"));
            }
        }

//            // Authentication 객체 생성 -> Custom UserDetailsService를 통해 User 객체를 SecurityContext에 저장
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginUserRequest.getUsername(), loginUserRequest.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            HttpSession session = request.getSession(true);
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//
//            log.info("sessionID: {}", session.getId());
//
//            User user = (User) authentication.getPrincipal();
//
//            LoginResponse loginResponse = LoginResponse.builder()
//                    .username(user.getUsername())
//                    .nickname(user.getNickname())
//                    .authentication(session.getId())
//                    .build();
//
//            log.info("loginResponse: {}", session.getId());
//
//            // response를 안 해줘서 작동을 안 했나?
//    //        response.addCookie(new Cookie("JSESSIONID", session.getId()));
//
//            return ResponseEntity.status(200).body(loginResponse);



    @GetMapping("/api/user")
    public ResponseEntity<List<UserListResponse>> getUsers() {
        List<UserListResponse> users = userService.findAll()
                .stream()
                .map(UserListResponse::new)
                .toList();

        return ResponseEntity.status(200).body(users);
    }

    // 로그아웃
    // jwt 사용하면 필요 x
    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok().build();
    }

}
