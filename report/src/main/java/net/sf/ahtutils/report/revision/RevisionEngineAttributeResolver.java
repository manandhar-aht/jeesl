package net.sf.ahtutils.report.revision;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevision;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
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

public class RevisionEngineAttributeResolver<L extends UtilsLang,D extends UtilsDescription,
							RC extends UtilsStatus<RC,L,D>,
							RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RST extends UtilsStatus<RST,L,D>,
							RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
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
	final static Logger logger = LoggerFactory.getLogger(RevisionEngineAttributeResolver.class);

	private String lang;

	private Map<RAT,DecimalFormat> mapDecimalFormatter;
	private Map<RAT,SimpleDateFormat> mapDateFormatter;
	
	public RevisionEngineAttributeResolver(Map<RAT,DecimalFormat> mapDecimalFormatter, Map<RAT,SimpleDateFormat> mapDateFormatter)
	{
		this.mapDecimalFormatter=mapDecimalFormatter;
		this.mapDateFormatter=mapDateFormatter;
	}
		
	public String build(RA attribute, JXPathContext ctx)
	{
		StringBuffer sb = new StringBuffer();
		if(attribute.isShowEnclosure()){sb.append("{");}
		if(attribute.isShowName()){sb.append(attribute.getName().get(lang).getLang()).append(": ");}
		sb.append(buildString(attribute,ctx));
		if(attribute.isShowEnclosure()){sb.append("}");}
		return sb.toString();
	}
	
	private String buildString(RA attribute, JXPathContext ctx)
	{
		String result = null;
		if(attribute.getType().getCode().startsWith(UtilsRevisionAttribute.Type.text.toString()))
		{
			result = (String)ctx.getValue(attribute.getXpath());
		}
		else if(attribute.getType().getCode().startsWith(UtilsRevisionAttribute.Type.number.toString()))
		{
			result = mapDecimalFormatter.get(attribute.getType()).format((Double)ctx.getValue(attribute.getXpath()));
		}
		else if(attribute.getType().getCode().startsWith(UtilsRevisionAttribute.Type.date.toString()))
		{
			result = mapDateFormatter.get(attribute.getType()).format((Date)ctx.getValue(attribute.getXpath()));
		}
		else
		{
			logger.warn("Unsupported Type: "+attribute.getType().getCode());
			result = ""+ctx.getValue(attribute.getXpath());
		}
		
		return result;
	}
}