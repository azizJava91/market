package com.market.repository;

import com.market.entity.Department;;
import com.market.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByActive(Integer active);

    Employee findEmployeeByEmployeeIdAndActive(Long emloyeeId, Integer active);

    List<Employee> findAllByDepartmentAndActive(Department department, Integer active);

    List<Employee> findAllByPositionAndActive(String position, Integer active);

}