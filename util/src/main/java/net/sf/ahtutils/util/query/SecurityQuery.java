package net.sf.ahtutils.util.query;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.xml.system.security.XmlActionFactory;
import org.jeesl.factory.xml.system.security.XmlActionsFactory;
import org.jeesl.factory.xml.system.security.XmlUsecaseFactory;
import org.jeesl.factory.xml.system.security.XmlUsecasesFactory;
import org.jeesl.factory.xml.system.security.XmlViewFactory;
import org.jeesl.factory.xml.system.security.XmlViewsFactory;
import org.jeesl.model.xml.system.navigation.Navigation;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.security.Category;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.security.Usecase;
import net.sf.ahtutils.xml.security.User;
import net.sf.ahtutils.xml.status.Domain;

public class SecurityQuery
{
	public static enum Key {role,roleLabel,exStaff,categoryLabel}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case categoryLabel: q.setCategory(categoryLabel());
				default: break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Role role()
	{
		Role xml = new Role();
		xml.setId(0);
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		return xml;
	}
	
	public static Role roleLabel()
	{
		Role xml = new Role();
		xml.setId(0);
		xml.setCode("");
		xml.setLabel("");
		return xml;
	}
	
	public static User user()
	{
		User xml = new User();
		xml.setId(0);
		xml.setFirstName("");
		xml.setLastName("");
		return xml;
	}
	
	public static Staff exStaff()
	{
		Role role = new Role();
		role.setCode("");
		
		Domain domain = new Domain();
		domain.setId(0);
		
		User user = new User();
		user.setId(0);
		
		Staff xml = new Staff();
		xml.setRole(role);
		xml.setUser(user);
		xml.setDomain(domain);
		
		return xml;
	}
	
	public static Staff staffUserRole()
	{
		Staff xml = new Staff();
		xml.setRole(roleLabel());
		xml.setUser(user());
		
		return xml;
	}
	
	public static Category exCategory()
	{
		Category xml = new Category();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setDocumentation(true);
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		return xml;
	}
	
	public static Category categoryLabel()
	{
		Category xml = new Category();
		xml.setCode("");
		xml.setLabel("");
		return xml;
	}
	
	public static View exViewOld()
	{
		View xml = new View();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setDocumentation(true);
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		xml.setPublic(true);
		xml.setOnlyLoginRequired(true);
		
		xml.setNavigation(new Navigation());
		xml.getNavigation().setPackage("");
	
		
		return xml;
	}
	
	public static net.sf.ahtutils.xml.security.View exView()
	{
		net.sf.ahtutils.xml.security.View xml = new net.sf.ahtutils.xml.security.View();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setDocumentation(true);
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
//		xml.setPublic(true);
//		xml.setOnlyLoginRequired(true);
		
//		xml.setNavigation(new Navigation());
//		xml.getNavigation().setPackage("");

		return xml;
	}
	
	public static Role exRole()
	{
		Role xml = new Role();
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setDocumentation(true);
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		xml.setActions(XmlActionsFactory.build());xml.getActions().getAction().add(XmlActionFactory.build(""));
		xml.setViews(XmlViewsFactory.build());xml.getViews().getView().add(XmlViewFactory.build(""));
		xml.setUsecases(XmlUsecasesFactory.build());xml.getUsecases().getUsecase().add(XmlUsecaseFactory.build(""));
		return xml;
	}
	
	public static net.sf.ahtutils.xml.security.Action exAction()
	{
		net.sf.ahtutils.xml.security.Action xml = new net.sf.ahtutils.xml.security.Action();
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		return xml;
	}
	
	public static net.sf.ahtutils.xml.security.Template exTemplate()
	{
		net.sf.ahtutils.xml.security.Template xml = new net.sf.ahtutils.xml.security.Template();
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		return xml;
	}
	
	public static net.sf.ahtutils.xml.access.Action exActionAcl()
	{
		net.sf.ahtutils.xml.security.Template template = new net.sf.ahtutils.xml.security.Template();
		template.setCode("");
		
		net.sf.ahtutils.xml.access.Action xml = new net.sf.ahtutils.xml.access.Action();
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		xml.setTemplate(template);
		return xml;
	}
	
	public static net.sf.ahtutils.xml.access.Action docActionAcl()
	{
		net.sf.ahtutils.xml.security.Template template = new net.sf.ahtutils.xml.security.Template();
		template.setCode("");
		template.setLangs(StatusQuery.langs());
		template.setDescriptions(StatusQuery.descriptions());
		
		net.sf.ahtutils.xml.access.Action xml = new net.sf.ahtutils.xml.access.Action();
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		xml.setTemplate(template);
		return xml;
	}
	
	public static Usecase exUsecase()
	{
		net.sf.ahtutils.xml.security.Action action = new net.sf.ahtutils.xml.security.Action();action.setCode("");
		net.sf.ahtutils.xml.security.View view = new net.sf.ahtutils.xml.security.View();view.setCode("");
		
		Usecase xml = new Usecase();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setDocumentation(true);
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		xml.setActions(XmlActionsFactory.build());xml.getActions().getAction().add(action);
		xml.setViews(XmlViewsFactory.build());xml.getViews().getView().add(view);
		
		return xml;
	}
	
	public static Usecase docUsecase()
	{
		net.sf.ahtutils.xml.security.Template template = new net.sf.ahtutils.xml.security.Template();
		template.setCode("");
		template.setLangs(StatusQuery.langs());
		template.setDescriptions(StatusQuery.descriptions());
		
		net.sf.ahtutils.xml.security.View viewCode = new net.sf.ahtutils.xml.security.View();
		viewCode.setCode("");
		
		net.sf.ahtutils.xml.security.Action action = new net.sf.ahtutils.xml.security.Action();
		action.setCode("");
		action.setView(viewCode);
		action.setLangs(StatusQuery.langs());
		action.setDescriptions(StatusQuery.descriptions());
		action.setTemplate(template);
		
		Usecase xml = new Usecase();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setDocumentation(true);
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		xml.setActions(XmlActionsFactory.build());xml.getActions().getAction().add(action);
		xml.setViews(XmlViewsFactory.build());xml.getViews().getView().add(SecurityQuery.exView());
		
		return xml;
	}
}