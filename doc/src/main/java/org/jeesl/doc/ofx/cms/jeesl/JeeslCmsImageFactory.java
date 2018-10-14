package org.jeesl.doc.ofx.cms.jeesl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.xmlbeans.impl.common.IOUtil;
import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryHandler;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.openfuxml.content.media.Image;
import org.openfuxml.factory.xml.media.XmlImageFactory;
import org.openfuxml.factory.xml.media.XmlMediaFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.exlp.util.xml.JaxbUtil;

public class JeeslCmsImageFactory<E extends JeeslIoCmsElement<?,?,?,?,C,FC>,
								C extends JeeslIoCmsContent<?,E,?>,
								FS extends JeeslFileStorage<?,?,?>,
								FC extends JeeslFileContainer<FS,?>,
								FM extends JeeslFileMeta<FC,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslCmsImageFactory.class);
	
	private final JeeslFileRepositoryHandler<FS,FC,FM> frh;
	private final JeeslMarkupFactory ofxMarkup;
	
	public JeeslCmsImageFactory(JeeslFileRepositoryHandler<FS,FC,FM> frh)
	{
		this.frh=frh;
		ofxMarkup = new JeeslMarkupFactory();
	}


	
	public Image build(String localeCode, E element)
	{
		logger.info("Building Image ");
		Image xml = XmlImageFactory.centerPercent(element.getId(), 80);
		xml.setTitle(XmlTitleFactory.build("Test"));
		
		xml.setMedia(XmlMediaFactory.build(element.getId()+".png",element.getId()+".png"));
		
		try
		{
			frh.init(element,false);
			List<FM> metas = frh.getMetas();
			for(FM m : metas)
			{
				try
				{
					logger.info(m.toString()+" "+m.getType().getCode());
					File fDir = new File("/Volumes/ramdisk/dev/srs/png");
					File f = new File(fDir,element.getId()+".png");
					IOUtil.copyCompletely(frh.download(m), new FileOutputStream(f));
				}
				catch (FileNotFoundException e) {e.printStackTrace();}
				catch (IOException e) {e.printStackTrace();}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
			
		}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		catch (UtilsLockingException e) {e.printStackTrace();}
		
		JaxbUtil.info(xml);
		
		return xml;
	}
}