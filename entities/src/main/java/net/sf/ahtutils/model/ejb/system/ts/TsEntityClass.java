package net.sf.ahtutils.model.ejb.system.ts;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntityClass;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Class",category="ts",subset="ts",level=4)
public class TsEntityClass implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsTsEntityClass<Lang,Description,TsCategory,TsScope,TsUnit,TimeSeries,TsEntity,TsEntityClass,TsInterval,TsData,TsWorkspace,TsQaFlag>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String resolveParentAttribute() {return "category";}
	
	@NotNull @ManyToOne
	private TsCategory category;
	@Override public TsCategory getCategory() {return category;}
	@Override public void setCategory(TsCategory category) {this.category = category;}
	
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	private Integer position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private Boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}

	private String xpath;
	@Override public String getXpath() {return xpath;}
	@Override public void setXpath(String xpath) {this.xpath = xpath;}
	
	private String attribute;
	@Override public String getAttribute() {return attribute;}
	@Override public void setAttribute(String attribute) {this.attribute = attribute;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, Lang> name;
	@Override public Map<String, Lang> getName() {return name;}
	@Override public void setName(Map<String, Lang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String,Description> description;
	@Override public Map<String,Description> getDescription() {return description;}
	@Override public void setDescription(Map<String,Description> description) {this.description = description;}
	
	
	public boolean equals(Object object)
	{
        return (object instanceof TsData) ? id == ((TsData) object).getId() : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}