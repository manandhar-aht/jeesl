package org.jeesl.interfaces.controller.handler;

public interface JeeslProgressHandler
{
	void reset();
	void setRounds(int rounds);
	void setSteps(int steps);
	
	void nextRound();
	void update(int round, int step);
}