package com.sabi.globaladmin.repository;



import com.sabi.globaladmin.model.RolePermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findAllByRoleId(Long roleId);
    RolePermission findByRoleIdAndPermissionId(Long roleId,Long permissionId);

    @Query("SELECT p FROM RolePermission p WHERE ((:roleId IS NULL) OR (:roleId IS NOT NULL AND p.roleId = :roleId)) order by p.id")
    Page<RolePermission> findRolePermission(@Param("roleId") Long roleId,
                                            Pageable Pageable);
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);



    @Query("SELECT p FROM RolePermission p WHERE ((:roleId IS NULL) OR (:roleId IS NOT NULL AND p.roleId = :roleId)) order by p.id")
    List<RolePermission> getPermissionsByRole(@Param("roleId") Long roleId);


    @Modifying
    @Transactional
    @Query("delete from RolePermission p where p.id in ?1")
    void deleteByIdIn(List<Long> ids);

}
