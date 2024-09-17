package main.gospring.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserRequest {

    private String username;
    private String password;
//    private String authentication;

    public LoginUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
//        this.authentication = authentication;
    }
}
