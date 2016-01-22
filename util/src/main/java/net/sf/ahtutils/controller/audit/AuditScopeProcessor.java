package net.sf.ahtutils.controller.audit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.xml.audit.Change;
import net.sf.ahtutils.xml.audit.Revision;
import net.sf.ahtutils.xml.audit.Scope;
import net.sf.exlp.util.xml.JaxbUtil;

public class AuditScopeProcessor
{
	public AuditScopeProcessor()
	{
		
	}

    public List<Scope> group(List<Change> changes)
    {
        List<Scope> scopes = new ArrayList<Scope>();
        LinkedHashMap<String,Scope> tmp = new LinkedHashMap<String,Scope>();

        for (Change change : changes)
        {
        	JaxbUtil.trace(change);
            tmp.put(change.getScope().getId() + "/" + change.getScope().getClazz(), change.getScope());
        }

        for(Map.Entry<String, Scope> me : tmp.entrySet())
        {
            modificationWithChanges(changes, me, "Change");
            scopes.add(me.getValue());
        }
        deleteScopes(changes);

        return scopes;
    }

    private void modificationWithChanges(List<Change> changes, Map.Entry<String, Scope> me, String elementToAdd)
    {
        for (Change ch: changes)
        {
            // add change to scope
            if(elementToAdd.equals("Change"))
            {
                if (compareScopes(ch.getScope(), me)) {me.getValue().getChange().add(ch);}
            }
            // add scope to change
            if(elementToAdd.equals("Scope"))
            {
                for(Change c : me.getValue().getChange())
                {
                    if(compareChanges(ch, c)) {ch.setScope(me.getValue());}
                }
            }
        }
    }

    private void modificationWithRevisions(List<Revision> revisions, Map.Entry<String, Scope> me, String elementToAdd)
    {
        for(Revision r : revisions)
        {
            // add revision to scope
            if (elementToAdd.equals("Revision"))
            {
                for(Scope sc : (r.getScope()))
                {
                    if(compareScopes(sc, me)) {me.getValue().setRevision(r);}
                }
            }
            // add scope to revision
            if(elementToAdd.equals("Scope"))
            {
                if(compareRevisions(r, me.getValue().getRevision())) {r.getScope().add(me.getValue());}
            }
        }
    }

    private boolean compareScopes(Scope sc1, Map.Entry<String, Scope> me)
    {
        String key = sc1.getId() + "/" + sc1.getClazz();
        return me.getKey().equals(key);
    }

    private boolean compareChanges(Change ch1, Change ch2)
    {
        String key = ch1.getAid() + "/" + ch1.getText();
        String key2 = ch2.getAid() + "/" + ch2.getText();
        return key2.equals(key);
    }

    private boolean compareRevisions(Revision r1, Revision r2)
    {
        String key = r1.getRev() + "/" + r1.getDate();
        String key2 = r2.getRev() + "/" + r2.getDate();
        return key2.equals(key);
    }

    private <E> void deleteScopes(List<E> elements)
    {
        for (E e : elements)
        {
            if(e instanceof Change){((Change)e).setScope(null);}
            if(e instanceof Revision){((Revision) e).unsetScope();}
        }
    }

    private void deleteChanges(LinkedHashMap<String, Scope> scopes)
    {
        for(Map.Entry<String, Scope> me : scopes.entrySet())
        {
            me.getValue().unsetChange();
        }
    }

    public List<Change> flat(List<Revision> revisions)
    {
    	List<Change> changes = new ArrayList<Change>();
        LinkedHashMap<String, Scope> scopes = new LinkedHashMap<String, Scope>();
        for(Revision rev : revisions)
        {
            for(Scope sc : rev.getScope())
            {
                scopes.put(sc.getId() + "/" + sc.getClazz(), sc);
            }
        }

        for(Map.Entry<String, Scope> me : scopes.entrySet())
        {
            for(Change ch : me.getValue().getChange())
            {
                changes.add(ch);
            }
            modificationWithChanges(changes, me, "Scope");
            modificationWithRevisions(revisions, me, "Revision");
        }

        deleteChanges(scopes);
        deleteScopes(revisions);

    	return changes;
    }
}