package com.market.repository;

import com.market.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findDepartmentByDepartmentIdAndActive(Long departmentId, Integer active);

    List<Department> findAllByActive(Integer active);

    List<Department> findAllByNameAndActive(String name, Integer active);
}
