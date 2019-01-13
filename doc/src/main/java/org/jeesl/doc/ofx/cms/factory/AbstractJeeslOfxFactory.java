package org.jeesl.doc.ofx.cms.factory;

import org.jeesl.doc.ofx.OfxMultiLangFactory;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractJeeslOfxFactory<L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslOfxFactory.class);
	
	protected OfxTranslationProvider tp;
	
	protected final OfxMultiLangFactory<L> ofxMultiLang;
	
	public AbstractJeeslOfxFactory(OfxTranslationProvider tp)
	{	
		this.tp=tp;

		ofxMultiLang = new OfxMultiLangFactory<L>(tp.getLocaleCodes());
	}
}