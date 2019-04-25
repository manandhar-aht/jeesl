package org.jeesl.factory.xml.module.ts;

import org.jeesl.factory.xml.system.security.XmlUserFactory;
import org.jeesl.factory.xml.system.status.XmlSourceFactory;
import org.jeesl.factory.xml.system.util.text.XmlReferenceFactory;
import org.jeesl.factory.xml.system.util.text.XmlRemarkFactory;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.jeesl.QueryTs;
import org.jeesl.model.xml.module.ts.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.xml.status.Source;
import net.sf.exlp.util.DateUtil;

public class XmlTransactionFactory<L extends UtilsLang,D extends UtilsDescription,
								   TRANSACTION extends JeeslTsTransaction<SOURCE,?,USER,?>,
								   SOURCE extends EjbWithLangDescription<L,D>, 
								   USER extends JeeslUser<?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTransactionFactory.class);

	private final String localeCode;
	private final Transaction q;
	
	private XmlUserFactory<USER> xfUser;

	public XmlTransactionFactory(QueryTs query){this(query.getLocaleCode(),query.getTransaction());}
	public XmlTransactionFactory(String localeCode,Transaction q)
	{
		this.localeCode=localeCode;
		this.q=q;
		if(q.isSetUser()) {xfUser = new XmlUserFactory<>(q.getUser());}
	}

	public static Transaction build() {return new Transaction();}

	public Transaction build(TRANSACTION ejb)
	{
		Transaction xml = new Transaction();

		if(q.isSetId()) {xml.setId(ejb.getId());}
		if(q.isSetRecord()) {xml.setRecord(DateUtil.getXmlGc4D(ejb.getRecord()));}
		if(q.isSetUser()) {xml.setUser(xfUser.build(ejb.getUser()));}
		
		if(q.isSetRemark()) {xml.setRemark(XmlRemarkFactory.build(ejb.getRemark()));}
		if(q.isSetReference()) {xml.setReference(XmlReferenceFactory.build(ejb.getReference()));}
		
		if(q.isSetSource() && ejb.getSource()!=null && ejb.getSource().getName().containsKey(localeCode))
		{
			Source source = XmlSourceFactory.label(null,ejb.getSource().getName().get(localeCode).getLang());
			xml.setSource(source);
		}
		
		return xml;
	}
}