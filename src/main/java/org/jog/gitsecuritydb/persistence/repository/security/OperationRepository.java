package org.jog.gitsecuritydb.persistence.repository.security;

import org.jog.gitsecuritydb.persistence.entity.security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM  Operation  o WHERE o.permitAll = true")
    List<Operation> findPublicAccess();
    Optional<Operation> findOperationByName(String operation);

}
