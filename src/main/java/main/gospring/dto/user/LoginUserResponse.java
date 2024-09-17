package main.gospring.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserResponse {

    private String username;
    private String token;
    private String error;

    @Builder
    public LoginUserResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public LoginUserResponse(String error){
        this.error = error;
    }
}
