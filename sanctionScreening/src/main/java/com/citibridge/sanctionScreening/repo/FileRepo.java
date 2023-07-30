package com.citibridge.sanctionScreening.repo;

import com.citibridge.sanctionScreening.entity.File_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<File_entity, Long> {
    File_entity findByFileName(String fileName);
}

