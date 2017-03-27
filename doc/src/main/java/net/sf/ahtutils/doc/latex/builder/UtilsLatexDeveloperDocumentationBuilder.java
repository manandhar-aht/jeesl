package net.sf.ahtutils.doc.latex.builder;

import org.apache.commons.configuration.Configuration;
import org.jeesl.doc.latex.builder.AbstractLatexDocumentationBuilder;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;

public class UtilsLatexDeveloperDocumentationBuilder extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexDeveloperDocumentationBuilder.class);
					
	public static final String cfgKeyErSvg = "doc.image.admin.development.er";
	public static final String cfgKeyVcsUrl = "doc.admin.development.vcs";
	
	public static enum Code {configuration}
		
	public UtilsLatexDeveloperDocumentationBuilder(Configuration config, Translations translations,String[] langs, ConfigurationProvider cp)
	{
		super(config,translations,langs,cp);
	}
	
	@Override protected void applyBaseLatexDir()
	{
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseLatexDir);
	}
	
	@Override protected void applyConfigCodes()
	{	
		addConfig(Code.configuration.toString(),"ofx.aht-utils/developer/configuration.xml","developer/configuration");
		
	}

	public void render(Code code) throws UtilsConfigurationException, OfxConfigurationException{render(2,code);}
	public void render(int lvl, Code code) throws UtilsConfigurationException, OfxConfigurationException{render(lvl,code.toString());}
	
	
}