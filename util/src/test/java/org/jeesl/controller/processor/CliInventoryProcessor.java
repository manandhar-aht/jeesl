
package org.jeesl.controller.processor;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
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
	static Document doc;
	static Namespace ns;
	static List<Element> sW, sys, upds, uItem;
		
	public static void main(String[] args) throws Exception
	{
		JeeslUtilTestBootstrap.init();
		PcInventoryProcessor clip = new PcInventoryProcessor();
		Inventory inventory = new Inventory();
		
		String s = StringIO.loadTxt("data/txt/inventory.txt");
		
//		inventory.getComputer().add(clip.inventoryPC("F:/etc/wa/NBTK_report_10.01.2008.xml"));
		inventory.getComputer().add(PcInventoryPostProcessor.postProcess(clip.inventoryPC(s)));
		
		JaxbUtil.info(inventory);
		
//		FileOutputStream fos = new FileOutputStream("F:\\testinventory.xml");
//		JAXB.marshal(inventory, fos);
	}
}
