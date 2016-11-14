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
import org.project.cassino.model.util.Utils.Associatives;

public class TablePanel extends JPanel implements CardPainter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TablePanel() {
		super();

		setBorder(new EmptyBorder(20, 20, 20, 20));
		setPreferredSize(new Dimension(0, (int) (120 * 3.3 + 30 + 30)));
		setBackground(new Color(3, 98, 42));
		setLayout(new FlowLayout());
	}
	
	@Override
	public void paintCards(GameController gameController) {
		removeAll();
		revalidate();
		
		int cardIndex = 0;
		
		for(Card card : gameController.getTableCards()) {
			CardButton cardButton = new CardButton(card, cardIndex, false);
			cardButton.setSelected(false);
			add(cardButton);
			cardIndex += 1;
		}
		
		repaint();
	}
	
	public boolean hasTableSelection() {
		for(CardButton cardButton : getCardButtons()) {
			if(cardButton.isSelected()) {
				return true;
			}
		}
		
		return false;
	}
	
	public Associatives<Integer> getTableSelections() {
		Associatives<Integer> tableIndices = new Associatives<Integer>();
		
		for(CardButton cardButton : getCardButtons()) {
			if(cardButton.isSelected()) {
				tableIndices.add(cardButton.getIndex());
			}
		}
		
		return tableIndices;
	}
	
	public void disableOthers() {
		List<CardButton> cardButtons = getCardButtons();
		Associatives<Integer> tableIndices = getTableSelections();

		for(int i = 0; i < cardButtons.size(); i++) {
			for(int index = 0; index < tableIndices.size(); index++) {
				if(tableIndices.validate(i)) {
					cardButtons.get(i).setSelected(false);
					cardButtons.get(i).setEnabled(false);
				}
			}
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
