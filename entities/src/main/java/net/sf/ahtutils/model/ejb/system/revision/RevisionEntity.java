package net.sf.ahtutils.model.ejb.system.revision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.HashCodeBuilder;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Table(name="RevisionEntity", uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
@EjbErNode(name="Entity",category="revision",subset="revision")
public class RevisionEntity implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsRevisionEntity<Lang,Description,RevisionCategory,RevisionView,RevisionViewMapping,RevisionScope,RevisionEntity,RevisionEntityMapping,RevisionAttribute,RevisionAttributeType>
{
	public static final long serialVersionUID=1;	
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String resolveParentAttribute() {return "category";}
	
	@NotNull @ManyToOne
	private RevisionCategory category;
	public RevisionCategory getCategory() {return category;}
	public void setCategory(RevisionCategory category) {this.category = category;}

	@NotNull
	protected String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	
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
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="entity")
	private List<RevisionAttribute> attributes;
	@Override public List<RevisionAttribute> getAttributes() {if(attributes==null){attributes=new ArrayList<RevisionAttribute>();}return attributes;}
	@Override public void setAttributes(List<RevisionAttribute> attributes) {this.attributes = attributes;} 
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="entity")
	@OrderBy("position")
	private List<RevisionEntityMapping> maps;
	@Override public List<RevisionEntityMapping> getMaps() {if(maps==null){maps=new ArrayList<RevisionEntityMapping>();}return maps;}
	@Override public void setMaps(List<RevisionEntityMapping> maps) {this.maps=maps;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof RevisionEntity) ? id == ((RevisionEntity) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}