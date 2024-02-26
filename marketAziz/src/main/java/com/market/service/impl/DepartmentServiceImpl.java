package com.market.service.impl;

import com.market.dto.request.ReqDepartment;
import com.market.dto.request.ReqToken;
import com.market.dto.response.RespDepartment;
import com.market.dto.response.RespStatus;
import com.market.dto.response.Response;
import com.market.entity.Department;
import com.market.entity.UserToken;
import com.market.enums.EnumAvailableStatus;
import com.market.enums.EnumUserRoles;
import com.market.exception.ExceptionConstants;
import com.market.exception.MarketException;
import com.market.repository.DepartmentRepository;
import com.market.service.DepartmentService;
import com.market.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final Utility utility;

    @Override
    public Response<List<RespDepartment>> departmentList(ReqDepartment reqDepartment) {
        Response<List<RespDepartment>> response = new Response<>();
        try {
            ReqToken reqToken = reqDepartment.getReqToken();
            if (reqToken == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.HR.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You are not authorized this service");
            }
            List<Department> departments = departmentRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (departments.isEmpty()) {
                throw new MarketException(ExceptionConstants.DEPARTMENT_NOT_FOUND, "Department not found");
            }
            List<RespDepartment> respDepartmentList = departments.stream().map(this::convert).toList();

            response.setT(respDepartmentList);
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
    public Response create(ReqDepartment reqDepartment) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqDepartment.getReqToken();
            String name = reqDepartment.getName();
            if (reqToken == null || name == null || name.length() < 2) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.HR.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this operation");
            }
            List<Department> departments = departmentRepository.findAllByNameAndActive(name, EnumAvailableStatus.ACTIVE.value);
            if (!departments.isEmpty()) {
                throw new MarketException(ExceptionConstants.DEPARTMENT_ALREADY_EXISTS, "Department already exists");
            }
            Department department = Department.builder()
                    .name(reqDepartment.getName())
                    .dataDate(reqDepartment.getDataDate())
                    .build();

            departmentRepository.save(department);
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
    public Response delete(ReqDepartment reqDepartment) {
        Response response = new Response();
        try {
            Long departmentId = reqDepartment.getDepartmentId();
            ReqToken reqToken = reqDepartment.getReqToken();

            if (departmentId == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.HR.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this operation");
            }
            Department department = departmentRepository.findDepartmentByDepartmentIdAndActive(departmentId, EnumAvailableStatus.ACTIVE.value);
            if (department == null) {
                throw new MarketException(ExceptionConstants.DEPARTMENT_NOT_FOUND, "Department not found");
            }
            department.setActive(EnumAvailableStatus.DEACTIVE.value);
            departmentRepository.save(department);
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
    public Response update(ReqDepartment reqDepartment) {
        Response response = new Response();
        try {
            ReqToken reqToken = reqDepartment.getReqToken();
            Long departmentId = reqDepartment.getDepartmentId();
            String name = reqDepartment.getName();
            if (reqToken == null || departmentId == null || name == null) {
                throw new MarketException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = utility.checkToken(reqToken);
            if (!EnumUserRoles.HR.value.equalsIgnoreCase(userToken.getUser().getRole())) {
                throw new MarketException(ExceptionConstants.YOU_ARE_NOT_AUTHORIZED, "You not authorized this service");
            }
            Department department = departmentRepository.
                    findDepartmentByDepartmentIdAndActive(departmentId, EnumAvailableStatus.ACTIVE.value);
            if (department == null) {
                throw new MarketException(ExceptionConstants.DEPARTMENT_NOT_FOUND, "Department not found");
            }
            department.setName(reqDepartment.getName());
            departmentRepository.save(department);
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

    private RespDepartment convert(Department department) {
        return RespDepartment.builder()
                .departmentId(department.getDepartmentId())
                .name(department.getName())
                .dataDate(department.getDataDate())
                .build();
    }
}
