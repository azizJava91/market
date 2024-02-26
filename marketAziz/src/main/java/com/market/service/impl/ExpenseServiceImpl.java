package com.market.service.impl;

import com.market.dto.request.ReqExpense;
import com.market.dto.request.ReqToken;
import com.market.dto.response.RespExpense;
import com.market.dto.response.RespStatus;
import com.market.dto.response.Response;
import com.market.entity.Expense;
import com.market.entity.UserToken;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumUserRoles;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.ExpenseRepository;
import com.market.service.ExpenseService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final Utility utility;
    private final ExpenseRepository expenceRepository;

    @Override
    public Response<List<RespExpense>> getExpenceByType(ReqExpense reqExpense) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqExpense.getReqToken();
            String type = reqExpense.getType();
            if (reqToken == null || type == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!userToken.getUser().getRole().equalsIgnoreCase(EnumUserRoles.BOSS.value)) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            List<Expense> expenseList = expenceRepository.findAllByTypeAndActive(type.toUpperCase(), EnumAvailableStatus.ACTIVE.value);
            if (expenseList.isEmpty()) {
                throw new MarketException(ExceptionConstants.EXPENSE_NOT_FOUND, "Expense not found");
            }
            List<RespExpense> respExpenseList = expenseList.stream().map(this::convert).toList();

            response.setT(respExpenseList);
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

    private RespExpense convert(Expense expense) {
        return RespExpense.builder()
                .expenceId(expense.getExpenceId())
                .date(expense.getDataDate())
                .amount(expense.getAmount())
                .type(expense.getType())
                .build();
    }
}
