package org.jeesl.controller.processor;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.jdom2.Document;
import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.model.xml.module.inventory.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;


public class CliInventoryProcessor {
	
	final static Logger logger = LoggerFactory.getLogger(CliInventoryProcessor.class);
	
    public static void main(String[] args) throws Exception
    {
    	Configuration config = JeeslUtilTestBootstrap.init();

    	File f = new File(config.getString("cli.inventory.pc.winaudit"));
    	Inventory i = JaxbUtil.loadJAXB(f, Inventory.class);
    	JaxbUtil.info(i);
    	
    	    Document doc = JDomUtil.load(f);
    	    JDomUtil.debug(doc);
    }
   
}
        
    