package com.citibridge.sanctionScreening.repo;

import com.citibridge.sanctionScreening.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    long countByStatus(String status);
    long countByStatusAndDate(String status, LocalDate date);
    long countByDate(LocalDate date);

//    @Query(value = "select t from transaction t join keyword k on lower(t.payee-name) =lower(k.name) or lower(t.payer-name)=lower(k.name) where file_id=?1",nativeQuery = true)
//    List<Transaction> getScreenedTransactions(long fileId);
}
