package org.project.cassino.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.project.cassino.model.action.Action;
import org.project.cassino.model.action.ActionQuery;
import org.project.cassino.model.action.ActionType;
import org.project.cassino.model.card.Card;
import org.project.cassino.model.card.Deck;
import org.project.cassino.model.card.Rank;
import org.project.cassino.model.card.Shuffle;
import org.project.cassino.model.card.Suit;

import org.project.cassino.model.player.Computer;
import org.project.cassino.model.player.Player;
import org.project.cassino.model.player.Table;

import org.project.cassino.model.util.Utils;
import org.project.cassino.model.util.Utils.Associatives;

public class GameController {
	private boolean roundOver;
	private boolean lastRoundConfirmed;
	
	private int prevPlayer;
	private int nextPlayer;
	
	private int roundCounter;
	private int gameMode;
	private int gameModeValue;

	private Deck deck;
	private Table table;
	private Player player;
	private Computer computer;
	private List<Player> players;
	
	public static final int PLAYER = 0;
	public static final int COMPUTER = 1;
	
	public static final int GAME_MODE_ROUNDS = 0;
	public static final int GAME_MODE_POINTS = 1;
	
	public GameController(int computerDifficulty) {
		deck = new Deck();
		table= new Table();
		players = new ArrayList<Player>();

		player   = new Player("John");
		computer = new Computer(computerDifficulty);

		players.add(player);
		players.add(computer);
		
		gameReset();
	}
	
