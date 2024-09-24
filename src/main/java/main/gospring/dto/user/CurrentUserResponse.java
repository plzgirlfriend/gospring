package main.gospring.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CurrentUserResponse {


    private String username;

    @Builder
    public CurrentUserResponse(String username) {
        this.username = username;
    }
}
