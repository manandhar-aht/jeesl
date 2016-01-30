package net.sf.ahtutils.doc.latex.builder;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsLatexAdminDocumentationBuilder extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexAdminDocumentationBuilder.class);
	
	private static enum Code {accessIntroduction};
							 
	public static enum MaintenanceCode {mLoggingIntroduction,mJboss,mDisasterRecovery,mScp}
	
	public static enum BackupCode {bPostgres}
	
	public static enum SecurityCode {sConceptIntroduction,secViews,secUsecases,secRoles,sActualUsecases,sActualRoles,sActualViews}
							 
	public static enum InstallationCode {instIntroduction,instDebian,instJava,instJboss,instPostGis,instMySql,instMaven}
	public static enum InstallationArchitecture {debian,debianSqueeze,debianWheezy,debianJessie,debianRaspberry,devJava7FX}
	
	public static enum JBossClassifier {as7,eap6,eap6Source,mysql,postgis}
	
	public static enum RequirementsCode {reqIntroduction,reqHardware,reqAdmin,reqDeveloper}
	public static enum RequirementsClassifier {reqHardware,reqAdmin,reqDeveloper,reqNetwork}
	
	public static enum ToolsCode {toolsOsticket,toolsApache}
	public static enum SystemCode {systemWs,systemTsStructure}
		
	public UtilsLatexAdminDocumentationBuilder(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(config,translations,langs,cmm,dsm);
	}
	
	@Override protected void applyBaseLatexDir()
	{
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseLatexDir);
	}
	
	@Override protected void applyConfigCodes()
	{
		//Security
		addConfig(SecurityCode.sConceptIntroduction.toString(),"ofx.aht-utils/administration/security/concept/introduction.xml","admin/security/concept/introduction");
		addConfig(SecurityCode.secViews.toString(),"ofx.aht-utils/administration/security/settings/views.xml","admin/security/settings/views");
		addConfig(SecurityCode.secUsecases.toString(),"ofx.aht-utils/administration/security/settings/usecases.xml","admin/security/settings/usecases");
		addConfig(SecurityCode.secRoles.toString(),"ofx.aht-utils/administration/security/settings/roles.xml","admin/security/settings/roles");
		addConfig(SecurityCode.sActualViews.toString(),"ofx.aht-utils/administration/security/actual/views.xml");
		addConfig(SecurityCode.sActualRoles.toString(),"ofx.aht-utils/administration/security/actual/roles.xml");
		addConfig(SecurityCode.sActualUsecases.toString(),"ofx.aht-utils/administration/security/actual/usecases.xml");
				
		//Maintenance
		addConfig(MaintenanceCode.mLoggingIntroduction.toString(),"ofx.aht-utils/administration/logging/introduction.xml","admin/system/logging/introduction");
		addConfig(MaintenanceCode.mJboss.toString(),"ofx.aht-utils/administration/as/jboss.xml","admin/as/jboss");
		addConfig(MaintenanceCode.mDisasterRecovery.toString(),"ofx.aht-utils/administration/system/disaster.xml","admin/system/disasterRecovery");
		addConfig(MaintenanceCode.mScp.toString(),"ofx.aht-utils/maintenance/scp.xml","admin/maintenance/scp");

		//Backup
		addConfig(BackupCode.bPostgres.toString(),"ofx.aht-utils/administration/db/postgres.xml","admin/db/postgres");
		
		//Installation
		addConfig(InstallationCode.instIntroduction.toString(),"ofx.aht-utils/installation/introduction.xml","admin/installation/introduction");
		addConfig(InstallationCode.instDebian.toString(),"ofx.aht-utils/installation/debian/debian.xml","admin/installation/debian");
		addConfig(InstallationCode.instJava.toString(),"ofx.aht-utils/installation/java.xml","admin/installation/java");
		addConfig(InstallationCode.instJboss.toString(),"ofx.aht-utils/installation/jboss/jboss.xml","admin/installation/jboss");
		addConfig(InstallationCode.instPostGis.toString(),"ofx.aht-utils/installation/postgres/postgres.xml","admin/installation/postgres");
		addConfig(InstallationCode.instMySql.toString(),"ofx.aht-utils/installation/mysql.xml","admin/installation/mysql");
		addConfig(InstallationCode.instMaven.toString(),"ofx.aht-utils/installation/maven.xml","admin/installation/maven");
//		addConfig(InstallationCode.instGeoserver.toString(),"ofx.aht-utils/installation/geoserver.xml","admin/installation/geoserver");
		
		//Requirements
		addConfig(RequirementsCode.reqIntroduction.toString(),"ofx.aht-utils/requirements/introduction.xml","admin/requirements/introduction");
		addConfig(RequirementsCode.reqHardware.toString(),"ofx.aht-utils/requirements/hardware.xml","admin/requirements/hardware");
		addConfig(RequirementsCode.reqAdmin.toString(),"ofx.aht-utils/requirements/administrator.xml","admin/requirements/administrator");
		addConfig(RequirementsCode.reqDeveloper.toString(),"ofx.aht-utils/requirements/developer.xml","admin/requirements/developer");
		
		//Tools
		addConfig(ToolsCode.toolsOsticket.toString(),"ofx.aht-utils/installation/tools/osticket.xml","admin/installation/tools/osticket");
		addConfig(ToolsCode.toolsApache.toString(),"ofx.aht-utils/installation/tools/apache.xml","admin/installation/tools/apache");
		
		//System
		addConfig(SystemCode.systemWs.toString(),"ofx.aht-utils/administration/system/webservice.xml","admin/system/webservice/introduction");
		
		//TimeSeries
		addConfig(SystemCode.systemTsStructure.toString(),"aht-utils/ofx/system/ts/structure.xml","system/ts/structure");
	}
	
	@Deprecated
	public void buildDoc() throws UtilsConfigurationException, OfxConfigurationException
	{
		logger.info("buildDoc");
		render(Code.accessIntroduction.toString());
	}
	
	public void render(SecurityCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	public void render(int lvl,SecurityCode code) throws UtilsConfigurationException, OfxConfigurationException{render(lvl,code.toString());}

	public void render(MaintenanceCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	public void render(BackupCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	
	public void render(SystemCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	public void render(int lvl,SystemCode code) throws UtilsConfigurationException, OfxConfigurationException{render(lvl,code.toString());}
	
	public void render(ToolsCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	public void render(int lvl,ToolsCode code) throws UtilsConfigurationException, OfxConfigurationException{render(lvl,code.toString());}
	
	public void render(InstallationCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	public void render(InstallationCode code, InstallationArchitecture... architectures) throws UtilsConfigurationException, OfxConfigurationException
	{
		String[] classifier = new String[architectures.length];
		for(int i=0;i<architectures.length;i++){classifier[i]=architectures[i].toString();}
		render(code.toString(),classifier);
	}
	public void render(InstallationCode code, JBossClassifier... versions) throws UtilsConfigurationException, OfxConfigurationException
	{
		String[] classifier = new String[versions.length];
		for(int i=0;i<versions.length;i++){classifier[i]=versions[i].toString();}
		render(code.toString(),classifier);
	}
	
	public void render(RequirementsCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	public void render(RequirementsCode code, RequirementsClassifier... reqClassifier) throws UtilsConfigurationException, OfxConfigurationException
	{
		String[] classifier = new String[reqClassifier.length];
		for(int i=0;i<reqClassifier.length;i++){classifier[i]=reqClassifier[i].toString();}
		render(code.toString(),classifier);
	}
	
}