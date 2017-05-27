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
import static org.junit.Assert.*;
import game.*;

import org.junit.Test;

public class UnitTest {

	@Test
	public void assertCard() {
		Card card = new Card("Car", 412, 342, 2.5);
		
		assertNotNull(card);

		assertEquals("Car", card.getName());
		assertEquals(412, card.getPerformance());
		assertEquals(342, card.getTopSpeed());
		assertEquals(new Double(2.5), new Double(card.getAcceleration()));
		
		Card card2 = new Card("Car", 413, 343, 2.4);
		Card card3 = new Card("Car", 412, 342, 2.5);
		Card card4 = new Card("Car", 411, 341, 2.6);
		assertEquals(-1, card.comparePerformance(card2));
		assertEquals( 0, card.comparePerformance(card3));
		assertEquals( 1, card.comparePerformance(card4));
		assertEquals(-1, card.compareTopSpeed(card2));
		assertEquals( 0, card.compareTopSpeed(card3));
		assertEquals( 1, card.compareTopSpeed(card4));
		assertEquals(-1, card.compareAcceleration(card2));
		assertEquals( 0, card.compareAcceleration(card3));
		assertEquals( 1, card.compareAcceleration(card4));
	}
	
	@Test
	public void assertDeck() {
		Deck deck = new Deck();
		assertNotNull(new Deck());
		
		assertTrue(deck.hasNextCard());
		
		while(deck.hasNextCard()) {
			assertNotNull(deck.nextCard());
		}
		
		try {
			deck.nextCard();
			fail("Should have got IllegalStateException");
		} catch (IllegalStateException e) {	}
		
		deck = new Deck();
		deck.shuffleDeck();
		Deck deck2 = new Deck();
		deck2.shuffleDeck();
		assertNotSame(deck, deck2);

		deck.nextCard();
		deck.nextCard();
		assertEquals(6, deck.currentDeckSize());
	}
	
	@Test
	public void assertGame() {
		Game game = new Game();
		assertNotNull(game);
		
		game.maxCardsInHands = 1000;
		game.start();
		assertEquals(4, game.getPlayerHand().size());
		
		game.maxCardsInHands = 2;
		game.start();
		assertEquals(2, game.getPlayerHand().size());
		
		game.nextRound();
		assertEquals(2, game.getPlayerHand().size());
		
		game.call(0, 0);
		assertEquals(1, game.getPlayerHand().size());
		game.nextRound();
		assertEquals(2, game.getPlayerHand().size());
		
		try {
			game.computersCard();
			fail("Should have got IllegalStateException");
		} catch (IllegalStateException e) {}
		
		game.call(0, 0);
		try {
			assertNotNull(game.computersCard());
		} catch (IllegalStateException e) {
			fail("Shouldn't have got IllegalStateException");
		}
		
		game.nextRound();
		game.getPlayerHand().clear();
		game.getPlayerHand().add(new Card("I", 10000, 10000, 0.1));
		game.call(0, 0);
		try {
			assertNotNull(game.computersCard());
		} catch (IllegalStateException e) {
			fail("Shouldn't have got IllegalStateException");
		}
		game = new Game();
		game.getPlayerHand().clear();
		game.getPlayerHand().add(new Card("I", 10000, 10000, 0.1));
		assertFalse(game.call(-1, 0));
		assertFalse(game.call(game.getPlayerHand().size(), 0));
		assertFalse(game.call(0, -1));
		assertFalse(game.call(0, 3));
		assertTrue(game.call(0, 0));

		Card compcard;
		game.start();
		game.getPlayerHand().clear();
		game.getPlayerHand().add(new Card("I", 10000, 10000, 0.1));
		game.call(0, 0);
		compcard = new Card("C", 0, 0, 1000);
		assertTrue(game.roundEnd(compcard));
		compcard = new Card("C", 10001, 0, 1000);
		assertFalse(game.roundEnd(compcard));

		game.start();
		game.getPlayerHand().clear();
		game.getPlayerHand().add(new Card("I", 10000, 10000, 0.1));
		game.call(0, 1);
		compcard = new Card("C", 0, 0, 1000);
		assertTrue(game.roundEnd(compcard));
		compcard = new Card("C", 10001, 10001, 1000);
		assertFalse(game.roundEnd(compcard));

		game.start();
		game.getPlayerHand().clear();
		game.getPlayerHand().add(new Card("I", 10000, 10000, 0.1));
		game.call(0, 2);
		compcard = new Card("C", 0, 0, 1000);
		assertTrue(game.roundEnd(compcard));
		compcard = new Card("C", 10001, 10001, 0.0);
		assertFalse(game.roundEnd(compcard));
		
		assertFalse(game.isFinished());
		game.maxCardsInHands = 1000;
		game.start();
		while(!game.isFinished()) {
			game.call(0, 0);
			game.roundEnd(game.computersCard());
			game.nextRound();
		}
		assertTrue(game.isFinished());
	}
	
	@Test
	public void assertMain() {
		Card card = new Card("Lamborghini Aventador", 800, 350, 2.9);
		assertEquals("| 01 |   Lamborghini Aventador |  800 |    350 km/h |      2,9 sec |\n+------------------------------------------------------------------+", Main.cardString(card, 1));
	}

}
