package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByUsername(String username);

    User findByEmailOrPhone (String email, String phone);

    User findByResetToken (String resetToken);
}
