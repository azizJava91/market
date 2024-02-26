package com.market.service.impl;

import com.market.dto.request.ReqProfit;
import com.market.dto.request.ReqToken;
import com.market.dto.response.RespProfit;
import com.market.dto.response.RespStatus;
import com.market.dto.response.Response;
import com.market.entity.Profit;
import com.market.entity.UserToken;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumUserRoles;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.ProfitRepository;
import com.market.service.ProfitService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor


public class ProfitServiceImpl implements ProfitService {

    private final Utility utility;
    private final ProfitRepository profitRepository;

    @Override
    public Response totalProfit(ReqProfit reqProfit) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqProfit.getReqToken();
            if (reqToken == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.BOSS.value.toLowerCase(), EnumUserRoles.ACCAUNTANT.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase()))  {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            List<Profit> profitList = profitRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);

            if (profitList.isEmpty()) {
                throw new MarketException(ExceptionConstants.PROFIT_NOT_FOUND, "Profit not found");
            }
            Double totalProfit = profitList.stream().mapToDouble(Profit::getAmount).sum();

            RespProfit respProfit = RespProfit.builder()
                    .total(totalProfit)
                    .build();
            response.setT(respProfit);
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
}
