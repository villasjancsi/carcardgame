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

/**
 * 
 * Class for representing a card. Each card has 4 properties. These are the followings:
 * name, performance, top speed and acceleration. 
 * @author Janos Villas
 *
 */
public class Card {
	/**
	 * The name of the car, which is represented by this object.
	 */
	private String name;
	/**
	 * The performance of the car, which is represented by this object.
	 * Value in horse power.
	 */
	private int performance;
	/**
	 * The top speed of the car, which is represented by this object.
	 * Value in kilometer/hour.
	 */
	private int topSpeed;
	/**
	 * The acceleration of the car, which is represented by this object.
	 * Value in seconds (0-100 km/h).
	 */
	private double acceleration;
	
	/**
	 * Constructor for the class.
	 * @param name car's name
	 * @param performance car's performance in horse power
	 * @param topSpeed car's top speed in kilometer/hour
	 * @param acceleration car's acceleration from 0 km/h to 100 km/h in seconds
	 */
	public Card(String name, int performance, int topSpeed, double acceleration) {
		this.name = name;
		this.performance = performance;
		this.topSpeed = topSpeed;
		this.acceleration = acceleration;
		Main.logger.debug("Card - New card object: " + this.toString());
	}
	
	/**
	 * Returns the name of the car.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the performance of the car.
	 * @return the performance
	 */
	public int getPerformance() {
		return performance;
	}
	
	/**
	 * Returns the top speed of the car.
	 * @return the top speed
	 */
	public int getTopSpeed() {
		return topSpeed;
	}
	
	/**
	 * Returns the acceleration of the car.
	 * @return the acceleration
	 */
	public double getAcceleration() {
		return acceleration;
	}
	
	/**
	 * Compares this object with the specified {@code Card} by performance.
	 * @param card the card to be compared.
	 * @return the value 0 if object's performance equals to the other's performance; 
	 * a value less than 0 if object's performance less than the other's performance; 
	 * and a value greater than 0 if object's performance greater than other's performance
	 */
	public int comparePerformance(Card card) {
		return Integer.compare(performance, card.getPerformance());
	}

	/**
	 * Compares this object with the specified {@code Card} by top speed. 
	 * @param card the card to be compared.
	 * @return the value 0 if object's top speed equals to the other's top speed; 
	 * a value less than 0 if object's top speed less than the other's top speed; 
	 * and a value greater than 0 if object's top speed greater than other's top speed
	 */
	public int compareTopSpeed(Card card) {
		return Integer.compare(topSpeed, card.getTopSpeed());
	}

	/**
	 * Compares this object with the specified {@code Card} by acceleration.
	 * @param card the card to be compared.
	 * @return the value 0 if object's acceleration equals to the other's acceleration; 
	 * a value less than 0 if object's acceleration less than the other's acceleration; 
	 * and a value greater than 0 if object's acceleration greater than other's acceleration
	 */
	public int compareAcceleration(Card card) {
		return Double.compare(card.getAcceleration(), acceleration);
	}
	
	/**
	 * String representation of the object.
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name);
		stringBuilder.append(" : [ performance=");
		stringBuilder.append(performance);
		stringBuilder.append(" HP; top speed=");
		stringBuilder.append(topSpeed);
		stringBuilder.append(" km/h; acceleration (0-100)=");
		stringBuilder.append(acceleration);
		stringBuilder.append(" sec ]");
		return stringBuilder.toString();
	}
	
	
}
