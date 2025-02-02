package org.jeesl.controller.report.module.ts;

import java.util.Date;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.factory.xml.module.ts.XmlTransactionFactory;
import org.jeesl.factory.xml.module.ts.XmlTsFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportFactory;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.module.ts.Ts;
import org.jeesl.util.query.xml.module.XmlTsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.xml.report.Report;

public class JeeslTsTransactionsReport <L extends UtilsLang,D extends UtilsDescription,
						
						TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER,?>,
						SOURCE extends EjbWithLangDescription<L,D>, 
						
						DATA extends JeeslTsData<?,TRANSACTION,?,?>,
						
						USER extends JeeslUser<?>
						>
					
//implements JeeslReportHeader//,JeeslFlatReport,JeeslXlsReport
{
	final static Logger logger = LoggerFactory.getLogger(JeeslTsTransactionsReport.class);

	private final JeeslTsFacade<L,D,?,?,?,?,?,?,TRANSACTION,SOURCE,?,?,?,?,?,?,USER,?,?> fTs;
	
	private final XmlTransactionFactory<L,D,TRANSACTION,SOURCE,USER> xfTransaction;
	
	public JeeslTsTransactionsReport(String localeCode,
			final JeeslTsFacade<L,D,?,?,?,?,?,?,TRANSACTION,SOURCE,?,?,?,?,?,?,USER,?,?> fTs)
	{
	
		this.fTs=fTs;
		
		xfTransaction = new XmlTransactionFactory<>(XmlTsQuery.get(XmlTsQuery.Key.rTransaction, localeCode));
	}
	
	public Report build(Date from, Date to)
	{
		Report xml = XmlReportFactory.build();
		
		Ts ts = XmlTsFactory.build();
		
		for(TRANSACTION t : fTs.fTransactions(null, from, to))
		{
			ts.getTransaction().add(xfTransaction.build(t));
		}
		xml.setTs(ts);
		
		return xml;
	}
	
	
	
}