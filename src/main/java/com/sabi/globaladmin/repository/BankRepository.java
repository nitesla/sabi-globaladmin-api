package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByName(String name);
    Bank findByCode(String code);

    Bank findBankById(Long id);

    @Query("SELECT b FROM Bank b WHERE ((:status IS NULL) OR (:status IS NOT NULL AND b.status = :status))")
    List<Bank> findByStatus(int status);

    @Query("SELECT b FROM Bank b WHERE ((:name IS NULL) OR (:name IS NOT NULL AND b.name like %:name%))" +
            " AND ((:code IS NULL) OR (:code IS NOT NULL AND b.code like %:code%)) order by b.id desc")
    Page<Bank> findBanks(@Param("name") String name,
                         @Param("code") String bankCode,
                         Pageable pageable);
}
