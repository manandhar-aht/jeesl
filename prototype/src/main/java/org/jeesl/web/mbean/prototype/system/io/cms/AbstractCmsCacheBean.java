package org.jeesl.web.mbean.prototype.system.io.cms;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jeesl.api.bean.JeeslCmsCacheBean;
import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.controller.provider.GenericLocaleProvider;
import org.jeesl.factory.builder.io.IoCmsFactoryBuilder;
import org.jeesl.interfaces.controller.JeeslCmsRenderer;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.util.xml.JaxbUtil;

public abstract class AbstractCmsCacheBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
										CAT extends UtilsStatus<CAT,L,D>,
										CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
										V extends JeeslIoCmsVisiblity,
										S extends JeeslIoCmsSection<L,S>,
										E extends JeeslIoCmsElement<V,S,EC,ET,C,FC>,
										EC extends UtilsStatus<EC,L,D>,
										ET extends UtilsStatus<ET,L,D>,
										C extends JeeslIoCmsContent<V,E,MT>,
										MT extends UtilsStatus<MT,L,D>,
										FC extends JeeslFileContainer<?,?>
										>
					implements Serializable,JeeslCmsCacheBean<S>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractCmsCacheBean.class);
	
	private final IoCmsFactoryBuilder<L,D,LOC,CAT,CMS,V,S,E,EC,ET,C,MT,FC> fbCms;
	private JeeslCmsRenderer<L,D,LOC,CAT,CMS,V,S,E,EC,ET,C,MT,FC> ofx;
	private JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,FC,LOC> fCms;
	
	private final Map<S,Map<String,Section>> mapSection;
	private boolean debugOnInfo; protected void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}

	public AbstractCmsCacheBean(IoCmsFactoryBuilder<L,D,LOC,CAT,CMS,V,S,E,EC,ET,C,MT,FC> fbCms)
	{
		this.fbCms=fbCms;
		
		mapSection = new HashMap<S,Map<String,Section>>();
	}
	
	protected void postConstructCms(JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,FC,LOC> fCms,
									JeeslCmsRenderer<L,D,LOC,CAT,CMS,V,S,E,EC,ET,C,MT,FC> ofx)
	{
		this.fCms=fCms;
		this.ofx=ofx;
	}
	
	public void clearCache(S section)
	{
		logger.info("Invalidation Section "+section.toString());
		if(mapSection.containsKey(section)) {mapSection.remove(section);}
	}
	
	public Section build(String localeCode, S section)
	{
		if(section==null) {logger.warn("Section is NULL"); return new Section();}
		else
		{
			if(debugOnInfo) {logger.info("Requesting "+localeCode+" "+section.toString());}
			if(!mapSection.containsKey(section)) {mapSection.put(section, new HashMap<String,Section>());}
			
//			logger.info("Cached section:"+mapSection.containsKey(section));
//			logger.info("Cached locale:"+mapSection.get(section).containsKey(localeCode));
			
			if(mapSection.get(section).containsKey(localeCode)) {return mapSection.get(section).get(localeCode);}
			else
			{
				section = fCms.find(fbCms.getClassSection(),section);
				if(debugOnInfo) {logger.info("Not in cache, Trrancoding Section: " + section.toString());}
				Section ofxSection = null;
				try
				{
					LOC locale = fCms.fByCode(fbCms.getClassLocale(), localeCode);
					GenericLocaleProvider<L,D,LOC> lp = new GenericLocaleProvider<L,D,LOC>();
					lp.setLocales(Arrays.asList(locale));
					
					ofxSection = ofx.build(lp,localeCode,section);
					if(debugOnInfo) {JaxbUtil.info(ofxSection);}
					mapSection.get(section).put(localeCode, ofxSection);
				}
				catch (OfxAuthoringException e){e.printStackTrace();}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
				return ofxSection;
			}		
		}
	}
}