package main.gospring.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupUserRequest {

    private String username;
    private String password;

    @Builder
    public SignupUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
