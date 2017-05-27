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
import java.util.List;

/**
 * Class for representing the game. 
 * @author Janos Villas
 *
 */
public class Game {
	/**
	 * Instance of the {@code Deck}, which is representing the deck on the table.
	 */
	private Deck deck;
	/**
	 * A list with {@code Card}s, which is representing the cards in the player's hand.
	 */
	private List<Card> playerHand;
	/**
	 * A list with {@code Card}s, which is representing the cards in the computer's hand.
	 */
	private List<Card> computerHand;
	/**
	 * The maximum number of cards can be in a player's hand.
	 */
	public int maxCardsInHands = 3;

	/**
	 * The {@code Card} which is on the table, called by player.
	 */
	private Card cardOnTable;
	/**
	 * The property's zero-based index of the {@code Card} on the table.
	 */
	private int propertyIndex;
	
	/**
	 * Constructor for the class. Creates the Deck, and
	 * initializes the player's hand and computer's hand.
	 */
	public Game() {
		deck = new Deck();
		playerHand = new ArrayList<Card>();
		computerHand = new ArrayList<Card>();
		Main.logger.debug("Game - game has been initialized.");
	}
	
	/**
	 * Starts the game. Shuffles the {@code Deck}, and removes all
	 * the cards from player and computer. After this deals maximum
	 * {@code maxCardsInHands} {@code Card}s to each player. If the {@code Deck}
	 * has less than {@code maxCardsInHands}, then the maximum equals
	 * {@link Deck#currentDeckSize currentDeckSize()} divided by 2.
	 */
	public void start() {
		deck.shuffleDeck();
		playerHand.clear();
		computerHand.clear();
		
		if(deck.currentDeckSize()/2 < maxCardsInHands)
			Main.logger.warn("Game - Can't give maxCardsInHands cards at the beginning.");
		
		int count = Math.min(maxCardsInHands, deck.currentDeckSize()/2);
		for(int i = 0; i < count; i++) {
			playerHand.add(deck.nextCard());
		}
		for(int i = 0; i < count; i++) {
			computerHand.add(deck.nextCard());
			Main.logger.info("Game - Computer's {}. card: {}", i, computerHand.get(i).toString());
		}
		
	}
	
	/**
	 * Prepares the next round. Resets the table's content. If the {@code Deck}
	 * {@link Deck#hasNextCard has next card} deals one to each player.
	 * Every player can hold at most {@code maxCardsInHands} cards in hand.
	 */
	public void nextRound() {
		cardOnTable = null;
		if(deck.hasNextCard()) {
			if(playerHand.size() >= maxCardsInHands)
				return;
			Card card = deck.nextCard();
			playerHand.add(card);
			card = deck.nextCard();
			computerHand.add(card);
			Main.logger.info("Game - Computer's new card: {}", card.toString());
		}
		Main.logger.debug("Game - next round has been initialized.");
	}
	
	/**
	 * Computes the computer's best choice for the call, and returns it. 
	 * If the computer is able to beat the call, then selects the {@code Card}
	 * with the smallest value of those, which can beat it.
	 * @return the best choice of computer's cards
	 * @throws IllegalStateException if it is not computer's turn
	 */
	public Card computersCard() throws IllegalStateException{
		if(cardOnTable != null) {
			Card computerCard = null;
			switch(propertyIndex) {
				case 0:
					computerCard = computerHand.stream().filter(p -> p.comparePerformance(cardOnTable) > 0)
														.min((c1, c2) -> c1.comparePerformance(c2))
														.orElse(null);
					break;
				case 1:
					computerCard = computerHand.stream().filter(p -> p.compareTopSpeed(cardOnTable) > 0)
														.min((c1, c2) -> c1.compareTopSpeed(c2))
														.orElse(null);
					break;
				case 2:
					computerCard = computerHand.stream().filter(p -> p.compareAcceleration(cardOnTable) > 0)
														.min((c1, c2) -> c1.compareAcceleration(c2))
														.orElse(null);
					break;
			}
			if(computerCard == null) {
				computerHand.sort((c1, c2) -> c1.comparePerformance(c2));
				computerCard = computerHand.remove(0);
			} else
				computerHand.remove(computerCard);
			Main.logger.info("Game - Computer's choice: {}", computerCard.toString());
			return computerCard;
		}
		Main.logger.error("Game - computersCard() has been called, but it is not computer's turn!");
		throw new IllegalStateException();
	}

	/**
	 * Checks if the call is valid, removes the card from player's hand
	 * and puts that on the table. 
	 * @param cardIndex the selected card's index from the player's hand (zero-based)
	 * @param propertyIndex the selected property of the selected card (zero-based)
	 * @return true if the call is valid, false otherwise
	 */
	public boolean call(int cardIndex, int propertyIndex) {
		if(cardIndex < 0 || cardIndex >= playerHand.size())
			return false;
		if(propertyIndex < 0 || propertyIndex >= 3)
			return false;
		
		cardOnTable = playerHand.remove(cardIndex);
		this.propertyIndex = propertyIndex;
		return true;
	}
	
	/**
	 * After each player showed their {@code Card}, decide which player wins
	 * the round.
	 * @param secondCard the computer's choice
	 * @return true if player's card beats the computer's card, false otherwise
	 */
	public boolean roundEnd(Card secondCard) {
		if(propertyIndex == 0) {
			if(cardOnTable.comparePerformance(secondCard) > 0)
				return true;
			else
				return false;
		}
		if(propertyIndex == 1) {
			if(cardOnTable.compareTopSpeed(secondCard) > 0)
				return true;
			else
				return false;
		}
		if(propertyIndex == 2) {
			if(cardOnTable.compareAcceleration(secondCard) > 0)
				return true;
			else
				return false;
		}
		return true;
	}
	
	/**
	 * Returns the state of the game. The game considered finished if both
	 * player played out their {@code Card}s, and there is no more {@code Card} in the {@code Deck}.
	 * @return true if the game is finished, false otherwise
	 */
	public boolean isFinished() {
		if(playerHand.size() == 0 && computerHand.size() == 0 && !deck.hasNextCard())
			return true;
		return false;
	}
	
	/**
	 * Returns a list containing the player's {@code Card}s.
	 * @return a list containing the player's {@code Card}s
	 */
	public List<Card> getPlayerHand() {
		return playerHand;
	}
	
	/**
	 * Returns the card which has been called by player.
	 * @return the card which has been called by player
	 */
	public Card getCardOnTable() {
		return cardOnTable;
	}
}
