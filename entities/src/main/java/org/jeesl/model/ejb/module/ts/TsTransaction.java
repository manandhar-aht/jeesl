package org.jeesl.model.ejb.module.ts;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Data",category="ts",subset="ts",level=2)
public class TsTransaction implements Serializable,EjbRemoveable,EjbPersistable,
								JeeslTsTransaction<Lang,Description,TsCategory,TsScope,TsUnit,TimeSeries,TsTransaction,TsBridge,TsEntityClass,TsInterval,TsData,TsWorkspace,TsQaFlag>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private Date record;
	@Override public Date getRecord() {return record;}
	@Override public void setRecord(java.util.Date record) {this.record=record;}
	
	
	@ManyToOne
	private TsQaFlag flag;
	public TsQaFlag getFlag() {return flag;}
	public void setFlag(TsQaFlag flag) {this.flag = flag;}
}