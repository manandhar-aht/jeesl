package org.jeesl.maven.goal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jeesl.factory.java.security.JavaSecurityViewIdentifierFactory;

import net.sf.ahtutils.controller.factory.java.security.JavaSecuritySeamPagesFactory;
import net.sf.ahtutils.controller.factory.java.security.JavaSecurityViewRestrictorFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.exlp.factory.txt.TxtFileNameFactory;

@Mojo(name="createSeamSecurity")
public class JeeslSecurityGoal extends AbstractMojo
{
	@Parameter(defaultValue = "WARN")
    private String log;
    
	@Parameter(defaultValue = "${project.build.directory}/security.${project.artifactId}")
    private String targetDir;
	
	@Parameter(defaultValue = "${project.basedir}/src/main/java")
    private String srcDir;
    
	@Parameter(defaultValue = "${project.groupId}.${project.artifactId}")
    private String packageBase;
    
	@Parameter
    private String viewsXml;
    
	@Parameter
    private String classPrefix;
    
	@Parameter
    private String classAbstractRestrictor;
    
	@Parameter
    private String classRestrictor;
    
	@Parameter(defaultValue = "login.jsf")
    private String loginView;
    
	@Parameter(defaultValue = "denied.jsf")
    private String accessDeniedView;
	
	@Parameter(defaultValue = "old")
    private String implementation;
	
	private JavaSecurityViewIdentifierFactory jfIdentifier;
	private JavaSecurityViewRestrictorFactory restrictorFactory;
    	
    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(log));
    	
    	if(viewsXml.equals("null")){throw new MojoExecutionException("configuration parameter 'viewsXml' not defined!");}
    	if(classPrefix.equals("null")){throw new MojoExecutionException("configuration parameter 'classPrefix' not defined!");}
    	
    	getLog().info(this.getClass().getSimpleName()+" Implementation: "+implementation);
    	
    	File fTmpDir = new File(targetDir);
    	if(!fTmpDir.exists()){fTmpDir.mkdirs();}
    	
		try
		{
	    	if(implementation.equals("old"))
	    	{
	        	executeIdentifierFactoryOld(fTmpDir);
	        	executeRestrictorFactoryOld(fTmpDir);
	        	executeSeamPagesOld(fTmpDir);
	    	}
	    	else
	    	{
	    		getLog().info("WARN NEW IMPLEMENTATION ");
	    		try {Thread.sleep(2*1000);} catch (InterruptedException e) {e.printStackTrace();}
	    		executeIdentifierFactory(fTmpDir);
	    		executeRestrictorFactory(fTmpDir);
	    	}
		}
    	catch (FileNotFoundException e) {throw new MojoExecutionException(e.getMessage());}
    	catch (UtilsConfigurationException e) {throw new MojoExecutionException(e.getMessage());}
    	
    	try {FileUtils.deleteDirectory(fTmpDir);} catch (IOException e) {throw new MojoExecutionException(e.getMessage());}
    }
        
    private void executeIdentifierFactory(File fTmpDir) throws FileNotFoundException, UtilsConfigurationException
    {    	    	
    	getLog().info("\tpackageBase " +packageBase);
    	getLog().info("\tviewsXml " +viewsXml);
    	
    	File fPackage = new File(TxtFileNameFactory.build(srcDir, packageBase));
		
		jfIdentifier = new JavaSecurityViewIdentifierFactory(fTmpDir,fPackage,packageBase,classPrefix);
		jfIdentifier.processViews(viewsXml);
    }
    
    private void executeRestrictorFactory(File fTmpDir) throws FileNotFoundException, UtilsConfigurationException
    {    	
    	getLog().info("\tclassAbstractRestrictor " +classAbstractRestrictor);
    	getLog().info("\tclassRestrictor " +classRestrictor);
    	
    	File fRestrictorClass = new File(TxtFileNameFactory.build(srcDir, classRestrictor, "java"));
    	getLog().info("\tfRestrictorClass " +fRestrictorClass.getAbsolutePath());
		
    	restrictorFactory = new JavaSecurityViewRestrictorFactory(fTmpDir,fRestrictorClass,classRestrictor,classAbstractRestrictor,packageBase,classPrefix);
		restrictorFactory.processViews(viewsXml);
    }
    
    @Deprecated private void executeIdentifierFactoryOld(File fTmpDir) throws FileNotFoundException, UtilsConfigurationException
    {    	    	
    	getLog().info("\tpackageBase " +packageBase);
    	getLog().info("\tviewsXml " +viewsXml);
    	
    	File fPackage = new File(TxtFileNameFactory.build(srcDir, packageBase));
		
		jfIdentifier = new JavaSecurityViewIdentifierFactory(fTmpDir,fPackage,packageBase,classPrefix);
		jfIdentifier.processViewsOld(viewsXml);
    }
    
    @Deprecated private void executeRestrictorFactoryOld(File fTmpDir) throws FileNotFoundException, UtilsConfigurationException
    {    	
    	getLog().info("classAbstractRestrictor " +classAbstractRestrictor);
    	getLog().info("classRestrictor " +classRestrictor);
    	
    	File fRestrictorClass = new File(TxtFileNameFactory.build(srcDir, classRestrictor, "java"));
    	getLog().info("fRestrictorClass " +fRestrictorClass.getAbsolutePath());
		
		restrictorFactory = new JavaSecurityViewRestrictorFactory(fTmpDir,fRestrictorClass,classRestrictor,classAbstractRestrictor,packageBase,classPrefix);
		restrictorFactory.processViewsOld(viewsXml);
    }
    
    @Deprecated private void executeSeamPagesOld(File fTmpDir) throws FileNotFoundException, UtilsConfigurationException
    {
		File fSrcDir = new File(srcDir);
    	
    	JavaSecuritySeamPagesFactory seamPagesFactory = new JavaSecuritySeamPagesFactory(fTmpDir,classPrefix,fSrcDir,loginView,accessDeniedView,packageBase);
		seamPagesFactory.processViewsOld(viewsXml);
    }
}