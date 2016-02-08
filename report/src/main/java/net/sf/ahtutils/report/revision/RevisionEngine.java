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
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.audit.Change;
import net.sf.ahtutils.xml.audit.Scope;

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
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
							AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(RevisionEngine.class);
	
	private UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> fRevision;
	
	private final Class<RV> cView;
	private final Class<RS> cScope;
	private final Class<RE> cEntity;
	
	private String lang;
	private Map<String,RVM> map;
	
	public RevisionEngine(UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> fRevision, final Class<RV> cView, final Class<RS> cScope, final Class<RE> cEntity)
	{
		this.fRevision=fRevision;
		this.cView=cView;
		this.cScope=cScope;
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
					C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
					R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
					V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
					U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
					A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
					AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
					USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
					RevisionEngine<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT,REV,C,R,V,U,A,AT,USER> factory(UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> fRevision, final Class<RV> cView, final Class<RS> cScope, final Class<RE> cEntity)
	{
		return new RevisionEngine<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT,REV,C,R,V,U,A,AT,USER>(fRevision, cView, cScope, cEntity);
	}
	
	public void init(String lang, RV view)
	{
		this.lang=lang;
		view = fRevision.load(cView, view);
		map.clear();
		for(RVM m : view.getMaps())
		{
			m.setEntity(fRevision.load(cEntity, m.getEntity()));
			m.getEntityMapping().setScope(fRevision.load(cScope, m.getEntityMapping().getScope()));
			map.put(m.getEntity().getCode(),m);
		}
		logger.info(this.getClass().getSimpleName()+" initialized with "+map.size()+" entities");
	}
	
	public Change build(UtilsRevisionContainer<REV,?,L,D,C,R,V,U,A,AT,USER> revision)
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
			return null;
		}
		xml.setAid(revision.getType().ordinal());
		return xml;
	}
	
	public Change build(RVM rvm, Object o)
	{
		JXPathContext context = JXPathContext.newContext(o);
		
		Change change = new Change();
		change.setType(rvm.getEntity().getName().get(lang).getLang());
		
		StringBuffer sb = new StringBuffer();
		for(RA attribute : rvm.getEntity().getAttributes())
		{
			if(attribute.isShowPrint())
			{
				sb.append(build(attribute, context));
				sb.append(" ");
			}
		}
		change.setText(sb.toString().trim());
		change.setScope(build(rvm,context));
		
		return change;
	}
		
	private Scope build(RVM rvm, JXPathContext context)
	{
		Object oScope = context.getValue(rvm.getEntityMapping().getXpath());
		JXPathContext ctx = JXPathContext.newContext(oScope);
		
		Scope xScope = new Scope();
		xScope.setClazz(oScope.getClass().getName());
		xScope.setCategory(rvm.getEntityMapping().getScope().getCategory().getName().get(lang).getLang());
		
		if(oScope instanceof EjbWithId){xScope.setId(((EjbWithId)oScope).getId());}
		StringBuffer sb = new StringBuffer();
		for(RA attribute : rvm.getEntityMapping().getScope().getAttributes())
		{
			if(attribute.isShowPrint())
			{
				sb.append(build(attribute, ctx));
				sb.append(" ");
			}
		}
		xScope.setEntity(sb.toString().trim());
		return xScope;
	}
	
	private String build(RA attribute, JXPathContext ctx)
	{
		StringBuffer sb = new StringBuffer();
		if(attribute.isShowEnclosure()){sb.append("{");}
		if(attribute.isShowName()){sb.append(attribute.getName().get(lang).getLang()).append(": ");}
		
		String txt;
		if(attribute.getType().getCode().startsWith(UtilsRevisionAttribute.Type.text.toString()))
		{
			txt = (String)ctx.getValue(attribute.getXpath());
		}
		else
		{
			logger.warn("Unsupported Type: "+attribute.getType().getCode());
			txt = ""+ctx.getValue(attribute.getXpath());
		}
		sb.append(txt);
		
		if(attribute.isShowEnclosure()){sb.append("}");}
		return sb.toString();
	}
}