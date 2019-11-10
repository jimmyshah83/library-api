package com.ultimatesoftware.libraryapi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.book.BookRepository;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolderRepository;
import com.ultimatesoftware.libraryapi.domain.rental.Rental;
import com.ultimatesoftware.libraryapi.domain.rental.RentalRepository;

/**
 * This class is solely responsible for seeding our database with some basic info. You should not need to make any
 * changes here.
 */
@Component
public class DataLoader implements ApplicationRunner {

	private BookRepository bookRepository;

	private CardHolderRepository cardHolderRepository;

	private RentalRepository rentalRepository;

	@Autowired
	public DataLoader(BookRepository bookRepository,
			CardHolderRepository cardHolderRepository,
			RentalRepository rentalRepository) {
		this.bookRepository = bookRepository;
		this.cardHolderRepository = cardHolderRepository;
		this.rentalRepository = rentalRepository;
	}

	@Override
	public void run(ApplicationArguments args) {

		List<Book> books = new ArrayList<>();
		books.addAll(createShakespeare());
		books.addAll(createOrwell());
		books.addAll(createSalinger());

		List<CardHolder> cardHolders = new ArrayList<>(createCardHolders());

		createRentals(books, cardHolders);
	}

	private Collection<Book> createShakespeare() {
		Collection<Book> books = Arrays.asList(
				new Book("Hamlet", "3-598-21500-2", "William Shakespeare"),
				new Book("Othello", "3-598-21501-0", "William Shakespeare"),
				new Book("Macbeth", "3-598-21502-9", "William Shakespeare")
		);
		bookRepository.saveAll(books);
		return books;
	}

	private Collection<Book> createOrwell() {
		Collection<Book> books = Arrays.asList(
				new Book("1984", "3-598-21523-1", "George Orwell"),
				new Book("Animal Farm", "3-598-21524-X", "George Orwell")
		);
		bookRepository.saveAll(books);
		return books;
	}

	private Collection<Book> createSalinger() {
		Collection<Book> books = Collections.singleton(new Book("The Catcher in the Rye", "3-598-21526-6",
				"J. D. Salinger"));
		bookRepository.saveAll(books);
		return books;
	}

	private Collection<CardHolder> createCardHolders() {
		return cardHolderRepository.saveAll(
				Arrays.asList(
						new CardHolder("North", "Kristine", "5305477475614179"),
						new CardHolder("Garland", "Robert", "4570216030512630")
				)
		);
	}

	private void createRentals(List<Book> books, List<CardHolder> cardHolders) {
		rentalRepository.saveAll(Arrays.asList(
				new Rental(cardHolders.get(0), books.get(0), LocalDate.of(2019, 1, 1)),
				new Rental(cardHolders.get(1), books.get(4), LocalDate.of(2021, 7, 10))
		));
	}
}
