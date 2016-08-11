package org.jeesl.mail.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.jeesl.factory.txt.system.io.TxtIoTemplateFactory;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.InvalidReferenceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class FreemarkerIoTemplateEngine<L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
										DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
										TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
{
	final static Logger logger = LoggerFactory.getLogger(FreemarkerIoTemplateEngine.class);

	private StringTemplateLoader fmStringTemplates;
	private Configuration fmConfiguration;
	
	public FreemarkerIoTemplateEngine()
	{
		fmConfiguration = new Configuration(Configuration.getVersion());
		fmStringTemplates = new StringTemplateLoader();
		fmConfiguration.setTemplateLoader(fmStringTemplates);
		fmConfiguration.setLogTemplateExceptions(false);
		fmConfiguration.setDefaultEncoding("UTF-8");
		fmConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}
	
	public void addTemplate(TEMPLATE template)
	{
		for(DEFINITION definition : template.getDefinitions())
		{
			for(String localeCode : definition.getDescription().keySet())
			{
				D description = definition.getDescription().get(localeCode);
				String code = TxtIoTemplateFactory.buildCode(template,definition,localeCode);
				fmStringTemplates.putTemplate(code,description.getLang());
			}
		}
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