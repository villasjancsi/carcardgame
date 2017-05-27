# Car card game
-----
### Description
A car card game. Every car has performance, top speed and acceleration properties. You can compare
a property on your card against the opponent's card. The caller chooses the property for the round.
The player with better values wins the round. You'll have to play against the computer.

### Gameplay
* Every round starts with the player's call
* Computer responding
* Evaluate
* Repeat until no cards left in the deck

### Call a card
The player can see a table containing own cards:

ID | Name | HP | Top speed | Acceleration
-- | ---- | -- | --------- | ------------
01 | Lamborghini Aventador | 800 HP | 350 km/h | 2.9 sec
02 | Audi R8 | 611 HP | 330 km/h | 3.2 sec

To call, wait for the promt, and type:

        (Card index, property index): id[space]propertyid[enter]

where id is in the first column, and property ids are the following:

* Performance (HP): 1
* Top speed: 2
* Acceleration: 3

### Evaluation
* **Performance**: the greater the better
* **Top speed**: the greater the better
* **Acceleration**: the less the better

### Commands
#### Compile:
        mvn compile

#### Packaging:
        mvn package

#### Run the Uber JAR (in target/ folder):
        java -jar kartyajatek-1.0-jar-with-dependencies.jar

#### Site:
        mvn site

### Developing environment
* Eclipse

### License
[Apache License 2.0](LICENSE.txt)

### Contributions
Your contributions are always welcome!
