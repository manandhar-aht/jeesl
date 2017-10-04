package net.sf.ahtutils.maven;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.msgbundle.TranslationFactory;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.jeesl.maven.goal.JeeslMsgGoal;

/**
 * Goal which compiles a set of JasperReports jrxml files to .jasper file. Creates a rtl language and a ltr language version of all reports.
 *
 * @goal msgBundle9
 * 
 * @phase initialize
 */
public class UtilsMsgBundleGoal extends AbstractMojo
{
	/**
     * Location of the file.
     * @parameter expression="${project.groupId}"
     * @required
     */
    private String groupId;
    
	/**
     * Location of the file.
     * @parameter expression="${project.parent.artifactId}"
     * @required
     */
    private String projectArtifactId;
    
	/**
     * Location of the file.
     * @parameter expression="${project.artifactId}"
     * @required
     */
    private String artifactId;
    
	/**
     * Location of the file.
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private String projectBuildDirectory;
    
    /**
     * Location of the file.
     * This the deprecated location: ${basedir}/src/main/resources/msg.${project.artifactId}
     * @parameter expression="${basedir}/../doc/src/main/resources/msg.${project.parent.artifactId}"
     * @required
     */
    private String msgSource;
    
    /**
     * Location of the file.
     * This the deprecated location: ${project.build.directory}/msg.${project.artifactId}
     * @parameter expression="${basedir}/src/main/resources/msg.${project.artifactId}"
     * @required
     */
    private String targetDir;
    
    /**
     * Location of the file.
     */
    @Parameter(property = "translationXml", defaultValue = "translation.xml" )
    private String translationXml;
	
    
    public void execute() throws MojoExecutionException
    {
	    	if(translationXml==null){translationXml = "translation.xml";}
	    	
	    	BasicConfigurator.configure();
	    	Logger.getRootLogger().setLevel(Level.ERROR);
	    	 
	    	getLog().info("groupId: "+groupId);
	    	getLog().info("projectArtifactId: "+projectArtifactId);
	    	getLog().info("artifactId: "+artifactId);
	    	getLog().info("msgSource: "+msgSource);
	    	getLog().info("projectBuildDirectory: "+projectBuildDirectory);
	    	getLog().info("targetDir: "+targetDir);
	    	getLog().info("translationXml: ?? "+translationXml);
	    	
	    	File fTarget = JeeslMsgGoal.createTargetDir(targetDir);
	    	
	    	File fRoot = new File(msgSource);
	    	if(!fRoot.exists()){throw new MojoExecutionException("msg.bundle directory does not exist: "+fRoot.getAbsolutePath());}
	    	
	    	File fTranslationsXml = new File(fTarget,translationXml);
	    	
	    	getLog().info("Creating MessageBundle "+groupId+".msg_<lang>.txt from "+msgSource);
	    	
	    	TranslationFactory tFactory = new TranslationFactory();
		tFactory.setOutEncoding("UTF-8");
		try
		{
			Dir dir = tFactory.rekursiveDirectory(fRoot.getAbsolutePath());
			dir.setName("msg."+projectArtifactId);
			getLog().info("Saving XML summary file to"+fTranslationsXml.getAbsolutePath());
			JaxbUtil.save(fTranslationsXml,dir,true);
			
			tFactory.writeMessageResourceBundles("msg",fTarget);
			for(String s : tFactory.getStats())
			{
				getLog().info(s);
			}
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
    }
}