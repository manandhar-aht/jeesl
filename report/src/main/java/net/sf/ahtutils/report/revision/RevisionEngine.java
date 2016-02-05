package net.sf.ahtutils.report.revision;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevision;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionContainer;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.audit.Change;

public class RevisionEngine<L extends UtilsLang,D extends UtilsDescription,
							RC extends UtilsStatus<RC,L,D>,
							RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
							RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
							RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
							RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
							REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
							RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
							RAT extends UtilsStatus<RAT,L,D>,
							REV extends UtilsRevision,
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(RevisionEngine.class);
	
	private UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> fRevision;
	
	private final Class<RV> cView;
	private final Class<RE> cEntity;
	
	private String lang;
	private Map<String,RVM> map;
	
	public RevisionEngine(UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> fRevision, final Class<RV> cView, final Class<RE> cEntity)
	{
		this.fRevision=fRevision;
		this.cView=cView;
		this.cEntity=cEntity;
		
		map = new ConcurrentHashMap<String,RVM>();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RC extends UtilsStatus<RC,L,D>,
					RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RAT extends UtilsStatus<RAT,L,D>,
					REV extends UtilsRevision,
					C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
					R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
					V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
					U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
					A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
					USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
					RevisionEngine<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT,REV,C,R,V,U,A,USER> factory(UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> fRevision, final Class<RV> cView, final Class<RE> cEntity)
	{
		return new RevisionEngine<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT,REV,C,R,V,U,A,USER>(fRevision, cView, cEntity);
	}
	
	public void init(String lang, RV view)
	{
		this.lang=lang;
		view = fRevision.load(cView, view);
		map.clear();
		for(RVM m : view.getMaps())
		{
			m.setEntity(fRevision.load(cEntity, m.getEntity()));
			map.put(m.getEntity().getCode(),m);
		}
		logger.info(this.getClass().getSimpleName()+" initialized with "+map.size()+" entities");
	}
	
	public Change build(UtilsRevisionContainer<REV,?,L,D,C,R,V,U,A,USER> revision)
	{
		Object o = revision.getEntity();
		String key = o.getClass().getName();
		Change xml;
		
		if(map.containsKey(key))
		{
			xml = build(map.get(key),o);
		}
		else
		{
			xml = new Change();
		}
		
		return xml;
	}
	
	public Change build(RVM viewMapping, Object o)
	{
		Change change = new Change();
		change.setType(viewMapping.getEntity().getName().get(lang).getLang());
		
		StringBuffer sb = new StringBuffer();
		for(RA attribute : viewMapping.getEntity().getAttributes())
		{
			JXPathContext context = JXPathContext.newContext(o);
			sb.append(build(attribute, context));
			sb.append(" ");
		}
		change.setText(sb.toString().trim());
		
		return change;
	}
	
	private String build(RA attribute, JXPathContext ctx)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(attribute.getName().get(lang).getLang()).append(": ");
		sb.append((String)ctx.getValue(attribute.getXpath()));
		sb.append("}");
		return sb.toString();
	}
}