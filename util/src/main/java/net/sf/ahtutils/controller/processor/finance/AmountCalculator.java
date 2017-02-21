package net.sf.ahtutils.controller.processor.finance;

import java.util.List;

import org.jeesl.controller.processor.finance.AmountRounder;
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
	
	public static Double sum(Double...list)
	{
		Double sum = null;
		for(Double value :list)
		{
			if(value!=null)
			{
				if(sum==null){sum = value;}
				else {sum = sum + value;}
			}
		}
		if(sum!=null){sum = AmountRounder.two(sum);}
		return sum;
	}
	
	public static Double minus(Double a, Double b)
	{
		Double result = null;
		if(a!=null){result = a;}
		else{result=Double.valueOf(0);}
		
		if(b!=null){result = result - b;}
		
		if(result!=null){result = AmountRounder.two(result);}
		return result;
	}
}