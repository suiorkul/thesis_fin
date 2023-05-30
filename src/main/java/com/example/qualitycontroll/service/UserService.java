package com.example.qualitycontroll.service;

import com.example.qualitycontroll.dal.entity.User;
import com.example.qualitycontroll.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractService<User, Long> implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
