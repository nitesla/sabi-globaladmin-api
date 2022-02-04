package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@SuppressWarnings("ALL")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);





//    @Query("SELECT r FROM Role r WHERE ((:name IS NULL) OR (:name IS NOT NULL AND r.name like %:name%))" +
//            " AND ((:isActive IS NULL) OR (:isActive IS NOT NULL AND r.isActive = :isActive)) order by r.id")
//    Page<Role> findRoles(@Param("name") String name,
//                         @Param("status") int status,
//                         Pageable pageable);


}
