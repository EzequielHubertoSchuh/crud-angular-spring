package com.ezequiel.federativeUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FederativeUnitRepository extends JpaRepository<FederativeUnit, Long>  {
    FederativeUnit findById(long id);
}
