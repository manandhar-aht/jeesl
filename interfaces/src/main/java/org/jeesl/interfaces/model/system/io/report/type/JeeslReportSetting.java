package org.jeesl.interfaces.model.system.io.report.type;

public interface JeeslReportSetting
{
	public static enum Type{implementation,filling,transformation}
	
	public static enum Filling{flat,hierarchical}
	public static enum Transformation{none,last}
	public static enum Implementation{flat,tree,model}
}