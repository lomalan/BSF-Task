package com.bsf.repository;

import com.bsf.model.entity.Account;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

  Optional<Account> findAccountByNumber(String number);
}
