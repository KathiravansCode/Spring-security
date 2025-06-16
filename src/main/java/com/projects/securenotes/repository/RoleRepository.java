package com.projects.securenotes.repository;

import com.projects.securenotes.model.AppRole;
import com.projects.securenotes.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);

}