package com.ultimatesoftware.libraryapi.rest.rental;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class RentalDto implements Serializable {

	private static final long serialVersionUID = 7674423106566951954L;

	private Long id;

	@NotNull
	private Long cardHolderId;

	@NotNull
	private Long bookId;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dueDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCardHolderId() {
		return cardHolderId;
	}

	public void setCardHolderId(Long cardHolderId) {
		this.cardHolderId = cardHolderId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((cardHolderId == null) ? 0 : cardHolderId.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RentalDto other = (RentalDto) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (cardHolderId == null) {
			if (other.cardHolderId != null)
				return false;
		} else if (!cardHolderId.equals(other.cardHolderId))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public RentalDto(@NotNull Long cardHolderId, @NotNull Long bookId) {
		this.cardHolderId = cardHolderId;
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "RentalDto [id=" + id + ", cardHolderId=" + cardHolderId + ", bookId=" + bookId + ", dueDate=" + dueDate
				+ "]";
	}

	

}
