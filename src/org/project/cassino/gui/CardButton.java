package org.project.cassino.gui;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import org.project.cassino.model.card.Card;

public class CardButton extends JToggleButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int index;
	private Card card;
	private BufferedImage active;
	private BufferedImage idle;
	
	public CardButton(Card card, int index, boolean isAnonymous) {
		super();
		
		this.card = card;
		this.index = index;
		
		try {
			active = ImageIO.read(
				new File("H:\\beachsand\\Cassino\\res\\" + card.getRank().toString().toLowerCase() + "_of_" + card.getSuit().toString().toLowerCase() + ".png")
			);
			idle = ImageIO.read(
				new File("H:\\beachsand\\Cassino\\res\\backside.png")
			);
			setIcon(new ImageIcon(active));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!isAnonymous) {
			setIcon(new ImageIcon(active));
		} else {
			setIcon(new ImageIcon(idle));
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public Card getCard() {
		return card;
	}
	
	public boolean equalsCard(Card comparingCard) {
		if(card.equals(comparingCard)) {
			return true;
		} else {
			return false;
		}
	}
}













