package org.project.cassino.model.player;

import org.project.cassino.model.action.Action;
import org.project.cassino.model.player.strategy.EasyStrategy;
import org.project.cassino.model.player.strategy.HardStrategy;
import org.project.cassino.model.player.strategy.NormalStrategy;
import org.project.cassino.model.player.strategy.Strategy;

public class Computer extends Player {
	private Strategy strategy;
	
	public static final int   EASY = 0x01;
	public static final int NORMAL = 0x02;
	public static final int   HARD = 0x03;
	
	public Computer(int selectedStrategy) {
		super("Computer");
		
		switch(selectedStrategy) {
			case NORMAL:
				strategy = new NormalStrategy();
				break;
			case HARD:
				strategy = new HardStrategy();
				break;
			case EASY:
			default:
				strategy = new EasyStrategy();
				break;
		}
	}
	
	@Override
	public Action performAction(Table table, Action action) {
		action = strategy.selectAction(this, table);
		Action.perform(action, this, table);
		
		return action;
	}
	
	public Strategy getStrategy() {
		return strategy;
	}
}
