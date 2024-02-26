package com.market.controller;

import com.market.dto.request.ReqEmployee;
import com.market.dto.response.RespEmployee;
import com.market.dto.response.Response;
import com.market.service.EmployeeService;
import com.market.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/list")
//
//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    }
//    }
    public Response<List<RespEmployee>> getEmployeeList(@RequestBody ReqEmployee reqEmployee) {
        return employeeService.getEmployeeList(reqEmployee);
    }

    @PostMapping("/create")

//    {
//        "reqToken": {
//        "userId": 1,
//                "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "name": "?????",
//            "surname": "??????????",
//            "address": "???",
//            "dob": "2000-05-07",
//            "phone": "??????",
//            "pin": ???????????,
//            "seria": ???????,
//            "salary": ???,
//            "position": "????",
//            "department": {
//        "departmentId": 2
//    }
//    }

    public Response create(@RequestBody ReqEmployee reqEmployee) {
        return employeeService.create(reqEmployee);
    }

    @PostMapping("/update")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//            "employeeId":9,
//            "name": "?????",
//            "surname": "???????????",
//            "address": "?????",
//            "dob": "1999-05-07",
//            "phone": "????????",
//            "pin": ????,
//            "seria": ???????????,
//            "salary": ????,
//            "position": "???????",
//            "department": {
//          "departmentId": 1
//    }
//    }

    public Response update(@RequestBody ReqEmployee reqEmployee) {
        return employeeService.update(reqEmployee);
    }

    @PostMapping("/delete")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "employeeId": 9
//    }

    public Response delete( @RequestBody ReqEmployee reqEmployee) {
        return employeeService.delete( reqEmployee);
    }

    @GetMapping("getById")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "employeeId": 9
//    }

    public Response<RespEmployee> getById( @RequestBody ReqEmployee reqEmployee) {
        return employeeService.getById( reqEmployee);
    }

    @GetMapping("/getByDepartment")

//    {
//        "reqToken": {
//        "userId": 1,
//                "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "department": {
//        "departmentId": 1
//    }
//    }

    public Response<List<RespEmployee>> departmentEmployees(@RequestBody ReqEmployee reqEmployee) {
        return employeeService.departmentEmployees(reqEmployee);
    }

    @GetMapping("/getByPosition")

//    {
//        "reqToken": {
//        "userId": 1,
//        "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//    },
//        "position": "HR"
//    }

    public Response<List<RespEmployee>> EmployeesByPosition(@RequestBody ReqEmployee reqEmployee) {
        return employeeService.EmployeesByPosition(reqEmployee);
    }
   @PostMapping("/paySalary")

//   {
//       "reqToken": {
//       "userId": 1,
//       "token": "c51115a8-6033-4f73-be9f-beb77546706b"
//   },
//       "employeeId": 3
//   }


    public Response paySalary(@RequestBody ReqEmployee reqEmployee){
        return employeeService.paySalary(reqEmployee);
   }
}



