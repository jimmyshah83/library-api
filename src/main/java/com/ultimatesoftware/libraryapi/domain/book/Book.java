package com.ultimatesoftware.libraryapi.domain.book;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * An entity representing a book with a single author.
 */
@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String title;

	@NotNull
	private String ISBN;

	@NotNull
	private String author;

	protected Book() {}

	public Book(@NotNull String title, @NotNull String ISBN, @NotNull String author) {
		this.title = title;
		this.ISBN = ISBN;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Book book = (Book) o;
		return getId().equals(book.getId())
				&& getTitle().equals(book.getTitle())
				&& getISBN().equals(book.getISBN())
				&& getAuthor().equals(book.getAuthor());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getTitle(), getISBN());
	}
}
