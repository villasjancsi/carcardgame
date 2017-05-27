package game;

/*-
 * #%L
 * kartyajatek
 * %%
 * Copyright (C) 2017 University of Debrecen
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for representing a deck of cards. The {@code Card}s in the Deck can
 * be specified in the {@link Deck() constructor}.
 * @author Janos Villas
 *
 */
public class Deck {
	/**
	 * A list representing the {@code Card}s in the {@code Deck}.
	 */
	private List<Card> cards = new ArrayList<Card>();
	/**
	 * The last {@code Card}'s index in the {@code cards list which was dealt.
	 */
	private int lastCardIndex = 0;
	
	/**
	 * Constructor for the class. Fills up the {@code Deck} with {@code Card}s.
	 * Then shuffles the {@code Deck} using {@code shuffleDeck()} method.
	 */
	public Deck() {
		cards.add(new Card("F1", 931, 380, 3.5));
		cards.add(new Card("Audi A4", 120, 220, 5.3));
		cards.add(new Card("BMW 320d", 177, 235, 5.4));
		cards.add(new Card("Skoda Octavia RS", 240, 262, 4.2));
		cards.add(new Card("Honda Civic", 140, 200, 5.5));
		cards.add(new Card("Mercedes-Benz C220", 142, 210, 5.8));
		cards.add(new Card("Audi R8", 611, 330, 3.2));
		cards.add(new Card("Lamborghini Aventador", 800, 350, 2.9));
		shuffleDeck();
		Main.logger.debug("Deck - A deck of cards has been created.");
	}
	
	/**
	 * Checks if the {@code Deck} has {@code Card}s to be dealt.
	 * @return true if the deck has at least 1 card, false otherwise
	 */
	public boolean hasNextCard() {
		return lastCardIndex != 0;
	}
	

	/**
	 * Returns the next {@code Card} in the deck.
	 * @return the next card in the deck, if there is next card ({@link hasNextCard()}) available
	 * @throws IllegalStateException if there are no cards left in the deck
	 */
	public Card nextCard() throws IllegalStateException {
		if(!hasNextCard()) {
			Main.logger.error("Deck - nextCard() method called, but there are no more cards.");
			throw new IllegalStateException();
		}
		return cards.get(--lastCardIndex);
	}
	
	/**
	 * Collects all the {@code Card}s, and shuffles it.
	 */
	public void shuffleDeck() {
		Collections.shuffle(cards);
		lastCardIndex = cards.size();
		Main.logger.debug("Deck - The deck has been shuffled.");
	}
	
	/**
	 * Returns the number of {@code Card}s to be dealt.
	 * @return the number of cards in the deck
	 */
	public int currentDeckSize() {
		return lastCardIndex;
	}
}
