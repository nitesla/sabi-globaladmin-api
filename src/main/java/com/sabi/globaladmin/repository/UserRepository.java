package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByUsername(String username);

    User findByEmailOrPhone (String email, String phone);

    User findByResetToken (String resetToken);


    @Query("SELECT u FROM User u WHERE ((:firstName IS NULL) OR (:firstName IS NOT NULL AND u.firstName like %:firstName%))" +
            " AND ((:lastName IS NULL) OR (:lastName IS NOT NULL AND u.lastName = :lastName))"+
            " AND ((:phone IS NULL) OR (:phone IS NOT NULL AND u.phone = :phone))"+
            " AND ((:status IS NULL) OR (:status IS NOT NULL AND u.status = :status))"+
            " AND ((:email IS NULL) OR (:email IS NOT NULL AND u.email = :email)) order by u.id")
    Page<User> findUsers(@Param("firstName")String firstName,
                         @Param("lastName")String lastName,
                         @Param("phone")String phone,
                         @Param("status")int status,
                         @Param("email")String email,
                         Pageable pageable);
}
