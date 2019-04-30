package org.jeesl.factory.xml.system.io.report;

import java.util.List;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.DataAssociation;
import net.sf.ahtutils.xml.report.DataAssociations;
import net.sf.ahtutils.xml.report.ImportStructure;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class XmlImportStructureFactory <L extends UtilsLang,D extends UtilsDescription,
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
								STYLE extends JeeslReportStyle<L,D>,CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends UtilsStatus<TLS,L,D>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
								>
{
    final static Logger logger = LoggerFactory.getLogger(XmlImportStructureFactory.class);

    /**
    * Creates an import structure for the given target class and the data associations.
    * @param targetClass The class to hold the properties to be imported from spreadsheet
    * @param associations Definitions of what information will be imported to which property using which strategy and validation method
    * @return Initialized import structure
    */
    public static ImportStructure build(Class targetClass, List<DataAssociation> associations) throws InstantiationException, IllegalAccessException
    {
        ImportStructure structure = new ImportStructure();
        DataAssociations associationsContainer = XmlDataAssociationsFactory.build();

        // Validate existence of given property in targetclass
        PropertyUtilsBean propertyUtil = new PropertyUtilsBean();
        Object test = targetClass.newInstance();
        for (DataAssociation association : associations)
        {
            associationsContainer.getDataAssociation().add(association);
            if (propertyUtil.isWriteable(test, association.getProperty())) 
            {
                if (logger.isTraceEnabled()){logger.trace("Added " +association.getProperty() + " to be imported from column " +association.getColumn());}
            }
            else
            {
                if (logger.isTraceEnabled()){logger.warn("The property " +association.getProperty() +" is not a writable field of " +targetClass.getName() +". Please fix your configuration!");}
            }
        }

        structure.setTargetClass(targetClass.getName());
        structure.setDataAssociations(associationsContainer);
        return structure;
    }
}