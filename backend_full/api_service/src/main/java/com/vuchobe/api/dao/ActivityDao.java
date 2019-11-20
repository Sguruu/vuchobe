package com.vuchobe.api.dao;

import com.vuchobe.api.model.v2.Activity;
import com.vuchobe.api.model.v2.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityDao extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM activity a JOIN a.user u WHERE u.id = ?1")
    Page<Activity> findAllByActivityUser(Long userId, Pageable pageable);
}
