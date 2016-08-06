package net.sf.ahtutils.factory.svg;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.jeesl.JeeslUtilTestBootstrap;
import org.openfuxml.media.transcode.Svg2PngTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.ejb.symbol.Graphic;
import net.sf.ahtutils.model.ejb.symbol.GraphicStyle;
import net.sf.ahtutils.model.ejb.symbol.GraphicType;

public class TestSvgSymbolFactory
{
	final static Logger logger = LoggerFactory.getLogger(TestSvgSymbolFactory.class);

	private SvgSymbolFactory<Lang,Description,Graphic,GraphicType,GraphicStyle> svgF;
	
	public TestSvgSymbolFactory()
	{
		svgF = SvgSymbolFactory.factory();
	}
	
	public void test() throws IOException, TranscoderException
	{
		Graphic rule = new Graphic();
		for(int i=0;i<=12;i++)
		{
			rule.setSize(i);
			SVGGraphics2D g = svgF.build(12,rule);
			byte[] bytes = Svg2PngTranscoder.transcode(g);
			FileUtils.writeByteArrayToFile(new File("/Volumes/ramdisk/"+i+".png"), bytes);
		}
	}
	
	public static void main(String[] args) throws TranscoderException, IOException, ParserConfigurationException
	{
		JeeslUtilTestBootstrap.init();
		TestSvgSymbolFactory test = new TestSvgSymbolFactory();
		test.test();
	}
}