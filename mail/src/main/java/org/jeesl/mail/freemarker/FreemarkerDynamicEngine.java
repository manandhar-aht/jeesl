package org.jeesl.mail.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.InvalidReferenceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerDynamicEngine
{
	final static Logger logger = LoggerFactory.getLogger(FreemarkerDynamicEngine.class);

	private StringTemplateLoader fmStringTemplates;
	private Configuration fmConfiguration;
	
	public FreemarkerDynamicEngine()
	{
		fmStringTemplates = new StringTemplateLoader();
		
		fmConfiguration = new Configuration(Configuration.getVersion());
		fmConfiguration.setTemplateLoader(fmStringTemplates);
		fmConfiguration.setLogTemplateExceptions(false);
		fmConfiguration.setDefaultEncoding("UTF-8");
		fmConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}
	
	public void addTemplate(String code, String template)
	{
		fmStringTemplates.removeTemplate(code);
		fmStringTemplates.putTemplate(code, template);
	}
	
	public String process(String code, Map<String,String> model) throws IOException, TemplateException, InvalidReferenceException
	{
		Template ftl = fmConfiguration.getTemplate(code);
		StringWriter sw = new StringWriter();
		ftl.process(model, sw);
		sw.flush();
		return sw.toString();
	}
}