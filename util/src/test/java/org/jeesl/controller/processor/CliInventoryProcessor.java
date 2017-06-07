package org.jeesl.controller.processor;

import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.jxpath.JXPathAbstractFactoryException;
import org.apache.poi.poifs.property.Child;
import org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchProfileElement.JaxbFetch;
import org.hibernate.internal.jaxb.mapping.orm.JaxbFieldResult;
import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.Text;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.util.IteratorIterable;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.jaxen.JaxenXPathFactory;
import org.jdom2.filter.ContentFilter;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filter;
import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.model.xml.module.inventory.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliInventoryProcessor
{
	final static Logger logger = LoggerFactory.getLogger(CliInventoryProcessor.class);
	
	public static void main(String[] args) throws Exception
    {
    	JeeslUtilTestBootstrap.init();

    	MultiResourceLoader mrl = new MultiResourceLoader();
    	//Inventory i = JaxbUtil.loadJAXB(mrl.searchIs("data/processor/inventory/winaudit.xml"), Inventory.class);
    	
   // 	Inventory i = JaxbUtil.loadJAXB(mrl.searchIs("C:/Users/Petersen/Documents/WINAUDIT/_report.xml"), Inventory.class);
   // 	JaxbUtil.info(i);
   	
   	
   // 	ContentFilter filter = new ContentFilter(ContentFilter.TEXT);
    	Document doc = JDomUtil.load(mrl.searchIs("data/processor/inventory/winaudit.xml"));
 //   	JDomUtil.debug(doc);
    	
    	Namespace ns = Namespace.getNamespace("power", "http://schemas.microsoft.com/powershell/2004/04");
    	// 	Element root = doc.getRootElement();
    	//   	List <Element> listChildren = root.getChildren();  
    	XPathFactory xFactory = XPathFactory.instance();

    	XPathExpression<Element> expr = xFactory.compile("//power:Obj",Filters.element(),null,ns);
    	
        List<Element> links = expr.evaluate(doc);
        for (Element e : links)
        {
            logger.debug(e.getName());
        }
    	
    	
   //     Element firstTitle = xFactory.compile("//S::item/descendant::ItemName", Filters.element()).evaluateFirst(doc);
   //     System.out.println(firstTitle.getValue());
        
        
   // 	Element child = listChildren.get(2);
   // 	Attribute a = child.getAttribute( "ItemName" );
   // 	String aName    =  child.getAttribute( "CategoryID" ).getValue();
   // 	System.out.println(albertName);
    	//JaxbUtil.info(listChildren);
//    	JDomUtil.debug(doc);
    	
    	//JaxbUtil.info(doc.getProperty("3"));

    }
}