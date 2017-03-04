package org.jeesl.web.rest.system;

import org.jeesl.api.facade.io.JeeslIoTemplateFacade;
import org.jeesl.api.rest.system.io.template.JeeslIoTemplateRestExport;
import org.jeesl.api.rest.system.io.template.JeeslIoTemplateRestImport;
import org.jeesl.factory.xml.system.io.template.XmlTemplateFactory;
import org.jeesl.factory.xml.system.io.template.XmlTemplatesFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.system.io.template.Templates;
import org.jeesl.util.query.xml.StatusQuery;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class IoTemplateRestService <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
									SCOPE extends UtilsStatus<SCOPE,L,D>,
									DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
									TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>>
		extends AbstractJeeslRestService<L,D>
		implements JeeslIoTemplateRestExport,JeeslIoTemplateRestImport
{
	final static Logger logger = LoggerFactory.getLogger(IoTemplateRestService.class);
	
	private JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> fTemplate;
	
	private final Class<CATEGORY> cCategory;
	private final Class<TYPE> cType;
	private final Class<TEMPLATE> cTemplate;
	private final Class<SCOPE> cScope;
	private final Class<DEFINITION> cDefinition;
	private final Class<TOKEN> cToken;

	private XmlStatusFactory xfStatus;
	private XmlTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> xfTemplate;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
	
	private IoTemplateRestService(JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> fTemplate,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<TYPE> cType, final Class<TEMPLATE> cTemplate, final Class<SCOPE> cScope, final Class<DEFINITION> cDefinition, final Class<TOKEN> cToken)
	{
		super(fTemplate,cL,cD);
		this.fTemplate=fTemplate;
		
		this.cCategory=cCategory;
		this.cType=cType;
		this.cTemplate=cTemplate;
		this.cScope=cScope;
		this.cDefinition=cDefinition;
		this.cToken=cToken;
	
		xfStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
					SCOPE extends UtilsStatus<SCOPE,L,D>,
					DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
					TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>>
		IoTemplateRestService<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>
		factory(JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> fTemplate,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<TYPE> cType, final Class<TEMPLATE> cTemplate, final Class<SCOPE> cScope, final Class<DEFINITION> cDefinition, final Class<TOKEN> cToken)
	{
		return new IoTemplateRestService<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>(fTemplate,cL,cD,cCategory,cType,cTemplate,cScope,cDefinition,cToken);
	}
	
	@Override
	public Templates exportSystemIoTemplates()
	{
		Templates xml = XmlTemplatesFactory.build();
		
		return xml;
	}
	
	@Override public Container exportSystemIoTemplateCategories() {return xfContainer.build(fTemplate.allOrderedPosition(cCategory));}
	@Override public Container exportSystemIoTemplateTypes() {return xfContainer.build(fTemplate.allOrderedPosition(cType));}
	@Override public Container exportSystemIoTemplateScopes() {return xfContainer.build(fTemplate.allOrderedPosition(cScope));}
	
	
	@Override public DataUpdate importSystemIoTemplateCategories(org.jeesl.model.xml.jeesl.Container categories){return importStatus(cCategory,categories,null);}
	@Override public DataUpdate importSystemIoTemplateTypes(org.jeesl.model.xml.jeesl.Container types){return importStatus(cType,types,null);}
	@Override public DataUpdate importSystemIoTemplateScopes(Container scopes){return importStatus(cScope,scopes,null);}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Aht container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, clLang, clDescription));
        asdi.setFacade(fTemplate);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, clLang, clParent);
        asdi.deleteUnusedStatus(clStatus, clLang, clDescription);
        return dataUpdate;
    }
}