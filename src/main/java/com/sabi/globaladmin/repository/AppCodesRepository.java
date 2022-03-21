package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.AppCodes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppCodesRepository extends JpaRepository<AppCodes, Long> {

    AppCodes findByAppCode(String appCode);




    @Query("SELECT a FROM AppCodes a WHERE ((:appCode IS NULL) OR (:appCode IS NOT NULL AND a.appCode like %:appCode%))" +
            " AND ((:appName IS NULL) OR (:appName IS NOT NULL AND a.appName like %:appName%))")
    List<AppCodes> findAppList(@Param("appCode") String appCode,
                            @Param("appName") String appName);

    @Query("SELECT a FROM AppCodes a WHERE ((:appCode IS NULL) OR (:appCode IS NOT NULL AND a.appCode like %:appCode%))" +
            " AND ((:appName IS NULL) OR (:appName IS NOT NULL AND a.appName like %:appName%))")
    Page<AppCodes> findAppCodes(@Param("appCode") String appCode,
                         @Param("appName") String appName,
                         Pageable pageable);
}
