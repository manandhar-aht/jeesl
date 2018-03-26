package org.jeesl.util.comparator.ejb.system.io.template;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoTemplateTokenComparator<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(IoTemplateTokenComparator.class);

    public enum Type {position};

    public IoTemplateTokenComparator()
    {
    	
    }
    
    public Comparator<TOKEN> factory(Type type)
    {
        Comparator<TOKEN> c = null;
        IoTemplateTokenComparator<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> factory = new IoTemplateTokenComparator<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<TOKEN>
    {
        public int compare(TOKEN a, TOKEN b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getPosition(), b.getPosition());
			  ctb.append(a.getCode(), b.getCode());
			  return ctb.toComparison();
        }
    }
}