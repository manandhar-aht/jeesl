package net.sf.ahtutils.model.ejb.system.ts;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

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
	
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}

	private String xpath;
	public String getXpath() {return xpath;}
	public void setXpath(String xpath) {this.xpath = xpath;}
	
	private String attribute;
	public String getAttribute() {return attribute;}
	public void setAttribute(String attribute) {this.attribute = attribute;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, Lang> name;
	public Map<String, Lang> getName() {return name;}
	public void setName(Map<String, Lang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String,Description> description;
	public Map<String,Description> getDescription() {return description;}
	public void setDescription(Map<String,Description> description) {this.description = description;}
	
	
	
	
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