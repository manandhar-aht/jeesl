package org.jeesl.model.ejb.module.ts.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.model.ejb.module.ts.data.TsData;
import org.jeesl.model.ejb.module.ts.data.TsDataSource;
import org.jeesl.model.ejb.module.ts.data.TsSample;
import org.jeesl.model.ejb.module.ts.data.TsTransaction;
import org.jeesl.model.ejb.module.ts.qa.TsQaFlag;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;
import org.jeesl.model.ejb.user.JeeslUser;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Scope",category="ts",subset="ts")
public class TsScope implements Serializable,EjbRemoveable,EjbPersistable,
								JeeslTsScope<Lang,Description,TsCategory,TsScope,TsUnit,TimeSeries,TsTransaction,TsDataSource,TsBridge,TsEntityClass,TsInterval,TsData,TsSample,JeeslUser,TsWorkspace,TsQaFlag>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String resolveParentAttribute() {return "category";}
	
	@NotNull @ManyToOne
	private TsCategory category;
	public TsCategory getCategory() {return category;}
	public void setCategory(TsCategory category) {this.category = category;}
	
	@NotNull
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@NotNull @ManyToOne
	private TsUnit unit;
	@Override public TsUnit getUnit() {return unit;}
	@Override public void setUnit(TsUnit unit) {this.unit = unit;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	
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
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="TsScope_Interval",joinColumns={@JoinColumn(name="scope")},inverseJoinColumns={@JoinColumn(name="interval")})
	private List<TsInterval> intervals;
	public List<TsInterval> getIntervals() {if(intervals==null){intervals = new ArrayList<TsInterval>();}return intervals;}
	public void setIntervals(List<TsInterval> intervals) {this.intervals = intervals;}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="TsScope_Class",joinColumns={@JoinColumn(name="scope")},inverseJoinColumns={@JoinColumn(name="class")})
	private List<TsEntityClass> classes;
	public List<TsEntityClass> getClasses() {if(classes==null){classes = new ArrayList<TsEntityClass>();}return classes;}
	public void setClasses(List<TsEntityClass> classes) {this.classes = classes;}
}