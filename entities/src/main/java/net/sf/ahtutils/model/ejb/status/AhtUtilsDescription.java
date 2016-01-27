package net.sf.ahtutils.model.ejb.status;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(name = "UtilsLang")
@EjbErNode(name="Description",category="status",subset="status",level=3)
public class AhtUtilsDescription implements UtilsDescription,EjbRemoveable,Serializable
{
	public static final long serialVersionUID=1;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String lkey;
	
	@NotNull
	private String lang;
	
	private Boolean styled;
	@Override public Boolean getStyled() {return styled;}
	@Override public void setStyled(Boolean styled) {this.styled = styled;}
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getLkey() {return lkey;}
	public void setLkey(String lkey) {this.lkey = lkey;}
	
	public String getLang() {return lang;}
	public void setLang(String name) {this.lang = name;}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ["+lkey+"]");
			sb.append(" "+lang);
		return sb.toString();
	}
	
	public synchronized static Map<String,AhtUtilsDescription> createMap(AhtUtilsDescription... languages)
	{
		Map<String,AhtUtilsDescription> langMap = new Hashtable<String, AhtUtilsDescription>();
		for(AhtUtilsDescription lang : languages)
		{
			langMap.put(lang.getLkey(), lang);
		}
		return langMap;
	}
	
	public synchronized static AhtUtilsDescription create(String key, String lang)
	{
		AhtUtilsDescription pl = new AhtUtilsDescription();
		pl.setLkey(key);
		pl.setLang(lang);
		return pl;
	}
}