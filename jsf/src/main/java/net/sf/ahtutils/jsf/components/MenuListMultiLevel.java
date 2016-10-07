package net.sf.ahtutils.jsf.components;

import java.util.ArrayList;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.jeesl.model.xml.system.navigation.MenuItem;

@FacesComponent(value="net.sf.ahtutils.jsf.components.MenuListMultiLevel")
public class MenuListMultiLevel extends UINamingContainer
{
	
	private ArrayList<ArrayList<MenuItem>> subMenus;
	private ArrayList<MenuItem> submenusInLevel;
	
	public ArrayList<ArrayList<MenuItem>> submenus(MenuItem toplevel)
	{
		subMenus=new ArrayList<ArrayList<MenuItem>>();
		for (MenuItem item : toplevel.getMenuItem())
        {
        	submenusInLevel = new ArrayList<MenuItem>();
        	submenusInLevel.add(item);
        	for (MenuItem submenuItem : item.getMenuItem())
        	{
        		processMenu(submenuItem);
        	}
        	subMenus.add(submenusInLevel);
        }
        return subMenus;
	}
	
	public void processMenu(MenuItem menu)
	{
		submenusInLevel.add(menu);
		for (int i=0; i < menu.getMenuItem().size(); i++)
        {
			processMenu(menu.getMenuItem().get(i));
        }
	}
}