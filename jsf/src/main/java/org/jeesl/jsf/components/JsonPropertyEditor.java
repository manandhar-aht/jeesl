package org.jeesl.jsf.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import net.sf.exlp.util.io.JsonUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.jeesl.util.SetterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.jeesl.jsf.components.JsonPropertyEditor")
public class JsonPropertyEditor extends UIInput implements NamingContainer 
{
	final static Logger logger = LoggerFactory.getLogger(JsonPropertyEditor.class);
	
	private Map<String, Object> properties;
	public Map<String, Object> getProperties() {return properties;}
	public void setProperties(Map<String, Object> properties) {this.properties = properties;}
	
	private List<String> propertyNames;
	public List<String> getPropertyNames() {return propertyNames;}
	public void setPropertyNames(List<String> propertyNames) {this.propertyNames = propertyNames;}
	
	private Object jsonObject;
	public Object getJsonObject() {return jsonObject;}
	public void setJsonObject(Object jsonObject) {this.jsonObject = jsonObject;}
	
	private String value;
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
	
	@Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }
	
    public void init() throws IOException
    {
		try {
			logger.info("Initiating JSON property editor component");
			Class targetClass = Class.forName(getAttributes().get("for").toString());
			if (jsonObject == null)
			{
				logger.info("No object present - constructing new one.");
				jsonObject = (Object) targetClass.newInstance();
			}
			else
			{
				logger.info("State restored.");
			}
			logger.info("JSON Object is of type: " +jsonObject);
			properties = new HashMap<String, Object>();
			propertyNames = new ArrayList<String>();
			for (PropertyDescriptor desc : PropertyUtils.getPropertyDescriptors(jsonObject))
			{
				if (desc.getWriteMethod() != null)
				{
					Object currentValue = null;
					try {
						currentValue = desc.getReadMethod().invoke(jsonObject);
					} catch (IllegalAccessException ex) {
						logger.debug(ex.getMessage());
					} catch (IllegalArgumentException ex) {
						logger.debug(ex.getMessage());
					} catch (InvocationTargetException ex) {
						logger.debug(ex.getMessage());
					}
					properties.put(desc.getName(), currentValue);
					propertyNames.add(desc.getName());
				}
				else
				{
					logger.warn("No SETTER available for property " +desc.getName() +" - skipping");
				}
			}
			logger.info("Properties has " +properties.size() +" items: " +properties.toString());
		} catch (ClassNotFoundException ex) {
			logger.debug(ex.getMessage());
		} catch (InstantiationException ex) {
			logger.debug(ex.getMessage());
		} catch (IllegalAccessException ex) {
			logger.debug(ex.getMessage());
		}
	}
	
	
    @Override
    public Object getSubmittedValue() {
        return value;
    }

    @Override
    protected Object getConvertedValue(FacesContext context, Object submittedValue) {
        	logger.info("Converterd Value: " +submittedValue.toString());
            return submittedValue;
    }
	
	public void process(javax.faces.event.AjaxBehaviorEvent event) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		for (String propertyName : properties.keySet())
		{
			logger.info("Processing " +propertyName);
			Object argument = new Object();
			try {
				argument = properties.get(propertyName);
				if (argument!=null)
				{
					logger.info("Saving " +argument.toString());
					SetterHelper.set(propertyName, jsonObject, argument);
				}
			}
			catch (Exception e)
			{
				logger.error(e.getStackTrace().toString());
				logger.error(e.getMessage());
			}
		}
		value = JsonUtil.toString(jsonObject);
		this.getAttributes().put("value", value);
		logger.info("Value set to " +this.getAttributes().get("value").toString());
	}	
	
	
    public void listener(javax.faces.event.ActionEvent e)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression ajaxEventListener = (MethodExpression) getAttributes().get("listener");
        ajaxEventListener.invoke(context.getELContext(), new Object[] {});
    }
    
    public void listener()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression ajaxEventListener = (MethodExpression) getAttributes().get("listener");
        ajaxEventListener.invoke(context.getELContext(), new Object[] {});
    }
	
	@Override
	public Object saveState(FacesContext context)
	{
		Object[] rtrn = new Object[3];
		if (this.isRendered())
		{
			rtrn[0] = jsonObject;
			rtrn[1] = value;
			rtrn[2] = properties;
		}
		return rtrn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void restoreState(FacesContext context, Object state)
	{
		if(this.isRendered())
		{
			Object[] storedState = (Object[]) state;
		    logger.debug("Restoring state.");
			try
			{
				jsonObject       = (Object)					storedState[0];
				value			 = (String)					storedState[1];
				properties		 = (Map<String, Object>)	storedState[2];
			}
			catch (Exception e)
			{
				logger.error("Exception when restoring: " +e.getMessage());
			}
		}
	}
}