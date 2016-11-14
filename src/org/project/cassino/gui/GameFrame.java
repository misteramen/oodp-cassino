package org.project.cassino.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.project.cassino.control.GameController;
import org.project.cassino.control.GamePerformer;
import org.project.cassino.model.player.Computer;

public class GameFrame extends JFrame implements Runnable, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    private GameController gameController;
    private GamePerformer gamePermformer;
    private GamePanel gamePanel;
	
	public boolean running;
	public Thread thread;

	public GameFrame() {
		super("Cassino");
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(508, 604));
		
		gameController = new GameController(Computer.HARD);
		gamePermformer = new GamePerformer(gameController);
		gamePanel = new GamePanel(gameController, gamePermformer);
		
		running = false;
		thread = null;

        setFocusable(true);
        
        // Add panels here.

		gamePanel.updatePanels();
        add(gamePanel);
        
        // Stop adding here.
        
        pack();
        
        setVisible(true);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Cassino-Thread");
		thread.start();
	}

	@Override
	public void run() {
		while(running) {
			if(!gameController.isRoundOver() && gameController.isGameModeSelected()) {
				if(gameController.isPlayersHandsEmpty()) {
					gameController.deal();
					update();
					
					if(gameController.isLastRound()) {
						JOptionPane.showMessageDialog(this, "Last cards in the deck!");
						gameController.confirmLastRound();
					}
				}
				
				if(gameController.prevPlayer() == GameController.COMPUTER) {
					gamePanel.getPlayerPanel().disableCardButtons();
				} else if(gameController.prevPlayer() == GameController.PLAYER) {
					gamePanel.getPlayerPanel().enableCardButtons();
				}
				
				while(!gameController.hasNextPlayer()) {
					if(gameController.prevPlayer() == GameController.COMPUTER) {
						gamePermformer.performComputerAction();
						gameController.nextPlayer();
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} 
				
				gameController.updatePrevPlayer();
				
				update();
			} else if(gameController.getRoundsPlayed() >= 1) {
				if(gameController.isNextRound()) {
					update();
					JOptionPane.showMessageDialog(
						this, 
						"Current standings\n\n" +
						"Player score: " + gameController.getPlayerScore() + "\n\n" +
					    "Computer score: " + gameController.getComputerScore(), 
						"Next Round", 
						JOptionPane.INFORMATION_MESSAGE
					);
				} else {
					JOptionPane.showMessageDialog(
						this, 
						"Game Over!\n\n" +
						"Player score: " + gameController.getPlayerScore() + "\n\n" +
					    "Computer score: " + gameController.getComputerScore()
					);
				}
			}
		}
	}
	
	public void update() {
		gamePanel.updatePanels();
		revalidate();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
