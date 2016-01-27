package net.sf.ahtutils.model.ejb.ts;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Data",category="ts",subset="ts")
public class TsData implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsTsData<AhtUtilsLang,AhtUtilsDescription,TsCategory,TsInterval,TimeSeries,TsData>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private Date record;
	@Override public Date getRecord() {return record;}
	@Override public void setRecord(java.util.Date record) {this.record=record;}
}