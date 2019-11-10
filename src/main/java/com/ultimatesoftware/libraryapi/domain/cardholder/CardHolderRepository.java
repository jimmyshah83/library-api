package com.ultimatesoftware.libraryapi.domain.cardholder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardHolderRepository extends JpaRepository<CardHolder, Long> {

}
