package com.example.qualitycontroll.service;

import com.example.qualitycontroll.dal.entity.Department;
import com.example.qualitycontroll.dal.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService extends AbstractService<Department, Long>{

    private final DepartmentRepository departmentRepository;

    @Override
    protected JpaRepository<Department, Long> getRepository() {
        return departmentRepository;
    }
}
