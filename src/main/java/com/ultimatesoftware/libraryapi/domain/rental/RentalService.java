package com.ultimatesoftware.libraryapi.domain.rental;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;

@Service
public class RentalService {

	private final RentalRepository rentalRepository;

	@Autowired
	public RentalService(final RentalRepository rentalRepository) {
		this.rentalRepository = rentalRepository;
	}

	@Transactional(readOnly = true)
	public Optional<Rental> findRentalById(Long id) {
		return rentalRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Page<Rental> getRentalsByBook(Book book, Pageable pageable) {
		return rentalRepository.findRentalsByBook(book, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Rental> getRentalsByCardholder(CardHolder cardHolder, Pageable pageable) {
		return rentalRepository.findRentalsByCardHolder(cardHolder, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Rental> getRentalsDueAfter(LocalDate date, Pageable pageable) {
		return rentalRepository.findRentalsByDueDateAfter(date, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Rental> getAllRentals(Pageable pageable) {
		return rentalRepository.findAll(pageable);
	}

	@Transactional
	public Rental save(Rental rental) {
		return rentalRepository.save(rental);
	}

	@Transactional
	public void delete(Rental rental) {
		rentalRepository.delete(rental);
	}

}