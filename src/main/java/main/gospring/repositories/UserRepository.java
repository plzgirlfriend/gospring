package main.gospring.repositories;

import main.gospring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // UserServiceImple loadUserByUsername 구현에 사용
    // nickname 정보를 가져오기 위해 Spring Security가 nickname을 전달 받아야함.
    @Query(value = "select * from goSpring_user where user_name = :username", nativeQuery = true)
    Optional<User> findByUsername(String username);

}
