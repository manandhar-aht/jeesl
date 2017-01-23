package net.sf.ahtutils.security.pwd;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.xml.security.Rule;

import org.jeesl.factory.xml.system.security.XmlRuleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.RuleResultDetail;

public class PasswordResultBuilder
{
	final static Logger logger = LoggerFactory.getLogger(PasswordResultBuilder.class);
	
	private RuleResult result;
	private List<Rule> list;
	private String password;
	
	public PasswordResultBuilder(RuleResult result,String password)
	{
		this.result=result;
		this.password=password;
		list = new ArrayList<Rule>();
	}
	
	public List<Rule> build()
	{
		
		for(RuleResultDetail rrd : result.getDetails())
		{
			logger.debug(rrd.getErrorCode());
			if(rrd.getErrorCode().equals("INSUFFICIENT_CHARACTERS")){insufficientCharacter(rrd);}
			else if(rrd.getErrorCode().equals("TOO_SHORT")){length(rrd);}
			else if(rrd.getErrorCode().equals("ILLEGAL_MATCH")){illegalMatch(rrd);}
			else if(rrd.getErrorCode().equals("ILLEGAL_SEQUENCE")){illegalSequence(rrd);}
			else{logger.error("NYI: "+rrd.getErrorCode());}
		}
		return list;
	}
	
	public void length(RuleResultDetail rrd)
	{
		Rule xml = XmlRuleFactory.build(false,
				"LENGTH",
				null,
				new Integer(rrd.getParameters().get("minimumLength").toString()),
				new Integer(rrd.getParameters().get("maximumLength").toString()),
				password.length());
		list.add(xml);
	}
	
	
	private void insufficientCharacter(RuleResultDetail rrd)
	{
		Rule xml = XmlRuleFactory.build(false,
				"CHARACTERS",
				rrd.getParameters().get("characterType").toString(),
				new Integer(rrd.getParameters().get("minimumRequired").toString()),
				null,
				new Integer(rrd.getParameters().get("validCharacterCount").toString()));
		list.add(xml);
	}
	
	private void illegalMatch(RuleResultDetail rrd)
	{
		Rule xml = XmlRuleFactory.build(false,
				"MATCH",
				rrd.getParameters().get("match").toString(),
				null,
				null,
				null);
		list.add(xml);
	}
	
	private void illegalSequence(RuleResultDetail rrd)
	{
		Rule xml = XmlRuleFactory.build(false,
				"SEQUENCE",
				rrd.getParameters().get("sequence").toString(),
				null,
				null,
				null);
		list.add(xml);
	}
}