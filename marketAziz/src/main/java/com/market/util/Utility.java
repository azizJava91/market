package com.market.util;

import com.market.dto.request.ReqToken;
import com.market.entity.User;
import com.market.entity.UserToken;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumTokenAvailableStatus;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.UserRepository;
import com.market.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@RequiredArgsConstructor
@Component
public class Utility {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    public UserToken checkToken(ReqToken reqToken) {
        Long userId = reqToken.getUserId();
        String token = reqToken.getToken();
        User user = userRepository.findUserByUserId(userId);
        if (userId == null || token == null) {
            throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
        }
        if (user == null) {
            throw new MarketException(ExceptionConstants.USER_NOT_FOUND, "User not found");
        }
        UserToken userToken = userTokenRepository.findUserTokenByUserAndTokenAndActive(user, token, EnumAvailableStatus.ACTIVE.value);
        if (userToken == null) {
            throw new MarketException(ExceptionConstants.USER_TOKEN_NOT_FOUND, "token expired or invalid");
        }
        return userToken;
    }

}

