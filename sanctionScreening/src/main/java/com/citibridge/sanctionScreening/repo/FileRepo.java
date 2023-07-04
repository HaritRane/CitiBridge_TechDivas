package com.citibridge.sanctionScreening.repo;

import com.citibridge.sanctionScreening.entity.File_entity;
import com.citibridge.sanctionScreening.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface FileRepo extends JpaRepository<File_entity,String> {
}

