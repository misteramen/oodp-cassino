package org.project.cassino.model.player;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.project.cassino.model.card.Card;
import org.project.cassino.model.card.Rank;
import org.project.cassino.model.card.Suit;

public class PlayerTest {
	Player player;

	@Before
	public void setUp() throws Exception {
		player = new Player("John");
	}

	@After
	public void tearDown() throws Exception {
		player = null;
	}

	@Test
	public void testCardDeal() {
		player.take(new Card(Rank.FIVE, Suit.DIAMONDS));
		player.take(new Card(Rank.KING, Suit.SPADES));
		player.take(new Card(Rank.ACE,  Suit.HEARTS));
		
		assertEquals(player.peek(0), new Card(Rank.FIVE, Suit.DIAMONDS));
		assertEquals(player.peek(1), new Card(Rank.KING, Suit.SPADES));
		assertEquals(player.peek(2), new Card(Rank.ACE,  Suit.HEARTS));
	}
	
	@Test
	public void testScore() {
		player.addPoint(5);
		player.addPoint(4);
		player.addPoint(-1);
		player.addPoint(1);
		
		assertEquals(9, player.getScore());
	}
	
	@Test
	public void testCapturedCards() {
		player.capture(new Card(Rank.FIVE, Suit.DIAMONDS));
		player.capture(new Card(Rank.KING, Suit.SPADES));
		player.capture(new Card(Rank.ACE,  Suit.HEARTS));
		
		assertEquals(new Card(Rank.FIVE, Suit.DIAMONDS), player.showCaptured().get(0));
		assertEquals(new Card(Rank.KING, Suit.SPADES),   player.showCaptured().get(1));
		assertEquals(new Card(Rank.ACE,  Suit.HEARTS),   player.showCaptured().get(2));
	}
}
