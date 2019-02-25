package net.sf.ahtutils.maven;

/**
 * Goal which compiles a set of JasperReports jrxml files to .jasper file. Creates a rtl language and a ltr language version of all reports.
 *
 * goal msgMenu
 * 
 * phase initialize
 */
public class UtilsMsgMenuGoal //extends AbstractMojo
{    
//    /**
//     * Location of the file.
//     * This the deprecated location: ${basedir}/src/main/resources/msg.${project.artifactId}
//     * @parameter expression="${basedir}/../doc/src/main/resources/msg.${project.parent.artifactId}"
//     * @required
//     */
//    private String msgSource;
//    
//    /**
//     * Location of the file.
//     * This the deprecated location: ${project.build.directory}/msg.${project.artifactId}
//     * @parameter expression="${basedir}/src/main/resources/db.${project.artifactId}/admin/security/views.xml"
//     * @required
//     */
//    private String viewXml;	
//    
//    public void execute() throws MojoExecutionException
//    {
//    	BasicConfigurator.configure();
//    	Logger.getRootLogger().setLevel(Level.ERROR);
//    	 
//    	getLog().info("msgSource: "+msgSource);
//    	getLog().info("viewXml: "+viewXml);
//    		
//    	File fView = new File(viewXml);
//    	if(!fView.exists()){throw new MojoExecutionException("view does not exist: "+fView.getAbsolutePath());}
//    	
//    	File fMsg = new File(msgSource);
//    	if(!fMsg.exists()){throw new MojoExecutionException("msg.dir exist: "+fMsg.getAbsolutePath());}
//    	
//    	File fMenu = new File(fMsg,"menu.xml");
//    	
//    	getLog().info("Creating menu.xml for MessageBundle");
//    	
//		try
//		{
//			Access access = JaxbUtil.loadJAXB(fView.getAbsolutePath(), Access.class);
//			Translations t = build(access);
//			JaxbUtil.save(fMenu, t, true);
//		}
//		catch (FileNotFoundException e) {throw new MojoExecutionException("view does not exist: "+fView.getAbsolutePath());}    	
//    }
//    
//    private Translations build(Access access)
//    {
//    	Translations translations = new Translations();
//    	
//    	for(Category cat : access.getCategory())
//    	{
//    		if(cat.isSetViews())
//    		{
//    			for(View view : cat.getViews().getView())
//    			{
//    				if(view.isSetLangs())
//    				{
//    					StringBuffer sb = new StringBuffer();
//    					sb.append("menu");
//    					sb.append(view.getCode().substring(0,1).toUpperCase());
//    					sb.append(view.getCode().substring(1,view.getCode().length()));
//    					
//    					
//    					Translation t = new Translation();
//    					t.setKey(sb.toString());
//    					t.setLangs(view.getLangs());;
//    					translations.getTranslation().add(t);
//    				}
//    				
//    			}
//    		}
//    	}
//    	
//    	return translations;
//    }

}