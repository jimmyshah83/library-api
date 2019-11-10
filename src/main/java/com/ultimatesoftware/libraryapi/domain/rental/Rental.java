package com.ultimatesoftware.libraryapi.domain.rental;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;

/**
 * An entity representing a rental of a single book by a single cardholder. Rentals are due to be returned by a
 * certain date.
 */
@Entity
@Table(name = "rental")
public class Rental {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@OneToOne
	@JoinColumn(name = "cardholder_id", nullable = false, updatable = false)
	private CardHolder cardHolder;

	@NotNull
	@OneToOne
	@JoinColumn(name = "book_id", nullable = false, updatable = false)
	private Book book;

	@NotNull
	private LocalDate dueDate;

	protected Rental() {}

	public Rental(@NotNull CardHolder cardHolder, @NotNull Book book, @NotNull LocalDate dueDate) {
		this.cardHolder = cardHolder;
		this.book = book;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CardHolder getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(CardHolder cardHolder) {
		this.cardHolder = cardHolder;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Rental rental = (Rental) o;
		return getId().equals(rental.getId()) && getCardHolder().equals(
				rental.getCardHolder()) && getBook().equals(rental.getBook()) && getDueDate().equals(
				rental.getDueDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCardHolder(), getBook(), getDueDate());
	}
}
