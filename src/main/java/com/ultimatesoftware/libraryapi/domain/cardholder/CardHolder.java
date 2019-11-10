package com.ultimatesoftware.libraryapi.domain.cardholder;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * An entity representing a library card holder who can rent books.
 */
@Entity
@Table(name = "card_holder")
public class CardHolder {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String lastName;

	@NotNull
	private String firstName;

	@NotNull
	private String cardNumber;

	protected CardHolder() {}

	public CardHolder(@NotNull String lastName, @NotNull String firstName, @NotNull String cardNumber) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.cardNumber = cardNumber;
	}

	public Long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CardHolder that = (CardHolder) o;
		return getId().equals(that.getId()) && getLastName().equals(
				that.getLastName()) && getFirstName().equals(that.getFirstName()) && getCardNumber().equals(
				that.getCardNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getLastName(), getFirstName(), getCardNumber());
	}
}
