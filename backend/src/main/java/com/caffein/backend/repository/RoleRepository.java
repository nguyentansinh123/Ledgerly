package com.caffein.backend.repository;

import org.springframework.stereotype.Repository;

import com.caffein.backend.models.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<String, Role>{
    Optional<Role> findByName(String string);
}
