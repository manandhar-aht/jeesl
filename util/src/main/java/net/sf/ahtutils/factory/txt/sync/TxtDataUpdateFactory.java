package net.sf.ahtutils.factory.txt.sync;

import net.sf.ahtutils.xml.sync.DataUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtDataUpdateFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtDataUpdateFactory.class);
		
	public static String debug(DataUpdate du)
	{
		StringBuffer sb = new StringBuffer();
		
		if(du.isSetType())
		{
			if(du.getType().isSetLabel()){sb.append(du.getType().getLabel());}
			if(du.getType().isSetCode())
			{
				try
				{
					Class<?> c;
					c = Class.forName(du.getType().getCode());
					sb.append(" ").append(c.getSimpleName());
				}
				catch (ClassNotFoundException e) {}
			}
		}
		
		if(du.isSetResult() && du.getResult().isSetStatus() && du.getResult().getStatus().isSetCode())
		{
			sb.append(" [");
			sb.append(du.getResult().getStatus().getCode());
			sb.append("]");
		}
		
		if(du.isSetResult() && du.getResult().isSetSuccess() && du.getResult().isSetTotal() && du.getResult().getTotal()>0)
		{
			sb.append(" ").append(du.getResult().getSuccess());
			sb.append("/"+du.getResult().getTotal());
			if(du.getResult().isSetSkip()){sb.append(" (skipped:").append(du.getResult().getSkip()).append(")");}
		}
		
		return sb.toString();
	}
}