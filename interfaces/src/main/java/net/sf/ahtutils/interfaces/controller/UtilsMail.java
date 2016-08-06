package net.sf.ahtutils.interfaces.controller;

public interface UtilsMail
{		
	String smtpActive = "net.smtp.active";
	String smtpHost = "net.smtp.host";
	
	String smtpUser = "net.smtp.auth.user";
	String smtpPwd = "net.smtp.auth.pwd";
	
	String smtpTls = "net.smtp.tls";
	
	String smtpOverride   = "net.smtp.override.active";
	String smtpOverrideTo = "net.smtp.override.to";
}