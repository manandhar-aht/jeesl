package org.jeesl.interfaces.model.system.symbol;

import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;

public interface JeeslGraphicStyle extends JeeslOptionRestDownload
{
	public static enum Code{circle,square,triangle}
	public static enum Group{outer,inner}
	public static enum Color{outer,inner}
	public static enum Size{outer}
}