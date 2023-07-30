package com.citibridge.sanctionScreening.repo;

import com.citibridge.sanctionScreening.entity.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepo extends JpaRepository<Keywords,Long> {
    @Query(value = "select count(*) from keyword where lower(name) = lower(?1)",nativeQuery = true)
    int findNameCount(String name);
}
