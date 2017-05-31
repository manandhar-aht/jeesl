package org.jeesl.doc.er;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.configuration.Configuration;
import org.metachart.processor.graph.Graph2DotConverter;
import org.metachart.xml.graph.Graph;
import org.metachart.xml.graph.Node;
import org.openfuxml.media.transcode.Svg2PdfTranscoder;
import org.openfuxml.renderer.latex.OfxMultiLangLatexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractErDiagram
{
	final static Logger logger = LoggerFactory.getLogger(AbstractErDiagram.class);
	
	protected File fTmp;
	protected File fSrc,fDot,fSvg;
	protected File dPdf,dAtt;
	
	protected String packages;
	protected String colorScheme;
	
	private Configuration config;
	private OfxMultiLangLatexWriter ofxWriter;
	
	public AbstractErDiagram(Configuration config,OfxMultiLangLatexWriter ofxWriter)
	{
		this.config=config;
		this.ofxWriter=ofxWriter;
		
		fTmp = new File(config.getString(ConfigKey.dirTmp));
		logger.info("Using Tmp: "+fTmp);
	}
	
	protected void create(String key) throws ClassNotFoundException, IOException, TranscoderException
	{
		List<String> subset = new ArrayList<String>();
		subset.add(key);
		File fPdf = null;if(dPdf!=null){fPdf = new File(dPdf,key+".pdf");}
		buildSvg("neato",subset,new File(fSvg,key+".svg"),fPdf);
	}
	
	protected void buildSvg(String type, List<String> subset,File fDst, File fPdf) throws ClassNotFoundException, IOException, TranscoderException
	{
		ErAttributesProcessor eap = new ErAttributesProcessor(ofxWriter,config,fSrc);
		eap.addPackages(packages);
		
		ErGraphProcessor egp = new ErGraphProcessor(fSrc);
		egp.addPackages(packages,subset);
		
		Graph g = egp.create();
//		JaxbUtil.info(g);System.exit(-1);
		
		Node xml = JaxbUtil.loadJAXB(colorScheme, Node.class);
		JaxbUtil.info(xml);
		
		Graph2DotConverter gdc = new Graph2DotConverter("b");
		gdc.setColorScheme(xml);
		
		gdc.setOverlap(false);
		gdc.setRatio(0.9);
		gdc.setRanksep(0.2);
		
		gdc.convert(g);
		gdc.save(fDot);
		
		ErImageWriter w = new ErImageWriter(type);
		w.svg(fDot, fDst);
		
		if(fPdf!=null)
		{
			logger.info("SVG-PDF");
			Svg2PdfTranscoder.transcode(fDst,fPdf);
			logger.info("SVG-PDF done");
		}
	}
}