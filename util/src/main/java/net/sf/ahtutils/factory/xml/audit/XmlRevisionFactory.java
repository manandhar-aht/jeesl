package net.sf.ahtutils.factory.xml.audit;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.audit.Revision;
import net.sf.ahtutils.xml.audit.User;
import net.sf.exlp.util.DateUtil;

public class XmlRevisionFactory <L extends UtilsLang, D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRevisionFactory.class);
	
	private XmlUserFactory<L,D,C,R,V,U,A,AT,USER> fUser;
	
	public XmlRevisionFactory()
	{
		fUser = new XmlUserFactory<L,D,C,R,V,U,A,AT,USER>();
	}
	
	public Revision build(long rev, Date record)
	{
		Revision xml = new Revision();
		xml.setRev(rev);
		xml.setDate(DateUtil.toXmlGc(record));
		return xml;
	}
	
	public Revision build(long rev, Date record, USER user)
	{
		Revision xml = new Revision();
		xml.setRev(rev);
		xml.setDate(DateUtil.toXmlGc(record));
		xml.setUser(fUser.build(user));
		return xml;
	}
	
	public Revision build(long rev, Date record, User user)
	{
		Revision xml = new Revision();
		xml.setRev(rev);
		xml.setDate(DateUtil.toXmlGc(record));
		xml.setUser(user);
		return xml;
	}
}