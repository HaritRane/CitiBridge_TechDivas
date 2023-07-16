package com.citibridge.sanctionScreening.repo;

import com.citibridge.sanctionScreening.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String> {
    @Query(value = "select * from transaction where file_id=?1", nativeQuery = true)
    List<Transaction> getAllTransaction(long fileId);

    @Query(value = "select * from transaction where file_id=?1 and status=\"Validation-pass\" ", nativeQuery = true)
    List<Transaction> getValidTransaction(long fileId);

    @Query(value = "select * from transaction where file_id=?1 and status=\"Validation-fail\" ", nativeQuery = true)
    List<Transaction> getInvalidTransaction(long fileId);

    @Query(value = "select * from transaction where file_id=?1 and status=\"Screening-pass\" ", nativeQuery = true)
    List<Transaction> getScreenPassTransaction(long fileId);

    @Query(value = "select * from transaction where file_id=?1 and status=\"Screening-fail\" ", nativeQuery = true)
    List<Transaction> getScreenFailTransaction(long fileId);


}
