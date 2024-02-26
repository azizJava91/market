package com.market.controller;

import com.market.dto.request.ReqToken;
import com.market.dto.request.ReqUser;
import com.market.dto.response.RespUser;
import com.market.dto.response.Response;
import com.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/createAccount")

//    {
//            "username": "?????",
//            "password": "??????",
//            "fullName": "???? ?????"
//    }
    public Response createAccount(@RequestBody ReqUser reqUser) {
        return userService.createAccount(reqUser);
    }

    @PostMapping("/login")

//    {
//            "username": "????",
//            "password": "??????"
//    }
    public Response<RespUser> login(@RequestBody ReqUser reqUserUser) {
        return userService.login(reqUserUser);
    }

    @PutMapping("/logout")

//    {
//            "userId": ??,
//            "token": "???????????????"
//    }
    public Response logout(@RequestBody ReqToken reqToken) {
        return userService.logout(reqToken);
    }

    @PostMapping("/roleAssignment")

//    {
//        "reqToken": {
//        "userId": ?,     ==========================>  user.getRole must be "boss"
//        "token": "??????????????????????????"
//    },
//        "user": {
//        "userId": ??
//    },
//        "role": "HR"
//    }
    public Response roleAssignment(@RequestBody ReqUser reqUser) {
        return userService.roleAssignment(reqUser);
    }
}
