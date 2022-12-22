
# Multi-threaded Card Playing Simulation
In this game, players and decks of cards are organized in a ring topology. Each player holds a hand of 4 cards and each deck contains a number of cards. The goal of the game is for a player to have 4 cards of the same value in their hand. If this is achieved at the start of the game, the player wins and the game ends. If the game is not won immediately, players take turns picking a card from the top of the deck to their left and discarding one to the bottom of the deck to their right. The game continues until a player declares that they have won by having 4 cards of the same value.

## 👨🏽‍🎓 Author
- [@joshfinney](https://github.com/joshfinney)
- [@jarljreg](https://github.com/JarlJreg)
- Version: 3.2

## 💻 Requirements
- Java 11 or higher
- An IDE such as Eclipse or IntelliJ IDEA (optional)

## 📰 Configuration
1. Clone the repository to your local machine: ```git clone https://github.com/joshfinney/Card-Playing-Simulation```  .
2. Open the project in your IDE and make sure that it builds successfully.
3. To start a new game, run the Main class. You will be prompted to enter the number of players and the location of the card pack file.
4. Follow the on-screen instructions to play the game.

## 🎨 Customisation
#### Changing the number of points needed to win
Modify the gameEnded variable in the playTurn method of the Player class.

Here is an example of how you could change the game to end when a player reaches 10 points instead of 5:
```javascript
if (score >= 10) {
    gameEnded = true;
}
```

#### Customizing the card pack
Create a text file with a list of integers, one per line, ranging from 1 to 8. Save the file to the src directory, and specify its location when prompted at the start of the game. The game will validate the pack to ensure it contains the correct number of cards.

Here is an example of a card pack file with 8 cards:
```
2
3
6
1
2
5
3
1
```

## 🌲 Project Structure
```
Card-Playing-Simulation/
├─ src/
│  ├─ AbstractCardOwner.java
│  ├─ Card.java
│  ├─ Deck.java
│  ├─ Main.java
│  ├─ Player.java
│  ├─ Pack.txt
├─ test/
│  ├─ DeckTest.java
│  ├─ MainTest.java
│  ├─ PlayerTest.java
├─ out/
│  ├─ logs/
├─ LICENSE
├─ README.md
```

## 📚 Additional resources
- [Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/) - A tutorial on the concepts of concurrency in Java.
- [Java Streams](https://docs.oracle.com/javase/tutorial/collections/streams/) - A tutorial on using Java streams to perform functional-style operations on collections.

## 📝 License
Copyright © 2022 [Joshua](https://github.com/joshfinney).

This project is [MIT](https://choosealicense.com/licenses/mit/) licensed.
