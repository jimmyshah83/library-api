package com.ultimatesoftware.libraryapi.domain.book;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

	private final BookRepository bookRepository;

	@Autowired
	public BookService(final BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Transactional(readOnly = true)
	public Optional<Book> getBookById(Long id) {
		return bookRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<Book> getBooksByAuthor(String author) {
		return bookRepository.findBooksByAuthor(author);
	}

	@Transactional
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Transactional
	public void delete(Book book) {
		bookRepository.delete(book);
	}
}
