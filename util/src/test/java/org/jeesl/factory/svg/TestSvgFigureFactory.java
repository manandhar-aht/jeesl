package org.jeesl.factory.svg;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.jeesl.AbstractJeeslUtilTest;
import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.factory.ejb.system.status.EjbStatusFactory;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFigureFactory;
import org.jeesl.factory.factory.SvgFactoryFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
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

public class TestSvgFigureFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSvgFigureFactory.class);

	private EjbStatusFactory<GraphicStyle,Lang,Description> efStyle;
	private EjbGraphicFigureFactory<Lang,Description,Graphic,GraphicType,GraphicFigure,GraphicStyle> efFigure;
	private static SvgFigureFactory<Lang,Description,Graphic,GraphicType,GraphicFigure,GraphicStyle> svgF;
	
	private String colorRed = "FFFFFF";
	private String colorBlue = "FFFFFF";
	private String colorGreen = "FFFFFF";
	
	private GraphicStyle styleCircle,styleSquare;
	private GraphicFigure f1, f2, f3, f4;
	
	public TestSvgFigureFactory()
	{
		SvgFactoryFactory<Lang,Description,Graphic,GraphicType,GraphicFigure,GraphicStyle> ffSvg = SvgFactoryFactory.factory(Lang.class,Description.class,Graphic.class,GraphicFigure.class,GraphicStyle.class);
		efFigure = ffSvg.efFigure();
		efStyle = ffSvg.style();
		svgF = ffSvg.figure();

		init();
	}
	
	public void init()
	{
		styleCircle = efStyle.build(JeeslGraphicFigure.Style.circle);
		styleSquare = efStyle.build(JeeslGraphicFigure.Style.square);
		
		f1 = efFigure.build(styleCircle, true, 10, colorRed, 0, 0, 0);
		f2 = efFigure.build(styleCircle, false, 5, colorBlue, -5, 0, 0);
		f3 = efFigure.build(styleCircle, false, 5, colorGreen, 5, 0, 0);
		f4 = efFigure.build(styleSquare, false, 5, colorGreen, 0, 0, 45);
	}
	
	public void testA() throws IOException, TranscoderException
	{
		List<GraphicFigure> list = Arrays.asList(f1,f4);
		SVGGraphics2D g = svgF.build(list);
		
		File f = new File(fTarget,"a.svg");
		Svg2SvgTranscoder.transcode(g,f);
	}
	
	public void testB() throws IOException, TranscoderException
	{
		List<GraphicFigure> list = Arrays.asList(f1,f2,f3);
		SVGGraphics2D g = svgF.build(list);
		
		File f = new File(fTarget,"a.svg");
		Svg2SvgTranscoder.transcode(g,f);
	}
		
	public static void main(String[] args) throws TranscoderException, IOException, ParserConfigurationException
	{
		AbstractJeeslTest.initTargetDirectory();
		JeeslUtilTestBootstrap.init();
		TestSvgFigureFactory test = new TestSvgFigureFactory();
		
		test.testA();
		test.testB();
	}
}