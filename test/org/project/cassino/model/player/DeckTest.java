package org.project.cassino.model.player;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.project.cassino.model.card.Deck;

public class DeckTest {
	Deck deck;

	@Before
	public void setUp() throws Exception {
		deck = new Deck();
	}

	@After
	public void tearDown() throws Exception {
		deck = null;
	}

	@Test
	public void testDealSize() {
		assertEquals(4, deck.deal(4).length);
	}

	@Test
	public void testRestack() {
		deck.deal(52);
		deck.restack();
		
		assertEquals(52, deck.size());
	}
}
