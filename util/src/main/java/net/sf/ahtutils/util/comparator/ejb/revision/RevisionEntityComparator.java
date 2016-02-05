package net.sf.ahtutils.util.comparator.ejb.revision;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;

public class RevisionEntityComparator<L extends UtilsLang,D extends UtilsDescription,
										RC extends UtilsStatus<RC,L,D>,
										RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA>,
										RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
										RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA>,
										RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA>,
										REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
										RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(RevisionEntityComparator.class);

    public enum Type {position};

    public RevisionEntityComparator()
    {
    	
    }
    
    public Comparator<RE> factory(Type type)
    {
        Comparator<RE> c = null;
        RevisionEntityComparator<L,D,RC,RV,RVM,RS,RE,REM,RA> factory = new RevisionEntityComparator<L,D,RC,RV,RVM,RS,RE,REM,RA>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<RE>
    {
        public int compare(RE a, RE b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());
			  ctb.append(a.getPosition(), b.getPosition());
			  return ctb.toComparison();
        }
    }
}