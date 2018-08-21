package org.jeesl.interfaces.controller.report;

import java.util.Comparator;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslComparatorProvider <I extends EjbWithId>
{
	Comparator<I> provide(I ejb);
	
	boolean provides(Class<I> c);
	Comparator<I> provide(Class<I> c);
}
