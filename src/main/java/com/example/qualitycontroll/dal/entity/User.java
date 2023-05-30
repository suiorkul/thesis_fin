package com.example.qualitycontroll.dal.entity;

import com.example.qualitycontroll.dal.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractModel<Long> implements UserDetails {

    String username;

    String firstname;

    String lastname;

    String email;

    String phoneNumber;

    String password;

    String salary;

    String education;

    String expirience;

    Date dateOfReqist;

    Date dateOfSalary;


    @Enumerated(value = EnumType.STRING)
    Role role;


    @Column(columnDefinition = "boolean default true")
    Boolean active = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
