package com.example.qualitycontroll.dal.repository;

import com.example.qualitycontroll.dal.entity.User;
import com.example.qualitycontroll.dal.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findUserByRoleIsNot(Role role);

    List<User> findAllByRoleIs(Role role);
}
