package org.jeesl.util.query.xml;

import java.io.FileFilter;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class FileQuery
{
	public static FileFilter sql()
	{
		IOFileFilter ffDir = FileFilterUtils.directoryFileFilter();
		IOFileFilter ffFile = FileFilterUtils.fileFileFilter();
		IOFileFilter ffSql  = FileFilterUtils.suffixFileFilter(".sql");
		
		IOFileFilter ff = FileFilterUtils.and(ffFile,ffSql);
		        
		return FileFilterUtils.or(ffDir, ff);
	}
}