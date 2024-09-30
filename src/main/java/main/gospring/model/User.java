package main.gospring.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "goSpring_user")
@Entity
@Getter
@NoArgsConstructor
// UserDetails는 Spring Security에서 사용자 인증 정보를 담아두는 곳
public class User implements UserDetails {

//      String으로 생성할래
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="user_id", nullable = false)
//    private Long id;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "user_id", updatable = false, nullable = false)
    private String id;

    @Column(name="user_name", nullable = false, unique=true)
    private String username;

    @Column(name="user_password", nullable = false)
    private String password;

//    @Column(name="nickname", nullable = false, unique=true)
//    private String nickname;z

//    // @PrePersist로 User Entity가 저장되기 전에 id를 UUID.random으로 저장
//    @PrePersist
//    public void prePersist() {
//        if (this.id == null){
//            this.id = UUID.randomUUID().toString();
//        }
//    }

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // 비밀번호는 항상 암호화하여 저장
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        // 만료 x
        return true;
    }

    // 계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        // 잠금 x
        return true;
    }

    // 패스워드 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        // 만료 x
        return true;
    }

    // 계정 사용가능 여부
    @Override
    public boolean isEnabled() {
        // 사용 o
        return true;
    }

}
