package com.market.service;

import com.market.dto.request.ReqDepartment;
import com.market.dto.response.RespDepartment;
import com.market.dto.response.Response;

import java.util.List;

public interface DepartmentService {
    Response<List<RespDepartment>> departmentList(ReqDepartment reqDepartment);

    Response create(ReqDepartment reqDepartment);

    Response delete(ReqDepartment reqDepartment);

    Response update(ReqDepartment reqDepartment);
}
