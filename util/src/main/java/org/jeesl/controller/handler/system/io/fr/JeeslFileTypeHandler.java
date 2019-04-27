package org.jeesl.controller.handler.system.io.fr;

import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.util.db.cache.EjbCodeMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslFileTypeHandler<META extends JeeslFileMeta<?,?,TYPE>,
									TYPE extends JeeslFileType<TYPE,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslFileTypeHandler.class);

	private final EjbCodeMapCache<TYPE> cache;
	
	public JeeslFileTypeHandler(IoFileRepositoryFactoryBuilder<?,?,?,?,?,?,META,TYPE> fbFile,
								JeeslIoFrFacade<?,?,?,?,?,META,TYPE> fFr)
	{
		cache = new EjbCodeMapCache<TYPE>(fFr,fbFile.getClassType());
	}
	
	public void updateType(META meta)
	{
		logger.info("META "+meta.getFileName());
		
		JeeslFileType.Code code = buildType(meta.getFileName().trim().toLowerCase());
		meta.setType(cache.ejb(code));
	}
	
	private static JeeslFileType.Code buildType(String fileName)
	{
		if(fileName.endsWith(".doc") || fileName.endsWith(".docx")){return JeeslFileType.Code.docx;}
		else if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){return JeeslFileType.Code.xlsx;}
		else if(fileName.endsWith(".pdf")){return JeeslFileType.Code.pdf;}
		else if(fileName.endsWith(".csv")){return JeeslFileType.Code.csv;}
		else if(fileName.endsWith(".xml")){return JeeslFileType.Code.xml;}
		else if(fileName.endsWith(".zip") || fileName.endsWith(".rar")){return JeeslFileType.Code.zip;}
		else if(fileName.endsWith(".jpeg") || fileName.endsWith(".jpg")){return JeeslFileType.Code.jpg;}
		return JeeslFileType.Code.unknown;
	}
}