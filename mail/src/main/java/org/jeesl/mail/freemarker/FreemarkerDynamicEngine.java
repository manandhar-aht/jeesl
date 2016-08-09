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

public class FreemarkerDynamicEngine
{
	final static Logger logger = LoggerFactory.getLogger(FreemarkerDynamicEngine.class);

	private StringTemplateLoader fmStringTemplates = new StringTemplateLoader();
	private Configuration fmConfiguration;
	
	public FreemarkerDynamicEngine()
	{
		fmConfiguration = new Configuration();
		fmStringTemplates = new StringTemplateLoader();
		fmConfiguration.setTemplateLoader(fmStringTemplates);
	}
	
	public void addTemplate(String code, String template)
	{
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