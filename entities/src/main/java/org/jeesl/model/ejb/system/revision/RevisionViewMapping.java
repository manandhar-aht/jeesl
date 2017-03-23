package org.jeesl.model.ejb.system.revision;


import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="View Mapping",category="revision",subset="revision",level=3)
public class RevisionViewMapping implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsRevisionViewMapping<Lang,Description,RevisionCategory,RevisionView,RevisionViewMapping,RevisionScope,RevisionScopeType,RevisionEntity,RevisionEntityMapping,RevisionAttribute,RevisionAttributeType>
{
	public static final long serialVersionUID=1;

	public static enum Code {login}
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private RevisionView view;
	@Override public RevisionView getView() {return view;}
	@Override public void setView(RevisionView view) {this.view = view;}

	@NotNull @ManyToOne
	private RevisionEntity entity;
	@Override public RevisionEntity getEntity() {return entity;}
	@Override public void setEntity(RevisionEntity entity) {this.entity = entity;}
	
	@NotNull @ManyToOne
	private RevisionEntityMapping entityMapping;
	@Override public RevisionEntityMapping getEntityMapping() {return entityMapping;}
	@Override public void setEntityMapping(RevisionEntityMapping entityMapping) {this.entityMapping = entityMapping;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof RevisionViewMapping) ? id == ((RevisionViewMapping) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}