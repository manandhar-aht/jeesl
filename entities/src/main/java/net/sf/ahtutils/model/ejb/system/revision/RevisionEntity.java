package net.sf.ahtutils.model.ejb.system.revision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.HashCodeBuilder;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Entity",category="revision",subset="revision")
public class RevisionEntity implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsRevisionEntity<Lang,Description,RevisionView,RevisionMapping,RevisionScope,RevisionEntity,RevisionAttribute>
{
	public static final long serialVersionUID=1;	
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private RevisionScope scope;
	@Override public RevisionScope getScope() {return scope;}
	@Override public void setScope(RevisionScope scope) {this.scope = scope;}
	
	private String fqcn;
	@Override public String getFqcn() {return fqcn;}
	@Override public void setFqcn(String fqcn) {this.fqcn = fqcn;}
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="entity")
	private List<RevisionAttribute> attributes;
	public List<RevisionAttribute> getAttributes() {if(attributes==null){attributes=new ArrayList<RevisionAttribute>();}return attributes;}
	public void setAttributes(List<RevisionAttribute> attributes) {this.attributes = attributes;} 
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof RevisionEntity) ? id == ((RevisionEntity) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}