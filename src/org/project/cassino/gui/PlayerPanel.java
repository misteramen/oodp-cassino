package org.project.cassino.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.project.cassino.control.GameController;
import org.project.cassino.model.card.Card;

public class PlayerPanel extends JPanel implements CardPainter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NoneSelectedButtonGroup buttonGroup;

	public PlayerPanel() {
		super();
		
		buttonGroup = new NoneSelectedButtonGroup();
		
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setPreferredSize(new Dimension(0, 120 + 30 + 30));
		setBackground(new Color(35, 37, 39));
		setLayout(new FlowLayout());
	}
	
	@Override
	public void paintCards(GameController gameController) {
		removeAll();
		revalidate();
		
		int cardIndex = 0;
		for(Card card : gameController.getPlayerCards()) {
			CardButton cardButton = new CardButton(card, cardIndex, false);
			cardButton.setSelected(false);
			
			add(cardButton);
			buttonGroup.add(cardButton);
			
			cardIndex += 1;
		}
		
		repaint();
	}
	
	public boolean hasPlayerSelection() {
		for(CardButton cardButton : getCardButtons()) {
			if(cardButton.isSelected()) {
				return true;
			}
		}
		
		return false;
	}
	
	public void disableOthers() {
		for(CardButton cardButton : getCardButtons()) {
			if(!(cardButton.getIndex() == getPlayerSelection())) {
				cardButton.setEnabled(false);
			}
		}
	}
	
	public int getPlayerSelection() {
		for(CardButton cardButton : getCardButtons()) {
			if(cardButton.isSelected()) {
				return cardButton.getIndex();
			}
		}
		
		return -1;
	}
	
	public void disableCardButtons() {
		for(CardButton cardButton : getCardButtons()) {
			cardButton.setEnabled(false);
		}
	}
	
	public void enableCardButtons() {
		for(CardButton cardButton : getCardButtons()) {
			cardButton.setEnabled(true);
		}
	}
	
	public List<CardButton> getCardButtons() {
		List<CardButton> cardButtons = new ArrayList<CardButton>();
		
		for(Component component : getComponents()) {
			if(component instanceof CardButton) {
				cardButtons.add((CardButton) component);
			}
		}
		
		return cardButtons;
	}
}
