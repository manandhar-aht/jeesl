package org.jeesl.controller.processor;

import org.jdom2.Document;
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
    	Inventory i = JaxbUtil.loadJAXB(mrl.searchIs("data/processor/inventory/winaudit.xml"), Inventory.class);
    	JaxbUtil.info(i);

    	Document doc = JDomUtil.load(mrl.searchIs("data/processor/inventory/winaudit.xml"));
    	JDomUtil.debug(doc);
    }
}