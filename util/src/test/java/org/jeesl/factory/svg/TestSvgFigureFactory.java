package org.jeesl.factory.svg;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.transcoder.TranscoderException;
import org.jeesl.AbstractJeeslUtilTest;
import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.factory.factory.SvgFactoryFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;
import org.jeesl.model.ejb.system.symbol.Graphic;
import org.jeesl.model.ejb.system.symbol.GraphicFigure;
import org.jeesl.model.ejb.system.symbol.GraphicStyle;
import org.jeesl.model.ejb.system.symbol.GraphicType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.test.AbstractJeeslTest;

public class TestSvgFigureFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSvgFigureFactory.class);

	private SvgFigureFactory<Lang,Description,Graphic,GraphicType,GraphicFigure,GraphicStyle> svgF;
	private EjbStatusFactory<GraphicStyle,Lang,Description> efStyle;
	
	private GraphicStyle styleCircle,styleSquare;
	private GraphicFigure f1, f2, f3, f4;
	
	public TestSvgFigureFactory()
	{
		SvgFactoryFactory<Lang,Description,Graphic,GraphicType,GraphicFigure,GraphicStyle> ffSvg = SvgFactoryFactory.factory(Lang.class,Description.class,Graphic.class,GraphicFigure.class,GraphicStyle.class);
		svgF = ffSvg.figure();
		efStyle = ffSvg.style();
		
		 init();
	}
	
	public void init()
	{
		styleCircle = efStyle.build(JeeslGraphicFigure.Style.circle);
		styleSquare = efStyle.build(JeeslGraphicFigure.Style.square);
		
//		f1 = svgF.b
	}
	
	
	public void testA()
	{
		
	}
	
	public static void main(String[] args) throws TranscoderException, IOException, ParserConfigurationException
	{
		AbstractJeeslTest.initTargetDirectory();
		JeeslUtilTestBootstrap.init();
		TestSvgFigureFactory test = new TestSvgFigureFactory();
		AbstractJeeslTest.initTargetDirectory();
		
//		test.circle();
		test.init();
	}
}