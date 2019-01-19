package org.jeesl.controller.handler.progress;

import java.io.Serializable;

import org.jeesl.interfaces.controller.handler.JeeslProgressHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogProgressHandler implements JeeslProgressHandler,Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(LogProgressHandler.class);
	
	private int currentRound,maxRounds;
	
	public LogProgressHandler()
	{
		reset();
	}
	
	@Override public void reset()
	{
		currentRound = 0;
		maxRounds = 0;
		
	}
	
	@Override public void setRounds(int rounds) {maxRounds=rounds;}

	@Override
	public void setSteps(int steps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int round, int step) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void nextRound()
	{
		currentRound++;
		logger.info(currentRound+"/"+maxRounds);
		
	}

}