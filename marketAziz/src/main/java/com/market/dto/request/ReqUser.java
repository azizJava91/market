package com.market.dto.request;

import com.market.entity.User;
import lombok.Data;

@Data
public class ReqUser {
    private String username;
    private String password;
    private ReqToken reqToken;
    private User user;
    private String role;
    private String fullName;

}
