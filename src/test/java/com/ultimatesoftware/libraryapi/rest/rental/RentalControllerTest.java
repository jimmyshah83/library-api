package com.ultimatesoftware.libraryapi.rest.rental;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.book.BookService;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolderService;
import com.ultimatesoftware.libraryapi.domain.rental.Rental;
import com.ultimatesoftware.libraryapi.domain.rental.RentalService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RentalController.class)
public class RentalControllerTest {

	private static final MediaType JSONTYPE = MediaType.APPLICATION_JSON;

	@Autowired
	MockMvc mvc;
	
	@MockBean
	RentalService rentalService;
	
	@MockBean
	BookService bookService;
	
	@MockBean
	CardHolderService cardHolderService;
	
	static final String RESOURCE_URL= "/v1/rentals";
	
	@Test
	public void shouldRentABookToACardHolder() throws Exception {
		//given
		
		BDDMockito.given(bookService.getBookById(Mockito.anyLong())).willReturn(Optional.of(getABook()));
		BDDMockito.given(cardHolderService.getCardHolderById(Mockito.anyLong())).willReturn(Optional.of(getACardHolder()));
		BDDMockito.given(rentalService.save(Mockito.any(Rental.class))).willReturn(getARental());
		
		
		//when
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		RentalDto rentalDto = new RentalDto(1l, 1l);
		String jsonObject = mapper.writeValueAsString(	rentalDto );
		
		mvc.perform(
			post(RESOURCE_URL)
					.accept(JSONTYPE)
					.contentType(JSONTYPE)
					.content(jsonObject) )
		
		//then
			.andExpect( status().isOk() )
			.andExpect(jsonPath("$.id").value(getARental().getId()))
		;
					
	}
	
	@Test
	public void shouldReturn_400_whenBookIsNotFound() throws Exception{
		//given
		
		BDDMockito.given(bookService.getBookById(Mockito.anyLong())).willReturn(Optional.empty());
		
		//when
		mvc.perform(
			post(RESOURCE_URL)
					.accept(JSONTYPE)
					.contentType(JSONTYPE)
					.content(rentalJsonObject()) )
		
		//then
		.andExpect( status().isBadRequest() )
		.andExpect( status().reason(containsString("Book not found for id")))
		;
	}
	
	@Test
	public void shouldReturn_400_whenCardHolderIsNotFound() throws Exception{
		//given
		BDDMockito.given(bookService.getBookById(Mockito.anyLong())).willReturn(Optional.of(getABook()));
		BDDMockito.given(cardHolderService.getCardHolderById(Mockito.anyLong())).willReturn(Optional.empty());
		
		//when
		mvc.perform(
			post(RESOURCE_URL)
					.accept(JSONTYPE)
					.contentType(JSONTYPE)
					.content(rentalJsonObject()) )
		
		//then
		.andExpect( status().isBadRequest() )
		.andExpect( status().reason(containsString("Card Holder not found for id")))
		;
	}
	
	@Test
	public void shouldReturnAllLoanBooks() throws Exception{
		//given
		List<Rental> rentals =Arrays.asList(getARental());
		
		PageRequest pageable = PageRequest.of(0, 10);
		PageImpl<Rental> page = new PageImpl<Rental>(rentals, pageable, rentals.size());
		
		BDDMockito.given(rentalService.getAllRentals(pageable)).willReturn(page);
		
		//when
		mvc.perform(get(RESOURCE_URL + "/loaned-books").accept(JSONTYPE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].title").value(getABook().getTitle()) )
			.andExpect(jsonPath("$.[0].isbn").value(getABook().getISBN()) )
			.andExpect(jsonPath("$.[0].author").value(getABook().getAuthor()) )
			.andExpect(jsonPath("$", hasSize(rentals.size()))
		);
	}
	
	@Test
	public void shouldReturnAllOverdueLoanBooks() throws Exception{
		//given
		List<Rental> rentals = Arrays.asList(getARental());
		PageRequest pageable = PageRequest.of(0, 10);
				
		PageImpl<Rental> page = new PageImpl<Rental>(rentals, pageable, rentals.size());
		BDDMockito.given( rentalService.getRentalsDueAfter(LocalDate.now().minusDays(1), pageable) ).willReturn( page );
		
		//when
		mvc.perform(get(RESOURCE_URL + "/overdue-loaned-books").accept(JSONTYPE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].title").value(getABook().getTitle()) )
			.andExpect(jsonPath("$.[0].isbn").value(getABook().getISBN()) )
			.andExpect(jsonPath("$.[0].author").value(getABook().getAuthor()) )
			.andExpect(jsonPath("$", hasSize(rentals.size()))
		);
	}

	private String rentalJsonObject() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		RentalDto rentalDto = new RentalDto(1l, 1l);
		
		String jsonObject = mapper.writeValueAsString(rentalDto);
		return jsonObject;
	}

	private CardHolder getACardHolder() {
		return new CardHolder("Stark", "Aria", "001");
	}

	private Book getABook() {
		return new Book("my title", "my ISBN", "my author");
	}
	
	private Rental getARental() {
		Rental rental = new Rental(getACardHolder(), getABook(), LocalDate.now());
		rental.setId(1l);
		return rental;
	}
}
