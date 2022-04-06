package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.UserAppInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserAppInfoRepository extends JpaRepository<UserAppInfo, Long> {

    UserAppInfo findByUserIdAndApplicationCode(Long userId,String applicationCode);

    UserAppInfo findByAuthKey (String authKey);

    UserAppInfo findByUserId(Long userId);


    @Query("SELECT u FROM UserAppInfo u WHERE ((:username IS NULL) OR (:username IS NOT NULL AND u.username like %:username%))" +
            " AND ((:userId IS NULL) OR (:userId IS NOT NULL AND u.userId=:userId))"+
            " AND ((:applicationCode IS NULL) OR (:applicationCode IS NOT NULL AND u.applicationCode=:applicationCode))")
    List<UserAppInfo> findAppInfoList(@Param("username") String username,
                                      @Param("userId") Long userId,
                                      @Param("applicationCode") String applicationCode);

    @Query("SELECT u FROM UserAppInfo u WHERE ((:username IS NULL) OR (:username IS NOT NULL AND u.username like %:username%))" +
            " AND ((:userId IS NULL) OR (:userId IS NOT NULL AND u.userId=:userId))"+
            " AND ((:applicationCode IS NULL) OR (:applicationCode IS NOT NULL AND u.applicationCode=:applicationCode))")
    Page<UserAppInfo> findAppInfos(@Param("username") String username,
                                   @Param("userId") Long userId,
                                   @Param("applicationCode") String applicationCode,
                                   Pageable pageable);
}
