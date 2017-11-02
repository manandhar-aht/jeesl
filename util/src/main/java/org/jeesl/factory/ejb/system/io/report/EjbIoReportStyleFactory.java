package org.jeesl.factory.ejb.system.io.report;

import java.util.UUID;

import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportLayout;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Style;
import net.sf.ahtutils.xml.symbol.Color;

public class EjbIoReportStyleFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
								SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
								GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
								COLUMN extends JeeslReportColumn<L,D,GROUP,STYLE,CDT,CW,TLS>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								TEMPLATE extends JeeslReportTemplate<L,D,CELL>,
								CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								STYLE extends JeeslReportStyle<L,D>,CDT extends UtilsStatus<CDT,L,D>,
								CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends UtilsStatus<TLS,L,D>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportStyleFactory.class);
	
	final Class<STYLE> cStyle;
	
	private JeeslDbLangUpdater<STYLE,L> dbuLang;
	private JeeslDbDescriptionUpdater<STYLE,D> dbuDescription;
    
	public EjbIoReportStyleFactory(final Class<L> cL,final Class<D> cD,final Class<STYLE> cStyle)
	{       
        this.cStyle = cStyle;
        
        dbuLang = JeeslDbLangUpdater.factory(cStyle,cL);
        dbuDescription = JeeslDbDescriptionUpdater.factory(cStyle,cD);
	}
	    
	public STYLE build()
	{
		STYLE ejb = null;
		try
		{
			ejb = cStyle.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public STYLE build(Style xStyle)
	{
		STYLE ejb = null;
		try
		{
			ejb = cStyle.newInstance();
			ejb.setCode(xStyle.getCode());
			ejb = update(ejb,xStyle);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public STYLE update(STYLE eStyle, Style xStyle)
	{
		eStyle.setPosition(xStyle.getPosition());
		eStyle.setVisible(xStyle.isVisible());
		
		reset(eStyle, true);
		for(Color color : xStyle.getLayout().getColor())
		{
			switch(JeeslReportLayout.Color.valueOf(color.getGroup()))
			{
				case background: eStyle.setColorBackground(color.getValue());break;
				default: break;
			}
		}
		
		return eStyle;
	}
	
	private void reset(STYLE eStyle, boolean colors)
	{
		eStyle.setColorBackground(null);
		eStyle.setColorFont(null);
		eStyle.setColorBorderTop(null);
		eStyle.setColorBorderLeft(null);
		eStyle.setColorBorderRight(null);
		eStyle.setColorBorderBottom(null);
		
	}
	
	public STYLE updateLD(UtilsFacade fUtils, STYLE eStyle, Style xStyle) throws UtilsConstraintViolationException, UtilsLockingException
	{
		eStyle=dbuLang.handle(fUtils, eStyle, xStyle.getLangs());
		eStyle = fUtils.save(eStyle);
		
		eStyle=dbuDescription.handle(fUtils, eStyle, xStyle.getDescriptions());
		eStyle = fUtils.save(eStyle);
		
		return eStyle;
	}
}