package com.market.service;

import com.market.dto.request.ReqToken;
import com.market.dto.request.ReqUser;
import com.market.dto.response.RespUser;
import com.market.dto.response.Response;

public interface UserService {
    Response<RespUser> login(ReqUser reqUserUser);

    Response logout(ReqToken reqToken);

    Response roleAssignment(ReqUser reqUser);

    Response createAccount(ReqUser reqUser);
}
