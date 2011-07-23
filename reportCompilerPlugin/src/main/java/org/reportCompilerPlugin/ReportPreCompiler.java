package org.reportCompilerPlugin;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import net.sf.ahtutils.report.ReportController;
import net.sf.ahtutils.report.AbstractReportControl.Direction;
import net.sf.ahtutils.report.AbstractReportControl.Output;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.save.JRPrintSaveContributor;

/**
 * Goal which compiles a set of JasperReports jrxml files to .jasper file. Creates a rtl language and a ltr language version of all reports.
 *
 * @goal compileReports
 * 
 * @phase process-sources
 */
public class ReportPreCompiler
    extends AbstractMojo
{
	/**
     * Location of the file.
     * @parameter expression="src/main/resources/config/config.xml"
     * @required
     */
    private String configFile;

    static Log logger = LogFactory.getLog(ReportPreCompiler.class);
	protected Configuration config;
	
    public void execute()
        throws MojoExecutionException
    {	
    	if (false)
    	{
	        //Create Configuration object from configuration file
	        ConfigurationFactory factory = new ConfigurationFactory(configFile);
	        Configuration config = null;
	        try {
				config = factory.getConfiguration();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
			
			//Get the ReportController from ahtutils
			ReportController reportController = new ReportController(config);
	
			//Get a list of all reports in configuration file
			String xPathPrefix = "//report";
			int reportCount = (config.getStringArray(xPathPrefix)).length;
			logger.debug("Pre-Compiling "+reportCount+" Reports");
			for(int i=1;i<=reportCount;i++)
			{
				//Compile reports and save to file
				String rId = config.getString(xPathPrefix+"/["+i+"]/@id");
				reportController.setParameter("en", rId);
				
				
			}
    	}
    	else
    	{
    		getLog().warn("No config file specified!");
    	}
    }
}
