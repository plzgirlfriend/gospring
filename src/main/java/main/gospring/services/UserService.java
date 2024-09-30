package main.gospring.services;

import lombok.RequiredArgsConstructor;
import main.gospring.dto.user.SignupUserRequest;
import main.gospring.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import main.gospring.model.User;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // User 생성
    public User save(SignupUserRequest signupUserRequest){

        return userRepository.save(User.builder()
                .username(signupUserRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(signupUserRequest.getPassword()))
//                        .role(addUserRequest.getRole())
                .build());
    }

    // user 조회
    public List<User> findAll() {

        return userRepository.findAll();
    }

    // password 검증(로그인 할 때 확인하기 위해서)
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
