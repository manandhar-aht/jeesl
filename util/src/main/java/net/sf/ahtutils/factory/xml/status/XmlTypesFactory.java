package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.xml.status.Types;

public class XmlTypesFactory
{
	public static Types build()
	{
		Types xml = new Types();
		return xml;
	}
	
	public static Types build(String group)
	{
		Types xml = new Types();
		xml.setGroup(group);
		return xml;
	}
}