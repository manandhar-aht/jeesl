package org.jeesl.factory.svg;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.jeesl.AbstractJeeslUtilTest;
import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;
import org.jeesl.model.ejb.system.symbol.Graphic;
import org.jeesl.model.ejb.system.symbol.GraphicFigure;
import org.jeesl.model.ejb.system.symbol.GraphicStyle;
import org.jeesl.model.ejb.system.symbol.GraphicType;
import org.openfuxml.media.transcode.Svg2SvgTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractJeeslTest;

public class TestSvgSymbolFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSvgSymbolFactory.class);

	private SvgSymbolFactory<Lang,Description,Graphic,GraphicType,GraphicFigure,GraphicStyle> svgF;
	
	public TestSvgSymbolFactory()
	{
		svgF = SvgSymbolFactory.factory();
	}
	
	public void circle() throws IOException, TranscoderException
	{
		Graphic rule = new Graphic();
		for(int i=0;i<=12;i++)
		{
			rule.setSize(i);
			SVGGraphics2D g = svgF.build(12,rule);
			byte[] bytes = Svg2SvgTranscoder.transcode(g);
			FileUtils.writeByteArrayToFile(new File(fTarget,"circle-"+i+".svg"), bytes);
		}
	}
	
	public void square() throws IOException, TranscoderException
	{
		Graphic rule = new Graphic();
		for(int i=0;i<=12;i++)
		{
			rule.setSize(i);
			SVGGraphics2D g = svgF.square(12,rule); //@Lasse, only this this method, otherwise it will influence our production systems
			byte[] bytes = Svg2SvgTranscoder.transcode(g);
			FileUtils.writeByteArrayToFile(new File(fTarget,"square-"+i+".svg"), bytes);
		}
	}
	
	public static void main(String[] args) throws TranscoderException, IOException, ParserConfigurationException
	{
		AbstractJeeslTest.initTargetDirectory();
		JeeslUtilTestBootstrap.init();
		TestSvgSymbolFactory test = new TestSvgSymbolFactory();
		AbstractJeeslTest.initTargetDirectory();
		
//		test.circle();
		test.square();
	}
}