package com.market.service.impl;

import com.market.dto.request.ReqEmployee;
import com.market.dto.request.ReqToken;
import com.market.dto.response.RespEmployee;
import com.market.dto.response.RespStatus;
import com.market.dto.response.Response;
import com.market.entity.*;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumBalanceConstantId;
import com.market.enums.EnumExpenceType;
import com.market.enums.EnumUserRoles;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.DepartmentRepository;
import com.market.repository.EmployeeRepository;
import com.market.repository.ExpenseRepository;
import com.market.repository.MarketTotalBalanceRepository;
import com.market.service.EmployeeService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final Utility utility;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ExpenseRepository expenseRepository;
    private final MarketTotalBalanceRepository marketTotalBalanceRepository;

    @Override
    public Response<List<RespEmployee>> getEmployeeList(ReqEmployee reqEmployee) {
        Response<List<RespEmployee>> response = new Response<>();
        try {
            ReqToken reqToken = reqEmployee.getReqToken();
            if (reqToken == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.HR.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            List<Employee> employees = employeeRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (employees.isEmpty()) {
                throw new MarketException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
            }
            List<RespEmployee> employeeList = employees.stream().map(this::convert).toList();
            response.setT(employeeList);
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
    public Response create(ReqEmployee reqEmployee) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqEmployee.getReqToken();
            String name = reqEmployee.getName();
            String surname = reqEmployee.getSurname();
            Department department = reqEmployee.getDepartment();
            if (reqToken == null || name == null || surname == null || department == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.HR.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            Employee employee = Employee.builder()
                    .name(name)
                    .surname(surname)
                    .address(reqEmployee.getAddress())
                    .dob(reqEmployee.getDob())
                    .phone(reqEmployee.getPhone())
                    .pin(reqEmployee.getPin())
                    .seria(reqEmployee.getSeria())
                    .salary(reqEmployee.getSalary())
                    .position(reqEmployee.getPosition())
                    .department(reqEmployee.getDepartment())
                    .balance(reqEmployee.getBalance())
                    .build();
            employeeRepository.save(employee);
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
    public Response update(ReqEmployee reqEmployee) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqEmployee.getReqToken();
            Long employeeId = reqEmployee.getEmployeeId();
            Department department = reqEmployee.getDepartment();
            if (reqToken == null || employeeId == null || department == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            department = departmentRepository.findDepartmentByDepartmentIdAndActive(reqEmployee.getDepartment()
                    .getDepartmentId(), EnumAvailableStatus.ACTIVE.value);
            if (department == null) {
                throw new MarketException(ExceptionConstants.DEPARTMENT_NOT_FOUND, "Department not found");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.HR.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            Employee employee = employeeRepository.findEmployeeByEmployeeIdAndActive(employeeId, EnumAvailableStatus.ACTIVE.value);
            if (employee == null) {
                throw new MarketException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Emloyee not found");
            }
            employee.setName(reqEmployee.getName());
            employee.setSurname(reqEmployee.getSurname());
            employee.setAddress(reqEmployee.getAddress());
            employee.setDob(reqEmployee.getDob());
            employee.setPhone(reqEmployee.getPhone());
            employee.setPin(reqEmployee.getPin());
            employee.setSeria(reqEmployee.getSeria());
            employee.setSalary(reqEmployee.getSalary());
            employee.setPosition(reqEmployee.getPosition());
            employee.setDepartment(reqEmployee.getDepartment());

            employeeRepository.save(employee);
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
    public Response delete(ReqEmployee reqEmployee) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqEmployee.getReqToken();
            Long employeeId = reqEmployee.getEmployeeId();
            if (reqToken == null || employeeId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.HR.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            Employee employee = employeeRepository.findEmployeeByEmployeeIdAndActive(employeeId, EnumAvailableStatus.ACTIVE.value);
            if (employee == null) {
                throw new MarketException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
            }
            employee.setActive(EnumAvailableStatus.DEACTIVE.value);
            employeeRepository.save(employee);
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
    public Response<RespEmployee> getById(ReqEmployee reqEmployee) {
        Response response = new Response<>();
        try {
            Long employeeId = reqEmployee.getEmployeeId();
            ReqToken reqToken = reqEmployee.getReqToken();
            if (employeeId == null || reqToken == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.HR.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            Employee employee = employeeRepository.findEmployeeByEmployeeIdAndActive(employeeId, EnumAvailableStatus.ACTIVE.value);
            if (employee == null) {
                throw new MarketException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
            }
            RespEmployee respEmployee = RespEmployee.builder()
                    .name(employee.getName())
                    .surname(employee.getSurname())
                    .address(employee.getAddress())
                    .dob(employee.getDob())
                    .phone(employee.getPhone())
                    .pin(employee.getPin())
                    .seria(employee.getSeria())
                    .salary(employee.getSalary())
                    .position(employee.getPosition())
                    .department(employee.getDepartment().getName())
                    .data_date(employee.getData_date())
                    .build();
            response.setT(respEmployee);
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
    public Response<List<RespEmployee>> departmentEmployees(ReqEmployee reqEmployee) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqEmployee.getReqToken();
            Department department = reqEmployee.getDepartment();
            if (reqToken == null || department == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Long departmentId = reqEmployee.getDepartment().getDepartmentId();
            department = departmentRepository.findDepartmentByDepartmentIdAndActive(departmentId, EnumAvailableStatus.ACTIVE.value);
            if (department == null) {
                throw new MarketException(ExceptionConstants.DEPARTMENT_NOT_FOUND, "Department not found");
            }
            List<Employee> employees = employeeRepository.findAllByDepartmentAndActive(department, EnumAvailableStatus.ACTIVE.value);
            if (employees.isEmpty()) {
                throw new MarketException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
            }
            List<RespEmployee> respEmployees = employees.stream().map(this::convert).toList();
            response.setT(respEmployees);
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
    public Response<List<RespEmployee>> EmployeesByPosition(ReqEmployee reqEmployee) {
        Response response = new Response<>();
        try {
            ReqToken reqToken = reqEmployee.getReqToken();
            String position = reqEmployee.getPosition();
            if (reqToken == null || position == null || position.isEmpty()) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }

            UserToken userToken = utility.checkToken(reqToken);
            if (!List.of(EnumUserRoles.HR.value.toLowerCase(), EnumUserRoles.BOSS.value.toLowerCase())
                    .contains(userToken.getUser().getRole().toLowerCase())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized");
            }
            List<Employee> employees = employeeRepository.findAllByPositionAndActive(position.toLowerCase(), EnumAvailableStatus.ACTIVE.value);
            if (employees.isEmpty()) {
                throw new MarketException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
            }
            List<RespEmployee> respEmployees = employees.stream().map(this::convert).toList();
            response.setT(respEmployees);
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
    public Response paySalary(ReqEmployee reqEmployee) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqEmployee.getReqToken();
            Long employeeId = reqEmployee.getEmployeeId();

            if (reqToken == null || employeeId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.ACCAUNTANT.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            Employee employee = employeeRepository.findEmployeeByEmployeeIdAndActive(employeeId, EnumAvailableStatus.ACTIVE.value);
            if (employee == null) {
                throw new MarketException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
            }

            Expense expense = new Expense();
            expense.setType(EnumExpenceType.EMPLOYEE_SALARY_PAY.value);
            expense.setAmount(employee.getSalary());

            MarketTotalBalance marketTotalBalance = marketTotalBalanceRepository.findByIdAndActive(EnumBalanceConstantId.ID_CONSTANT.value,
                    EnumAvailableStatus.ACTIVE.value);

            if (marketTotalBalance.getTotalBalance() < expense.getAmount()) {
                throw new MarketException(ExceptionConstants.BALANCE_NOT_ENOUGHT, "Balance not enough");
            }

            employee.setBalance(employee.getBalance() + employee.getSalary());
            employeeRepository.save(employee);

            marketTotalBalance.setTotalBalance(marketTotalBalance.getTotalBalance() - expense.getAmount());
            marketTotalBalanceRepository.save(marketTotalBalance);

            System.err.println(expense);
            expenseRepository.save(expense);

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


    private RespEmployee convert(Employee employee) {
        return RespEmployee.builder()
                .name(employee.getName())
                .surname(employee.getSurname())
                .address(employee.getAddress())
                .dob(employee.getDob())
                .phone(employee.getPhone())
                .pin(employee.getPin())
                .seria(employee.getSeria())
                .salary(employee.getSalary())
                .position(employee.getPosition())
                .data_date(employee.getData_date())
                .balance(employee.getBalance())
                .department(employee.getDepartment().getName())
                .build();
    }
}
