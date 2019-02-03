package org.jeesl.jsf.components.output;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent(value="org.jeesl.jsf.components.output.Dialog")
public class Dialog extends UINamingContainer
{
	public String styleClass(String size)
	{
		return "jeeslDialog_"+size;
	}
}