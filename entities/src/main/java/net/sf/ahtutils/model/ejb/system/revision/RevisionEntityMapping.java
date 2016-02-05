package net.sf.ahtutils.model.ejb.system.revision;


import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.HashCodeBuilder;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Entity Mapping",category="revision",subset="revision",level=3)
public class RevisionEntityMapping implements Serializable,EjbRemoveable,EjbPersistable,
									UtilsRevisionEntityMapping<Lang,Description,RevisionCategory,RevisionView,RevisionViewMapping,RevisionScope,RevisionEntity,RevisionEntityMapping,RevisionAttribute>
{
	public static final long serialVersionUID=1;

	public static enum Code {login}
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	@NotNull @ManyToOne
	private RevisionEntity entity;
	@Override public RevisionEntity getEntity() {return entity;}
	@Override public void setEntity(RevisionEntity entity) {this.entity = entity;}
	
	@NotNull @ManyToOne
	private RevisionScope scope;
	@Override public RevisionScope getScope() {return scope;}
	@Override public void setScope(RevisionScope scope) {this.scope = scope;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}

	private String xpath;
	@Override public String getXpath() {return xpath;}
	@Override public void setXpath(String xpath) {this.xpath = xpath;}
	

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof RevisionEntityMapping) ? id == ((RevisionEntityMapping) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}