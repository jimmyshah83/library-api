package com.ultimatesoftware.libraryapi.domain.rental;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

	Page<Rental> findRentalsByBook(Book book, Pageable pageable);

	Page<Rental> findRentalsByCardHolder(CardHolder cardHolder, Pageable pageable);

	Page<Rental> findRentalsByDueDateAfter(LocalDate date, Pageable pageable);
}
