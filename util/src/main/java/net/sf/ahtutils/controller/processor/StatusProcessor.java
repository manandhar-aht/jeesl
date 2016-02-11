package net.sf.ahtutils.controller.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.msgbundle.TranslationFactory;
import net.sf.ahtutils.xml.status.Status;

public class StatusProcessor
{
	final static Logger logger = LoggerFactory.getLogger(TranslationFactory.class);
	
	public static List<Status> filterForParentCode(Status parent, List<Status> list)
	{
		List<Status> result = new ArrayList<Status>();
		for(Status s : list)
		{
			if(s.getParent().getCode().equals(parent.getCode())){result.add(s);}
		}
		return result;
	}
}