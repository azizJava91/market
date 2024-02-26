package com.market.service;

import com.market.dto.request.ReqEmployee;
import com.market.dto.response.RespEmployee;
import com.market.dto.response.Response;

import java.util.List;

public interface EmployeeService {
    Response<List<RespEmployee>> getEmployeeList(ReqEmployee reqEmployee);

    Response create(ReqEmployee reqEmployee);

    Response update(ReqEmployee reqEmployee);

    Response delete( ReqEmployee reqEmployee);

    Response<RespEmployee> getById( ReqEmployee reqEmployee);

    Response<List<RespEmployee>> departmentEmployees(ReqEmployee reqEmployee);

    Response<List<RespEmployee>> EmployeesByPosition(ReqEmployee reqEmployee);

    Response paySalary(ReqEmployee reqEmployee);

}
