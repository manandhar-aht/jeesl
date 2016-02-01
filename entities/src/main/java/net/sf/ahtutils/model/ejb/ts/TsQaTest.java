package net.sf.ahtutils.model.ejb.ts;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="QA Test",category="tsQA",subset="tsQa")
public class TsQaTest implements Serializable,EjbRemoveable,EjbPersistable,
	EjbWithId
								
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	@NotNull @ManyToOne
	private TsQaCategory category;
	public TsQaCategory getCategory() {return category;}
	public void setCategory(TsQaCategory category) {this.category = category;}
	
	@NotNull @ManyToOne
	private TsQaFlag flag;
	public TsQaFlag getFlag() {return flag;}
	public void setFlag(TsQaFlag flag) {this.flag = flag;}
	
	@NotNull @ManyToOne
	private TsData data;
	public TsData getdata() {return data;}
	public void setdata(TsData data) {this.data = data;}
}