package org.jeesl.factory.factory;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeesl.factory.ejb.system.io.db.EjbDbDumpFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportCellFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportRowFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportStyleFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportTemplateFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportWorkbookFactory;
import org.jeesl.factory.xls.system.io.report.XlsCellFactory;
import org.jeesl.factory.xls.system.io.report.XlsStyleFactory;
import org.jeesl.factory.xls.system.io.report.XlsColumnFactory;
import org.jeesl.factory.xls.system.io.report.XlsRowFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class DbFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
							DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
							FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
							HOST extends UtilsStatus<HOST,L,D>,
							STATUS extends UtilsStatus<STATUS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(DbFactoryFactory.class);
	
//	private final Class<L> cL;
//	private final Class<D> cD;
	private final Class<DUMP> cDump;
//	private final Class<FILE> cFile;
//	private final Class<HOST> cHost;
//	private final Class<STATUS> cStatus;
    
	private DbFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<DUMP> cDump, final Class<FILE> cFile, final Class<HOST> cHost, final Class<STATUS> cStatus)
	{       
//		this.cL = cL;
//       this.cD = cD;
        this.cDump = cDump;
//        this.cFile = cFile;
//        this.cHost=cHost;
//        this.cStatus = cStatus;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					DUMP extends JeeslDbDump<L,D,DUMP,FILE,HOST,STATUS>,
					FILE extends JeeslDbDumpFile<L,D,DUMP,FILE,HOST,STATUS>,
					HOST extends UtilsStatus<HOST,L,D>,
					STATUS extends UtilsStatus<STATUS,L,D>>
		DbFactoryFactory<L,D,DUMP,FILE,HOST,STATUS> factory(final Class<L> cL,final Class<D> cD,final Class<DUMP> cDump, final Class<FILE> cFile, final Class<HOST> cHost, final Class<STATUS> cStatus)
	{
		return new DbFactoryFactory<L,D,DUMP,FILE,HOST,STATUS>(cL,cD,cDump,cFile,cHost,cStatus);
	}
	
	public EjbDbDumpFactory<L,D,DUMP,FILE,HOST,STATUS> dump()
	{
		return new EjbDbDumpFactory<L,D,DUMP,FILE,HOST,STATUS>(cDump);
	}
}