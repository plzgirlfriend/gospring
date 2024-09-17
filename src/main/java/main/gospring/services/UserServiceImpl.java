package main.gospring.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.gospring.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import main.gospring.model.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // UserDetails를 User가 구현하기 때문에 UserDetails가 아닌 User로 return
    // 원래는 UserDetails의 username으로 만들어야하지만 User를 직접 구현하여 nickname으로 만듦
    // UserdetailsService는 Spring Security에서 사용자 정보를 가져옴(UserServiceImpl로 내가 직접 구현)
    @Override
    public User loadUserByUsername(String username) {

        User loadUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("loadUserByUsername: " + username));

        log.info("loadUserByUsername: {}", loadUser.getUsername());

        return loadUser;
    }


}
