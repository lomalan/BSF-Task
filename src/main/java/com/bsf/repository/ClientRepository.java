package com.bsf.repository;

import com.bsf.model.entity.Client;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

  Optional<Client> findClientByNumber(String number);
  Optional<Client> findClientByEmail(String email);

}
