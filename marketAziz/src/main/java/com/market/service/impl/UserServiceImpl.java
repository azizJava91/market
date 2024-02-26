package com.market.service.impl;

import com.market.dto.request.ReqToken;
import com.market.dto.request.ReqUser;
import com.market.dto.response.RespStatus;
import com.market.dto.response.RespToken;
import com.market.dto.response.RespUser;
import com.market.dto.response.Response;
import com.market.entity.User;
import com.market.entity.UserToken;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumTokenAvailableStatus;
import com.market.enums.EnumUserRoles;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.UserRepository;
import com.market.repository.UserTokenRepository;
import com.market.service.UserService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final Utility utility;

    @Override
    public Response<RespUser> login(ReqUser reqUser) {
        Response response = new Response();
        try {
            String username = reqUser.getUsername();
            String password = reqUser.getPassword();
            if (username == null || password == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            User user = userRepository.findUserByUsernameAndPasswordAndActive(username, password, EnumAvailableStatus.ACTIVE.value);
            if (user == null) {
                throw new MarketException(ExceptionConstants.USER_NOT_FOUND, "User not found");
            }
            String token = UUID.randomUUID().toString();
            UserToken userToken = new UserToken();
            userToken.setUser(user);
            userToken.setToken(token);
            userTokenRepository.save(userToken);
            RespUser respUser = new RespUser();
            respUser.setUsername(username);
            respUser.setFullName(user.getFullName());
            respUser.setRespToken(convertToken(userToken));
            response.setT(respUser);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException me) {
            me.printStackTrace();
            response.setRespons_Status(new RespStatus(me.getCode(), me.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    @Override
    public Response logout(ReqToken reqToken) {
        Response response = new Response();
        try {
            UserToken userToken = utility.checkToken(reqToken);
            userToken.setActive(EnumTokenAvailableStatus.DEACTIVE.value);
            userTokenRepository.save(userToken);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException me) {
            me.printStackTrace();
            response.setRespons_Status(new RespStatus(me.getCode(), me.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    @Override
    public Response roleAssignment(ReqUser reqUser) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqUser.getReqToken();
            User user = reqUser.getUser();
            String role = reqUser.getRole();
            if (reqToken == null || user == null || role == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            user = userRepository.findUserByUserId(reqUser.getUser().getUserId());
            if (user == null) {
                throw new MarketException(ExceptionConstants.USER_NOT_FOUND, "User not found");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.BOSS.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }

            user.setRole(role);
            userRepository.save(user);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException me) {
            me.printStackTrace();
            response.setRespons_Status(new RespStatus(me.getCode(), me.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    @Override
    public Response createAccount(ReqUser reqUser) {
        Response response = new Response();
        try {
            String username = reqUser.getUsername();
            String password = reqUser.getPassword();
            String fullName = reqUser.getFullName();
            if (username == null || password == null || fullName == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .fullName(fullName)
                    .role(reqUser.getRole())
                    .build();

            if (reqUser.getRole() == null) {
                user.setRole("customer");
            }
            userRepository.save(user);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (MarketException me) {
            me.printStackTrace();
            response.setRespons_Status(new RespStatus(me.getCode(), me.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }

        return response;
    }

    private RespToken convertToken(UserToken userToken) {
        return RespToken.builder()
                .token(userToken.getToken())
                .userId(userToken.getUser().getUserId())
                .build();
    }
}
