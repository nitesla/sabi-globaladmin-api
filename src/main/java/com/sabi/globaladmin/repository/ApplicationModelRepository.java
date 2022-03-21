package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.ApplicationModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationModelRepository extends JpaRepository<ApplicationModule, Long> {


    ApplicationModule findByAppCode (String appCode);



    @Query("SELECT a FROM ApplicationModule a WHERE ((:appCode IS NULL) OR (:appCode IS NOT NULL AND a.appCode like %:appCode%))" +
            " AND ((:name IS NULL) OR (:name IS NOT NULL AND a.name like %:name%))"+
            " AND ((:status IS NULL) OR (:status IS NOT NULL AND a.status =:status))")
    Page<ApplicationModule> findAppModules(@Param("appCode") String appCode,
                                         @Param("name") String name,
                                         @Param("status") int status,
                                         Pageable pageable);



    @Query("SELECT a FROM ApplicationModule a WHERE ((:appCode IS NULL) OR (:appCode IS NOT NULL AND a.appCode like %:appCode%))" +
            " AND ((:name IS NULL) OR (:name IS NOT NULL AND a.name like %:name%))"+
            " AND ((:status IS NULL) OR (:status IS NOT NULL AND a.status =:status))")
    List<ApplicationModule> findAppModulesList(@Param("appCode") String appCode,
                                               @Param("name") String name,
                                               @Param("status") int status);

}
