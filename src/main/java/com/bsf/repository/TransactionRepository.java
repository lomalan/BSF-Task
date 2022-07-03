package com.bsf.repository;

import com.bsf.model.entity.Account;
import com.bsf.model.entity.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Optional<Transaction> findByNumber(String transactionNumber);
    List<Transaction> findTransactionsByFrom(Account account);
    List<Transaction> findTransactionByTo(Account account);
}
