package main.gospring.dto.user;

import lombok.Getter;
import main.gospring.model.User;

@Getter
public class UserListResponse {

    private String id;
    private String username;

    public UserListResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

}
