package org.jeesl.web.rest.module;

import org.jeesl.api.facade.module.JeeslFeedbackFacade;
import org.jeesl.api.rest.module.feedback.JeeslFeedbackRestExport;
import org.jeesl.api.rest.module.feedback.JeeslFeedbackRestImport;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedback;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedbackThread;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class FeedbackRestService <L extends UtilsLang, D extends UtilsDescription,
								THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends UtilsStatus<STYLE,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								USER extends EjbWithEmail>
					extends AbstractJeeslRestService<L,D>
					implements JeeslFeedbackRestExport,JeeslFeedbackRestImport
{
	final static Logger logger = LoggerFactory.getLogger(FeedbackRestService.class);
	
	private final JeeslFeedbackFacade<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER> fFeedback;
	
	private final Class<STYLE> cStyle;
	private final Class<TYPE> cType;
	
	private FeedbackRestService(JeeslFeedbackFacade<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER> fFeedback,final Class<L> cL, final Class<D> cD, final Class<STYLE> cStyle, final Class<TYPE> cType)
	{
		super(fFeedback,cL,cD);
		this.fFeedback=fFeedback;
		
		this.cStyle=cStyle;
		this.cType=cType;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
					FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
					STYLE extends UtilsStatus<STYLE,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					USER extends EjbWithEmail>
			FeedbackRestService<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>
			factory(JeeslFeedbackFacade<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER> fFeedback,final Class<L> cL, final Class<D> cD, final Class<STYLE> cStyle, final Class<TYPE> cType)
	{
		return new FeedbackRestService<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>(fFeedback,cL,cD,cStyle,cType);
	}
	
	@Override public Container exportFeedbackStyle() {return xfContainer.build(fFeedback.allOrderedPosition(cStyle));}
	@Override public Container exportFeedbackType() {return xfContainer.build(fFeedback.allOrderedPosition(cType));}

	@Override public DataUpdate importFeedbackStyle(Container container){return importStatus(cStyle,container,null);}
	@Override public DataUpdate importFeedbackType(Container container){return importStatus(cType,container,null);}
}