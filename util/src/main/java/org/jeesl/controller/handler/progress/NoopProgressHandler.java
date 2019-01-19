package org.jeesl.controller.handler.progress;

import java.io.Serializable;

import org.jeesl.interfaces.controller.handler.JeeslProgressHandler;

public class NoopProgressHandler implements JeeslProgressHandler,Serializable
{
	private static final long serialVersionUID = 1L;

	@Override
	public void setRounds(int rounds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSteps(int steps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int round, int step) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextRound() {
		// TODO Auto-generated method stub
		
	}

}