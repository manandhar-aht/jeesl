package net.sf.ahtutils.security.pwd;

import java.util.List;

import net.sf.ahtutils.xml.security.Password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;

public class PasswordAnalyser
{
	final static Logger logger = LoggerFactory.getLogger(PasswordAnalyser.class);
		
	private PasswordValidator validator;
	private Password xmlPwdAnalyse;
	
	public PasswordAnalyser(List<Rule> ruleList)
	{
		validator = new PasswordValidator(ruleList);
	}
	
	public Password analyse(String password)
	{
		xmlPwdAnalyse = new Password();
		
		PasswordData passwordData = new PasswordData(new edu.vt.middleware.password.Password(password));

		RuleResult result = validator.validate(passwordData);
		xmlPwdAnalyse.setValid(result.isValid());
		if(!result.isValid())
		{
			PasswordResultBuilder prb = new PasswordResultBuilder(result,password);
			xmlPwdAnalyse.getRule().addAll(prb.build());
			
			if(logger.isTraceEnabled())
			{
				 for (String msg : validator.getMessages(result))
				 {
					 logger.trace(msg);
				 }
			}
		}
		logger.trace("PWD is valid: "+result.isValid());
				
		return xmlPwdAnalyse;
	}
}