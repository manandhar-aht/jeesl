package org.jeesl.controller.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.xml.io.File;
import net.sf.exlp.xml.io.Files;

public class JcrFileHandler implements Serializable
{
	private static final long serialVersionUID = 1L;

	private List<File> files; public List<File> getFiles() {return files;}
	private File file; public File getFile() {return file;} public void setFile(File file) {this.file = file;}
	
	public JcrFileHandler()
	{
		files = new ArrayList<File>();
	}
	
	public void reset()
	{
		this.files.clear();
		this.file=null;
	}
	
	public void reload(Files files)
	{
		reset();
		this.files.addAll(files.getFile());
	}
}