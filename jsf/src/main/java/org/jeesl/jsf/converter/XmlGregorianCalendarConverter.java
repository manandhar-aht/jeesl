package org.jeesl.jsf.converter;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import javax.xml.datatype.XMLGregorianCalendar;

@FacesConverter("org.jeesl.jsf.converter.XmlGregorianCalendarConverter")
public class XmlGregorianCalendarConverter extends DateTimeConverter implements Converter
{
	 private String dateStyle = "default";
	 private Locale locale = null;
	 private String pattern = null;
	 private String timeStyle = "default";
	 private TimeZone timeZone = TimeZone.getDefault();
	 private String type = "date";

	 @Override
	 public Object getAsObject(FacesContext context, UIComponent component, String newValue)
	 {
	     return null;
	 }

	 @Override
	 public String getAsString(FacesContext context, UIComponent component, Object value)
	 {
	     Map<String, Object> attributes = component.getAttributes();
	     
	     if(attributes.containsKey("pattern")){pattern = (String) attributes.get("pattern");}
	     setPattern(pattern);
	     
	     if(attributes.containsKey("locale")){locale = (Locale) attributes.get("locale");}
	     setLocale(locale);
	     
	     if(attributes.containsKey("timeZone")){timeZone = (TimeZone) attributes.get("timeZone");}
	     setTimeZone(timeZone);
	     
	     if(attributes.containsKey("dateStyle")){dateStyle = (String) attributes.get("dateStyle");}
	     setDateStyle(dateStyle);
	     
	     if(attributes.containsKey("timeStyle")){timeStyle = (String) attributes.get("timeStyle");}
	     setTimeStyle(timeStyle);
	     
	     if(attributes.containsKey("type")){type = (String) attributes.get("type");}
	     setType(type);

	     XMLGregorianCalendar xmlGregCal = (XMLGregorianCalendar) value;
	     Date date = xmlGregCal.toGregorianCalendar().getTime();
	     return super.getAsString(context,component,date);
	 }
}