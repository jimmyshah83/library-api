package com.ultimatesoftware.libraryapi.rest.rental;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.book.BookService;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolderService;
import com.ultimatesoftware.libraryapi.domain.rental.Rental;
import com.ultimatesoftware.libraryapi.domain.rental.RentalService;
import com.ultimatesoftware.libraryapi.rest.book.BookDto;

/**
 * @see com.ultimatesoftware.libraryapi.rest.rental.RentalControllerTest
 */

@RestController
@RequestMapping("/v1/rentals")
public class RentalController {
	private final RentalService service;
	private final BookService bookService;
	private final CardHolderService cardHolderService;
	private final ModelMapper modelMapper;

	@Autowired
	public RentalController(final RentalService service, final BookService bookService, 
			final CardHolderService cardHolderService, final ModelMapper modelMapper) {
		super();
		this.service = service;
		this.bookService = bookService;
		this.cardHolderService = cardHolderService;
		this.modelMapper = modelMapper;
	}

	/*
	 * - Rent a book to a card holder.
	 */
	@PostMapping
	public RentalDto addRental( @RequestBody @Valid RentalDto dto) {
		Book book = bookService
				.getBookById(dto.getBookId())
				.orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST, String.format("Book not found for id %d ", dto.getBookId()) ) );
		
		CardHolder cardHolder = cardHolderService
				.getCardHolderById(dto.getCardHolderId())
				.orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST, String.format("Card Holder not found for id %d ", dto.getCardHolderId()) ) );
	
		Rental newRental = service.save( new Rental(cardHolder, book, dto.getDueDate()) );
		
		return modelMapper.map(newRental, RentalDto.class);
	}

	/*
	 * - Get all books that are currently on loan.
	 */
	@GetMapping("/loaned-books")
	public List<BookDto> getAllLoanedBooks(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size){
		return service.getAllRentals(PageRequest.of(page, size)).stream().map(bookMapper()).collect(Collectors.toList());
	}

	/*
	 * - Get all overdue books that are currently on loan.
	 */
	@GetMapping("/overdue-loaned-books")
	public List<BookDto> getAllOverdueLoanedBooks(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size
	){
		LocalDate yesterday = LocalDate.now().minusDays(1);
		return service.getRentalsDueAfter( yesterday, PageRequest.of(page, size) ).stream().map(bookMapper()).collect(Collectors.toList());
	}

	/*
	 * - Return a book.
	 */
	@DeleteMapping(path = "/returnBook/{id}")
	public ResponseEntity removeRental(@PathVariable Long id) {
		return service.findRentalById(id).map( r -> {
			service.delete(r);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
	}

	/*
	 * - Update rental
	 */
	@PutMapping
	public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid RentalDto dto) {
		return service.findRentalById(id).map( r -> {
			Rental updated = modelMapper.map(dto, Rental.class);
			r.setBook(updated.getBook());
			r.setCardHolder(updated.getCardHolder());
			r.setDueDate(updated.getDueDate());
			service.save(r);
			return ResponseEntity.ok(dto.toString());
		}).orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
		
	}

	/*
	 * - Find by Id
	 */
	@GetMapping(path = "/{id}")
	public RentalDto getById(@PathVariable Long id) {
		Optional<Rental> rental = service.findRentalById(id);
		if (!rental.isPresent()) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Rental not found"
			);
		}
		return modelMapper.map(rental.get(), RentalDto.class);
	}

	private Function<Rental, BookDto> bookMapper() {
		return r -> modelMapper.map(r.getBook(), BookDto.class );
	}

}
