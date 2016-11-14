package org.project.cassino.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.project.cassino.control.GameController;
import org.project.cassino.control.GamePerformer;
import org.project.cassino.model.action.Action;
import org.project.cassino.model.action.IllegalActionException;
import org.project.cassino.model.util.Utils.Associatives;

public class GamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GameController gameController;
    private GamePerformer  gamePerformer;
	private ComputerPanel computerPanel;
	private TablePanel tablePanel;
	private PlayerPanel playerPanel;
	
	private List<Action> playerActions;

	public GamePanel(GameController controller, GamePerformer performer) {
		super();
		
		setBackground(new Color(3, 98, 42));
		
		gameController = controller;
		gamePerformer = performer;
		playerActions = new ArrayList<Action>();
		
		/*
		 * The Gaming panel
		 * 
		 */
		
		JPanel gamingPanel = new JPanel();
		gamingPanel.setLayout(new BorderLayout());

		computerPanel = new ComputerPanel();
		tablePanel    = new TablePanel();
		playerPanel   = new PlayerPanel();
		
		tablePanel.setLayout(new FlowLayout());
		
		gamingPanel.add(computerPanel, BorderLayout.NORTH);
		gamingPanel.add(   tablePanel, BorderLayout.CENTER);
		gamingPanel.add(  playerPanel, BorderLayout.SOUTH);
		
		/*
		 * The Control panel
		 * 
		 */
		
		JPanel controlPanel = new JPanel();
				
		controlPanel.setLayout(new GridLayout(1, 3));
		
		JButton newGameButton = new JButton("New Game");
		JButton captureButton = new JButton("Capture");
		JButton pairButton    = new JButton("Pair");
		JButton combineButton = new JButton("Combine");
		JButton trailButton   = new JButton("Trail");
		JButton cancelButton  = new JButton("Cancel");

		captureButton.setEnabled(false);
		cancelButton.setEnabled(false);
		
		newGameButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gameController.gameReset();
					
					if(requestNewGameMode()) {
						gameController.deal();
						updatePanels();
					}
				}
			}
		);
		captureButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					captureButton.setEnabled(false);
					trailButton.setEnabled(true);
					cancelButton.setEnabled(false);
					
					if(playerActions.size() > 0) {
						gamePerformer.performActions(playerActions);
						playerActions.clear();
						gameController.nextPlayer();
					}
				}
			}
		);
		pairButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					Integer playerSelection = playerPanel.getPlayerSelection();
					Associatives<Integer> tableSelections = tablePanel.getTableSelections();
					
					if(isUnqualifiedPairAction()) {
						return;
					}
					
					Action action = null;
					
					try {
						action = gamePerformer.requestPairAction(playerSelection, tableSelections.get(0));

						captureButton.setEnabled(true);
						trailButton.setEnabled(false);
						cancelButton.setEnabled(true);
						
						if(!playerActions.contains(action)) {
							playerActions.add(action);
						} else {
							return;
						}
						
						playerPanel.disableOthers();
						tablePanel.disableOthers();
					} catch(IllegalActionException e) {
						// Under construction!
						
						return;
					}
				}
			}
		);
		combineButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					Integer playerSelection = playerPanel.getPlayerSelection();
					Associatives<Integer> tableSelections = tablePanel.getTableSelections();
					
					if(isUnqualifiedCombineAction()) {
						return;
					}
					
					Action action = null;
					
					try {
						action = gamePerformer.requestCombineAction(playerSelection, tableSelections);

						captureButton.setEnabled(true);
						trailButton.setEnabled(false);
						cancelButton.setEnabled(true);
						
						if(!playerActions.contains(action)) {
							playerActions.add(action);
						} else {
							return;
						}
						
						playerPanel.disableOthers();
						tablePanel.disableOthers();
					} catch(IllegalActionException e) {
						// Under construction!
						
						return;
					}
				}
			}
		);
		trailButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					if(isUnqualifiedTrailAction()) {
						return;
					}
					
					gamePerformer.performTrailAction(playerPanel.getPlayerSelection());
					gameController.nextPlayer();
				}
			}
		);
		cancelButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					captureButton.setEnabled(false);
					trailButton.setEnabled(true);
					cancelButton.setEnabled(false);
					
					for(CardButton cardButton : playerPanel.getCardButtons()) {
						cardButton.setEnabled(true);
					}
					
					for(CardButton cardButton : tablePanel.getCardButtons()) {
						cardButton.setEnabled(true);
					}
					
					playerActions.clear();
				}
			}
		);
		
		controlPanel.add(newGameButton);
		controlPanel.add(captureButton);
		controlPanel.add(combineButton);
		controlPanel.add(pairButton);
		controlPanel.add(trailButton);
		controlPanel.add(cancelButton);
		
		/*
		 * Adding all to the Game Panel
		 * 
		 */
		
		setLayout(new BorderLayout());
		
		add(gamingPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
	}
	
	public boolean requestNewGameMode() {
		String[] gameModes = {
			"Quick game", 
			"Predetermined number of decks", 
			"Race to number of points"
		};
		
		int mode = JOptionPane.showOptionDialog(
			getParent(), 
			"Select game a mode.", 
			"Game Mode", 
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.QUESTION_MESSAGE, 
			null, 
			gameModes,
			new Integer(-1)
		);
		
		if(mode == 0) {
			gameController.setGameMode(GameController.GAME_MODE_ROUNDS, 1);
			
			return true;
		} else if(mode == 1) {
			boolean validInput = false;
			int roundLimit = 0;
			String input = null;
			String errorMessage = "";
			
			while(!validInput) {
				input = JOptionPane.showInputDialog("Round limit" + errorMessage + ":");
				
				try {
					roundLimit = (Integer) new Integer(input);
					gameController.setGameMode(GameController.GAME_MODE_ROUNDS, roundLimit);
					validInput = true;
				} catch(NumberFormatException e) {
					errorMessage = " [Invalid input! These are valid inputs 0-9 and any variation of these numbers.] ";
				}
			}
			
			return true;
		} else if(mode == 2) {
			boolean validInput = false;
			int pointLimit = 0;
			String input = null;
			String errorMessage = "";
			
			while(!validInput) {
				input = JOptionPane.showInputDialog("Point limit" + errorMessage + ":");
				
				try {
					pointLimit = (Integer) new Integer(input);
					gameController.setGameMode(GameController.GAME_MODE_POINTS, pointLimit);
					validInput = true;
				} catch(NumberFormatException e) {
					errorMessage = " [Invalid input! These are valid inputs 0-9 and any variation of these numbers.] ";
				}
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isUnqualifiedPairAction() {
		if(!gameController.isPlayerTurn() || !playerPanel.hasPlayerSelection() || !tablePanel.hasTableSelection() || tablePanel.getTableSelections().size() != 1) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isUnqualifiedCombineAction() {
		if(!gameController.isPlayerTurn() || !playerPanel.hasPlayerSelection() || !tablePanel.hasTableSelection() || tablePanel.getTableSelections().size() <= 1) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isUnqualifiedTrailAction() {
		if(!gameController.isPlayerTurn() || !playerPanel.hasPlayerSelection()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updatePanels() {
		computerPanel.paintCards(gameController);
		tablePanel.paintCards(gameController);
		playerPanel.paintCards(gameController);
	}
	
	public PlayerPanel getPlayerPanel() {
		return playerPanel;
	}
}
