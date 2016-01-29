package net.sf.ahtutils.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.FileIO;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsMsgBuilder
{	
	public static final String srcGeneric = "msg.aht-utils/generic.xml";
	public static final String srcProject = "msg.aht-utils/project.xml";
	public static final String srcFinance = "msg.aht-utils/finance/finance.xml";
	public static final String srcAmount = "msg.aht-utils/finance/amount.xml";
	public static final String srcDate = "msg.aht-utils/date.xml";
	
	public static final String timeseries = "aht-utils/msg/module/timeseries.xml";
	public static final String tooltip = "aht-utils/msg/tooltip.xml";
	public static final String srcMonitoring = "msg.aht-utils/monitoring.xml";
	public static final String srcWizard = "msg.aht-utils/wizard.xml";
	public static final String srcAdminAuditLog = "msg.aht-utils/admin/system/auditLog.xml";
	public static final String srcAdminSync = "msg.aht-utils/admin/system/sync.xml";
	public static final String srcAdminSystemProperty = "msg.aht-utils/admin/system/properties.xml";
	public static final String srcAdminSystemTraffic = "msg.aht-utils/admin/system/trafficLights.xml";
	public static final String srcAdminSystemDbStatistic = "msg.aht-utils/admin/system/dbStatistic.xml";
	public static final String srcAdminSystemDbDump = "msg.aht-utils/admin/system/dbDump.xml";
	public static final String msgAdminSystemOptionTables = "msg.aht-utils/admin/system/options.xml";
	public static final String srcAdminSecurity = "msg.aht-utils/admin/user/security.xml";
	public static final String srcAdminUser = "msg.aht-utils/admin/user/user.xml";
	public static final String srcAdminStatus = "msg.aht-utils/admin/status.xml";
	public static final String srcRevision = "msg.aht-utils/system/revision.xml";
	public static final String srcConstraint = "msg.aht-utils/system/constraint.xml";
	public static final String srcFacesMessages = "msg.aht-utils/facesMessages.xml";
	
	public static final String srcSurvey = "msg.aht-utils/survey.xml";
	public static final String devQa = "msg.aht-utils/development/qa.xml";
	
	final static Logger logger = LoggerFactory.getLogger(UtilsMsgBuilder.class);
		
	private MultiResourceLoader mrl;
	private File baseMsg;
	
	public UtilsMsgBuilder(Configuration config)
	{
		mrl = new MultiResourceLoader();
		baseMsg = new File(config.getString(UtilsDocumentation.keyBaseMsgDir));
		logger.info("Using msg.dir ("+UtilsDocumentation.keyBaseMsgDir+"): "+baseMsg.getAbsolutePath());
	}
	
	public void copy(String src, String dst) throws UtilsConfigurationException
	{
		try
		{
			InputStream is = mrl.searchIs(src);
			File fTarget = new File(baseMsg,dst);
			
			Translations t = JaxbUtil.loadJAXB(is, Translations.class);
			Document doc = JaxbUtil.toDocument(t);
			
			Comment comment = new Comment("Do not modify this file, it is automatically generated!");
			doc.addContent(0, comment);
			
			byte[] bytes = IOUtils.toByteArray(JDomUtil.toInputStream(doc, Format.getPrettyFormat()));
			FileIO.writeFileIfDiffers(bytes, fTarget);
			logger.info("Written "+dst);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}