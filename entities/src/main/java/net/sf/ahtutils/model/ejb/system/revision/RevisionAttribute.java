package net.sf.ahtutils.model.ejb.system.revision;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.HashCodeBuilder;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Attribute",category="revision",subset="revision",level=2)
public class RevisionAttribute implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsRevisionAttribute<Lang,Description,RevisionCategory,RevisionView,RevisionViewMapping,RevisionScope,RevisionScopeType,RevisionEntity,RevisionEntityMapping,RevisionAttribute,RevisionAttributeType>
{
	public static final long serialVersionUID=1;

	public static enum Code {login}
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
//	@NotNull @ManyToOne
//	private RevisionEntity entity;
//	@Override public RevisionEntity getEntity() {return entity;}
//	@Override public void setEntity(RevisionEntity entity) {this.entity = entity;}
	
	@NotNull @ManyToOne
	private RevisionAttributeType type;
	public RevisionAttributeType getType() {return type;}
	public void setType(RevisionAttributeType type) {this.type = type;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String,Lang> name;
	@Override public Map<String,Lang> getName() {return name;}
	@Override public void setName(Map<String,Lang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String,Description> description;
	@Override public Map<String,Description> getDescription() {return description;}
	@Override public void setDescription(Map<String,Description> description) {this.description = description;}
	
	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private String xpath;
	@Override public String getXpath() {return xpath;}
	@Override public void setXpath(String xpath) {this.xpath = xpath;}
	
	private boolean showWeb;
	@Override public boolean isShowWeb() {return showWeb;}
	@Override public void setShowWeb(boolean showWeb) {this.showWeb = showWeb;}
	
	private boolean showPrint;
	@Override public boolean isShowPrint() {return showPrint;}
	@Override public void setShowPrint(boolean showPrint) {this.showPrint = showPrint;}
	
	private boolean showName;
	@Override public boolean isShowName() {return showName;}
	@Override public void setShowName(boolean showName) {this.showName = showName;}
	
	private boolean showEnclosure;
	@Override public boolean isShowEnclosure() {return showEnclosure;}
	@Override public void setShowEnclosure(boolean showEnclosure) {this.showEnclosure = showEnclosure;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof RevisionAttribute) ? id == ((RevisionAttribute) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}