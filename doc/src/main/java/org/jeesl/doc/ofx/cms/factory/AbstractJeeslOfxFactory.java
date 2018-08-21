package org.jeesl.doc.ofx.cms.factory;

import org.jeesl.doc.ofx.OfxMultiLangFactory;
import org.openfuxml.interfaces.configuration.TranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractJeeslOfxFactory<L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslOfxFactory.class);
	
	protected TranslationProvider tp;
	
	protected final OfxMultiLangFactory<L> ofxMultiLang;
	
	public AbstractJeeslOfxFactory(TranslationProvider tp)
	{	
		this.tp=tp;

		ofxMultiLang = new OfxMultiLangFactory<L>(tp.getLocaleCodes());
	}
}