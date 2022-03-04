package com.sabi.globaladmin.repository;


import com.sabi.globaladmin.model.Ward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * This interface is responsible for Ward crud operations
 */

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {

    Ward findByName (String name);

    @Query("SELECT w FROM Ward w WHERE ((:name IS NULL) OR (:name IS NOT NULL AND w.name like %:name%))"+
            " AND ((:lgaId IS NULL) OR (:lgaId IS NOT NULL AND w.lgaId= :lgaId))")
    List<Ward> listWard(@Param("name") String name,@Param("lgaId") Long lgaId);


    @Query("SELECT w FROM Ward w WHERE ((:name IS NULL) OR (:name IS NOT NULL AND w.name like %:name%))" +
            " AND ((:lgaId IS NULL) OR (:lgaId IS NOT NULL AND w.lgaId = :lgaId))")
    Page<Ward> findWard(@Param("name") String name,
                           @Param("lgaId") Long lgaId,
                           Pageable pageable);

}
