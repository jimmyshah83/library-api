package com.ultimatesoftware.libraryapi.rest.cardholder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolderService;

@RestController
@RequestMapping("/v1/card-holders")
public class CardHolderController {

	private final CardHolderService cardHolderService;
	private final ModelMapper modelMapper;

	@Autowired
	public CardHolderController(final CardHolderService cardHolderService, final ModelMapper modelMapper) {
		this.cardHolderService = cardHolderService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public List<CardHolderDto> getAll() {
		return cardHolderService.getAllCardHolders().stream()
				.map(book -> modelMapper.map(book, CardHolderDto.class))
				.collect(Collectors.toList());
	}

	@GetMapping(path = "/{id}")
	public CardHolderDto getById(@PathVariable Long id) {
		Optional<CardHolder> cardHolder = cardHolderService.getCardHolderById(id);
		if (!cardHolder.isPresent()) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Card holder not found"
			);
		}
		return modelMapper.map(cardHolder.get(), CardHolderDto.class);
	}

	@PostMapping
	public CardHolderDto addCardHolder(@RequestBody CardHolderDto cardHolderDto) {
		CardHolder newCardHolder = new CardHolder(cardHolderDto.getLastName(), cardHolderDto.getFirstName(),
				cardHolderDto.getCardNumber());
		newCardHolder = cardHolderService.save(newCardHolder);
		return modelMapper.map(newCardHolder, CardHolderDto.class);
	}

}
