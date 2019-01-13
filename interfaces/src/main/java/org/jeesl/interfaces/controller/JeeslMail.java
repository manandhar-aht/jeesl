package org.jeesl.interfaces.controller;

import org.jeesl.model.xml.system.io.mail.EmailAddress;

public interface JeeslMail
{	
	void overrideRecipients(EmailAddress email);
	public void spool();
}