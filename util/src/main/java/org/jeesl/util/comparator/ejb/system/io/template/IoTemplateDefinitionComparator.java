package org.jeesl.util.comparator.ejb.system.io.template;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoTemplateDefinitionComparator<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(IoTemplateDefinitionComparator.class);

    public enum Type {position};

    public IoTemplateDefinitionComparator()
    {
    	
    }
    
    public Comparator<DEFINITION> factory(Type type)
    {
        Comparator<DEFINITION> c = null;
        IoTemplateDefinitionComparator<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> factory = new IoTemplateDefinitionComparator<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<DEFINITION>
    {
        public int compare(DEFINITION a, DEFINITION b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getType().getPosition(),b.getType().getPosition());
			  return ctb.toComparison();
        }
    }
}