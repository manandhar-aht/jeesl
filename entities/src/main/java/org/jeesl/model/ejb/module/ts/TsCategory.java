package org.jeesl.model.ejb.module.ts;

import java.io.Serializable;
import java.util.Map;

import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Category",category="ts",subset="ts",level=4)
public class TsCategory implements Serializable,EjbRemoveable,EjbPersistable,
							UtilsStatus<TsCategory,Lang,Description>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	protected String symbol;
	public String getSymbol(){return symbol;}
	public void setSymbol(String symbol){this.symbol = symbol;}
	
	@Override public String getCode() {return null;}
	@Override public void setCode(String code) {}
	
	@Override public int getPosition() {return 0;}
	@Override public void setPosition(int position) {}
	
	@Override public boolean isVisible() {return false;}
	@Override public void setVisible(boolean visible) {}
	
	@Override public Map<String, Lang> getName() {return null;}
	@Override public void setName(Map<String, Lang> name) {}
	
	@Override public Map<String, Description> getDescription() {return null;}
	@Override public void setDescription(Map<String, Description> description) {}
	
	@Override public String getStyle() {return null;}
	@Override public void setStyle(String style) {}
	
	@Override public String getImage() {return null;}
	@Override public void setImage(String image) {}
	
	@Override public String getImageAlt() {return null;}
	@Override public void setImageAlt(String image) {}
	
	@Override public <P extends EjbWithCode> P getParent() {return null;}
	@Override public <P extends EjbWithCode> void setParent(P parent) {}
	
	
	public boolean equals(Object object){return (object instanceof TsCategory) ? id == ((TsCategory) object).getId() : (object == this);}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}