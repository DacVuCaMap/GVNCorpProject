package com.GVNCop.app.Repository;

import com.GVNCop.app.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findByName(String roleName);
}
