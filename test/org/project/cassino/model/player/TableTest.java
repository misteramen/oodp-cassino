package org.project.cassino.model.player;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.project.cassino.model.card.Card;
import org.project.cassino.model.card.Rank;
import org.project.cassino.model.card.Suit;

public class TableTest {
	Table table;

	@Before
	public void setUp() throws Exception {
		table = new Table();
	}

	@After
	public void tearDown() throws Exception {
		table = null;
	}

	@Test
	public void testTake() {
		table.take(new Card(Rank.FIVE, Suit.DIAMONDS));
		table.take(new Card(Rank.KING, Suit.SPADES));
		table.take(new Card(Rank.ACE,  Suit.HEARTS));
		
		assertEquals(table.peek(0), new Card(Rank.FIVE, Suit.DIAMONDS));
		assertEquals(table.peek(1), new Card(Rank.KING, Suit.SPADES));
		assertEquals(table.peek(2), new Card(Rank.ACE,  Suit.HEARTS));
	}

	@Test
	public void testRemoveCard() {
		table.take(new Card(Rank.FIVE, Suit.DIAMONDS));
		table.take(new Card(Rank.KING, Suit.SPADES));
		table.take(new Card(Rank.ACE,  Suit.HEARTS));
		
		table.pick(0);
		
		assertEquals(table.peek(0), new Card(Rank.KING, Suit.SPADES));
		assertEquals(table.peek(1), new Card(Rank.ACE,  Suit.HEARTS));
	}
}
