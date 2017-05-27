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

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Main class, which provides a console layout for the {@code Game}.
 * @author Janos Villas
 *
 */
public class Main {
	/**
	 * Logger object for logging.
	 */
	public static Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * Main method, which provides a console layout, handles IO and game.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int win = 0, lose = 0;
		Game game = new Game();
		game.start();
		while(!game.isFinished()) {
			System.out.println("+------------------------------------------------------------------+");
			System.out.println(String.format("|                         %2d. ROUND (%02d/%02d)                        |", win+lose+1, win, lose));
			System.out.println("+------------------------------------------------------------------+");
			System.out.println("| ID |           NAME          |  HP  |  TOP SPEED  | ACCELERATION |");
			System.out.println("+------------------------------------------------------------------+");
			int id = 1;
			for(Card card : game.getPlayerHand()) {
				System.out.println(cardString(card, id++));
			}
			int cardIndex = 0;
			int propertyIndex = 0;
			do {
				System.out.print("(Card index, property index): ");
				cardIndex = scanner.nextInt();
				propertyIndex = scanner.nextInt();
			} while (!game.call(--cardIndex, --propertyIndex));
			Card computersCard = game.computersCard();
			System.out.println("Computer's card: ");
			System.out.println(cardString(computersCard, 0));
			if(game.roundEnd(computersCard)) {
				win++;
			} else {
				lose++;
			}
			System.out.println(String.format("|                            ROUND %4s                            |", game.roundEnd(computersCard) ? "WON" : "LOST"));
			System.out.println("+------------------------------------------------------------------+\n\n");
			game.nextRound();
		}
		System.out.println("Game finished.");
		scanner.close();
		
	}
	
	
	/**
	 * String representation of the {@code Card}. Returns a nicely, well
	 * aligned {@code String}.
	 * @param card the card to be printed out 
	 * @param id the card's id, for identify it
	 * @return {@code String} which represents the card
	 */
	public static String cardString(Card card, int id) {
		StringBuilder sb = new StringBuilder();
		if(id == 0)
			sb.append("+------------------------------------------------------------------+\n");
		sb.append(String.format(
				"| %02d | %23s | %4d | %6d km/h | %8.1f sec |\n",
				id,
				card.getName().substring(0, Math.min(23, card.getName().length())), 
				card.getPerformance(), 
				card.getTopSpeed(), 
				card.getAcceleration()));
		sb.append("+------------------------------------------------------------------+");
		return sb.toString();
	}

}
