package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {



    Permission findByName(String name);



    @Query("SELECT p FROM Permission p WHERE ((:name IS NULL) OR (:name IS NOT NULL AND p.name like %:name%)) " +
            " AND ((:status IS NULL) OR (:status IS NOT NULL AND p.status = :status)) order by p.id")
    Page<Permission> findFunctions(@Param("name")String name,
                                   @Param("status")int status,
                                   Pageable pageable);



    @Query("SELECT p FROM Permission p WHERE id=?1")
    List<Permission> findByPermissionId(Long id);




    @Query(value ="SELECT p.name FROM Permission p  INNER JOIN RolePermission rp  ON p.id = rp.permissionId\n" +
            "      INNER JOIN UserRole ur  ON rp.roleId = ur.roleId\n" +
            "    WHERE ur.userId =?1")
    List<Object[]> getPermissionsByUserId(Long userId);





}
