package com.example.qualitycontroll.dal.repository;

import com.example.qualitycontroll.dal.entity.Department;
import com.example.qualitycontroll.dal.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient getPatientByFirstNameAndLastNameAndPatronymic(String firstName, String lastName, String patronymic);

    @Query(value = "select * from patients pt join departments d on pt.department = d.id and pt.first_name like %?1% or pt.last_name like %?1% or pt.patronymic like %?1% or pt.email like %?1% or pt.phone_number like %?1% or d.name like %?2%", nativeQuery = true)
    List<Patient> findAllBySearchAndDepartment(String keyWord, String departmentName);

    @Query(value = "select * from patients pt WHERE pt.first_name like %:name% or pt.last_name like %:name% or pt.patronymic like %:name% or pt.email like %:name% or pt.phone_number like %:name% order by pt.created DESC", nativeQuery = true)
    List<Patient> findAllByFirstName(@Param("name") String name);

    List<Patient> findAllByDepartmentOrderByCreatedDesc(Department department);

    Patient findByFirstNameAndLastNameAndPatronymic(String firstName, String lastName, String patronymic);

    Patient getPatientById(Long id);
}
