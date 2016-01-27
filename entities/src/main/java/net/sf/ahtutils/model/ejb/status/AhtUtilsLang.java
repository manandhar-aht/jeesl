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
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(name = "UtilsLang")
@EjbErNode(name="Language",category="status",subset="status",level=3)
public class AhtUtilsLang implements UtilsLang,EjbRemoveable,Serializable
{
	public static final long serialVersionUID=1;
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	@NotNull
	private String lkey;
	public String getLkey() {return lkey;}
	public void setLkey(String lkey) {this.lkey = lkey;}
	
	@NotNull
	private String lang;
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
	
	public synchronized static Map<String,AhtUtilsLang> createMap(AhtUtilsLang... languages)
	{
		Map<String,AhtUtilsLang> langMap = new Hashtable<String, AhtUtilsLang>();
		for(AhtUtilsLang lang : languages)
		{
			langMap.put(lang.getLkey(), lang);
		}
		return langMap;
	}
	
	public synchronized static AhtUtilsLang create(String key, String lang)
	{
		AhtUtilsLang pl = new AhtUtilsLang();
		pl.setLkey(key);
		pl.setLang(lang);
		return pl;
	}
}