package net.sf.ahtutils.controller.processor.finance;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.with.finance.EjbWithAmount;
import net.sf.ahtutils.interfaces.model.with.finance.EjbWithTotalAmount;

public class AmountCalculator
{
	final static Logger logger = LoggerFactory.getLogger(AmountCalculator.class);
	
	public static <T extends EjbWithAmount>double sum(List<T> list)
	{
		double sum = 0;
		for(T a : list)
		{
			sum=sum+a.getAmount();
		}
		return sum;
	}
	
	public static <T extends EjbWithTotalAmount>double sumTotal(List<T> list)
	{
		double sum = 0;
		for(T a : list)
		{
			sum=sum+a.getTotalAmount();
		}
		return sum;
	}
	
	
}