package org.jeesl.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;

public class JeeslWorkflowException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	private final List<JeeslConstraint<?,?,?,?,?,?,?,?>> constraints;
 
	public JeeslWorkflowException(String s) 
	{ 
		super(s);
		constraints = new ArrayList<>();
	} 
}
