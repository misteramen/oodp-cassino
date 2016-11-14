package org.project.cassino.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.project.cassino.control.GameController;
import org.project.cassino.model.card.Card;

public class ComputerPanel extends JPanel implements CardPainter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NoneSelectedButtonGroup buttonGroup;

	public ComputerPanel() {
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
		for(Card card : gameController.getComputerCards()) {
			CardButton cardButton = new CardButton(card, cardIndex, true);
			cardButton.setSelected(false);
			
			add(cardButton);
			buttonGroup.add(cardButton);
			
			cardIndex += 1;
		}
		
		repaint();
	}
}