	public void gameReset() {
		gameMode = -1;
		gameModeValue = 0;
		roundCounter = 0;
		prevPlayer = 0;
		nextPlayer = 0;
		roundOver = false;
		lastRoundConfirmed = false;
		
		table.reset();
		
		player.reset();
		computer.reset();
		
		deck.restack();
		
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.RANDOM);
		deck = deck.shuffle(Shuffle.RANDOM);
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.RANDOM);
		deck = deck.shuffle(Shuffle.PILE);
		deck = deck.shuffle(Shuffle.PILE);
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.RANDOM);
		deck = deck.shuffle(Shuffle.PILE);
		deck = deck.shuffle(Shuffle.PILE);
	}
	
	public void roundReset() {
		prevPlayer = 0;
		nextPlayer = 0;
		roundOver = false;
		resetLastRoundConfirmed();
		
		deck.restack();
		table.reset();
		
		player.nextRound();
		computer.nextRound();
		
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.RANDOM);
		deck = deck.shuffle(Shuffle.RANDOM);
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.RANDOM);
		deck = deck.shuffle(Shuffle.PILE);
		deck = deck.shuffle(Shuffle.PILE);
		deck = deck.shuffle(Shuffle.FISHER_YATES);
		deck = deck.shuffle(Shuffle.RANDOM);
		deck = deck.shuffle(Shuffle.PILE);
		deck = deck.shuffle(Shuffle.PILE);
	}
	
	public boolean isNextRound() {
		if(gameMode == GameController.GAME_MODE_ROUNDS) {
			if(roundCounter == gameModeValue) {
				roundCounter = 0;
				return false;
			} else {
				roundReset();
				deal();
				return true;
			}
		} else if(gameMode == GameController.GAME_MODE_POINTS) {
			for(Player player : players) {
				if(player.getScore() >= gameModeValue) {
					roundCounter = 0;
					return false;
				}
			}
			
			roundReset();
			deal();
			return true;
		} else {
			roundReset();
			deal();
			
			return true;
		}
	}
	
	public void declareNewRound() {
		roundOver = false;
	}
	
	public void deal() {
		if(deck.size() == Deck.CAPACITY) {
			for(int i = 0; i < 2; i++) {
				for(Player player : players) {
					player.take(deck.deal(2));
				}
				
				table.take(deck.deal(2));
			}
		} else {
			if(deck.size() >= 8) {
				for(int i = 0; i < 2; i++) {
					for(Player player : players) {
						player.take(deck.deal(2));
					}
				}
			}
		}
	}
	
	public boolean isPlayersHandsEmpty() {
		boolean[] statusOfEntityHands = new boolean[players.size()];
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).hasEmptyHand()) {
				statusOfEntityHands[i] = true;
			} else {
				statusOfEntityHands[i] = false;
			}
		}
		
		return Utils.trueAll(statusOfEntityHands);
	}
	
	public int prevPlayer() {
		return prevPlayer;
	}
	
	public void nextPlayer() {
		if(prevPlayer < (players.size() - 1)) {
			nextPlayer += 1;
		} else if(prevPlayer == (players.size() - 1)) {
			nextPlayer = 0;
			updateNextRound();
		}
	}
	
	public void updateNextRound() {
		if(deck.isEmpty() && isPlayersHandsEmpty()) {
			roundOver = true;
			roundCounter += 1;
			
			countScores();
			
			System.out.println("  Player score: " + player.getScore());
			System.out.println("Computer score: " + computer.getScore());
		} else {
			System.out.println("  Player score: " + player.getScore());
			System.out.println("Computer score: " + computer.getScore());
		}
	}
	
	public boolean isRoundOver() {
		return roundOver;
	}
	
	public void countScores() {
		int playerSpadesCounter = 0;
		
		if(player.getSweeps() > 0) {
			System.out.println("Player sweeps: " + player.getSweeps() + "p");
		}
		
		if(computer.getSweeps() > 0) {
			System.out.println("Computer sweeps: " + computer.getSweeps() + "p");
		}
		
		for(Card card : player.showCaptured()) {
			if(card.equalsRank(new Card(Rank.ACE, null))) {
				player.addPoint(1);

				System.out.println("Player 1p ACE");
			}
			
			if(card.equals(new Card(Rank.DEUCE, Suit.SPADES))) {
				player.addPoint(1);
				System.out.println("Player 1p Deuce of Spades");
			}
			
			if(card.equals(new Card(Rank.TEN, Suit.DIAMONDS))) {
				player.addPoint(2);
				System.out.println("Player 2p Ten of Diamonds");
			}
			
			if(card.equalsSuit(new Card(null, Suit.SPADES))) {
				playerSpadesCounter += 1;
			}
		}

		int computerSpadesCounter = 0;
		
		if(computer.getSweeps() > 0) {
			System.out.println("Computer sweeps: " + computer.getSweeps() + "p");
		}
		
		for(Card card : computer.showCaptured()) {
			if(card.equalsRank(new Card(Rank.ACE, null))) {
				computer.addPoint(1);
				System.out.println("Computer 1p ACE");
			}
			
			if(card.equals(new Card(Rank.DEUCE, Suit.SPADES))) {
				computer.addPoint(1);
				System.out.println("Computer 1p Deuce of Spades");
			}
			
			if(card.equals(new Card(Rank.TEN, Suit.DIAMONDS))) {
				computer.addPoint(2);
				System.out.println("Computer 2p Ten of Diamonds");
			}
			
			if(card.equalsSuit(new Card(null, Suit.SPADES))) {
				computerSpadesCounter += 1;
			}
		}
		
		if(playerSpadesCounter > computerSpadesCounter) {
			player.addPoint(1);
			System.out.println("Player 1p most Spades");
		} else if(computerSpadesCounter > playerSpadesCounter) {
			computer.addPoint(1);
			System.out.println("Computer 1p most Spades");
		} else {
			System.out.println("Player and Computer 1p Spades");
			player.addPoint(1);
			computer.addPoint(1);
		}
		
		if(player.showCaptured().size() > computer.showCaptured().size()) {
			player.addPoint(1);
			System.out.println("Player 1p most cards");
		} else if(computer.showCaptured().size() > player.showCaptured().size()) {
			computer.addPoint(1);
			System.out.println("Computer 1p most cards");
		} else {
			System.out.println("Player and Computer 1p most cards");
			player.addPoint(1);
			computer.addPoint(1);
		}
		
		if((playerSpadesCounter > computerSpadesCounter) && (player.showCaptured().size() > computer.showCaptured().size())) {
			player.addPoint(1);
			System.out.println("Player 1p most cards and spades");
		} else if((computerSpadesCounter > playerSpadesCounter) && (computer.showCaptured().size() > player.showCaptured().size())) {
			computer.addPoint(1);
			System.out.println("Computer 1p most cards and spades");
		} else if((playerSpadesCounter == computerSpadesCounter) && (player.showCaptured().size() == computer.showCaptured().size())) {
			player.addPoint(1);
			computer.addPoint(1);
			System.out.println("Player and Computer 1p most cards and spades");
		}
		
		player.resetSweeps();
		computer.resetSweeps();
		resetLastRoundConfirmed();
	}
	
	public boolean hasNextPlayer() {
		if(prevPlayer != nextPlayer) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updatePrevPlayer() {
		prevPlayer = nextPlayer;
	}
	
	public boolean isPlayerTurn() {
		if(player.equals(players.get(prevPlayer))) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isLastRound() {
		if(deck.size() == 0 && !lastRoundConfirmed) {
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized boolean isGameModeSelected() {
		if(gameMode == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	public void confirmLastRound() {
		lastRoundConfirmed = true;
	}
	
	public void resetLastRoundConfirmed() {
		lastRoundConfirmed = false;
	}
	
	public int getRoundsPlayed() {
		return roundCounter;
	}
	
	public void setGameMode(int gameMode, int gameModeValue) {
		this.gameMode = gameMode;
		this.gameModeValue = gameModeValue;
	}
	
	public int getDeckSize() {
		return deck.size();
	}
	
	public String getPlayerName() {
		return player.getName();
	}
	
	public String getComputerName() {
		return computer.getName();
	}
	
	public int getPlayerScore() {
		return player.getScore();
	}
	
	public int getPlayerCardHandSize() {
		return player.showHand().size();
	}
	
	public int getComputerScore() {
		return computer.getScore();
	}
	
	public int getGameMode() {
		return gameMode;
	}
	
	Player getPlayer() {
		return player;
	}
	
	Player getComputer() {
		return computer;
	}
	
	Table getTable() {
		return table;
	}
	
	public synchronized List<Card> getPlayerCards() {
		return player.showHand();
	}
	
	public synchronized List<Card> getComputerCards() {
		return computer.showHand();
	}
	
	public synchronized List<Card> getTableCards() {
		return table.getCards();
	}
}











