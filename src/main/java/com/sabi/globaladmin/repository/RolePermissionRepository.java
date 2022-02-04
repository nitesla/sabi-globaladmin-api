package com.sabi.globaladmin.repository;



import com.sabi.globaladmin.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {



}
