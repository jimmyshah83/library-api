package com.ultimatesoftware.libraryapi.domain.cardholder;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardHolderService {

	private final CardHolderRepository cardHolderRepository;

	@Autowired
	public CardHolderService(final CardHolderRepository cardHolderRepository) {
		this.cardHolderRepository = cardHolderRepository;
	}

	@Transactional(readOnly = true)
	public Optional<CardHolder> getCardHolderById(Long id) {
		return cardHolderRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<CardHolder> getAllCardHolders() {
		return cardHolderRepository.findAll();
	}

	@Transactional
	public CardHolder save(CardHolder cardHolder) {
		return cardHolderRepository.save(cardHolder);
	}

	@Transactional
	public void delete(CardHolder cardHolder) {
		cardHolderRepository.delete(cardHolder);
	}

}
