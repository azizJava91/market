package com.market.controller;

import com.market.dto.request.ReqDepartment;
import com.market.dto.response.RespDepartment;
import com.market.dto.response.Response;
import com.market.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/list")

//    {
//        "reqToken": {
//        "userId": ??,
//        "token": "?????????????????????"
//    }
//    }
    public Response<List<RespDepartment>> departmentList(@RequestBody ReqDepartment reqDepartment) {
        return departmentService.departmentList(reqDepartment);
    }

    @PostMapping("/create")

//    {
//        "reqToken": {
//        "userId": ??,
//        "token": "??????????????????????/"
//    },
//        "name": "???????"
//    }
    public Response create(@RequestBody ReqDepartment reqDepartment) {
        return departmentService.create(reqDepartment);
    }

    @PostMapping("/delete")

//    {
//        "reqToken": {
//        "userId": ??,
//        "token": "???????????????????????????"
//    },
//        "departmentId": ??
//    }

    public Response delete(@RequestBody ReqDepartment reqDepartment) {
        return departmentService.delete(reqDepartment);
    }

    @PostMapping("/update")

//    {
//        "reqToken": {
//        "userId": ??,
//        "token": "???????????????????????"
//    },
//        "departmentId": ??,
//            "name": "????????"
//    }

    public Response update(@RequestBody ReqDepartment reqDepartment) {
        return departmentService.update(reqDepartment);
    }

}
