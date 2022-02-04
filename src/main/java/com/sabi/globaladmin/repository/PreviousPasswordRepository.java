package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.PreviousPasswords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("ALL")
@Repository
public interface PreviousPasswordRepository extends JpaRepository<PreviousPasswords, Long> {



     @Query(value ="SELECT id,createdDate, password,userId FROM PreviousPasswords  WHERE userId=?1 ORDER BY createdDate DESC LIMIT 3", nativeQuery=true)
    List<PreviousPasswords> previousPasswords(Long userId);



}
