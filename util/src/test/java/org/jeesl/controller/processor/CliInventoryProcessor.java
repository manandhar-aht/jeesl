package org.jeesl.controller.processor;

import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.controller.processor.pcinventory.PcInventoryPostProcessor;
import org.jeesl.controller.processor.pcinventory.PcInventoryProcessor;
import org.jeesl.model.xml.module.inventory.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliInventoryProcessor
{
	final static Logger logger = LoggerFactory.getLogger(CliInventoryProcessor.class);
		
	public static void main(String[] args) throws Exception
	{
		JeeslUtilTestBootstrap.init();
		Inventory inventory = new Inventory();
		
		String s = StringIO.loadTxt("data/txt/inventory.txt");
		
		inventory.getComputer().add(PcInventoryPostProcessor.postProcess(PcInventoryProcessor.transform(s)));
		
		JaxbUtil.info(inventory);
	}
}