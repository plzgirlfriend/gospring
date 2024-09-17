package main.gospring.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupUserResponse {

    private String username;

    @Builder
    public SignupUserResponse(String username) {
        this.username = username;
    }
}
