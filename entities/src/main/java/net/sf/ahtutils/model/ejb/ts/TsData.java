package net.sf.ahtutils.model.ejb.ts;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Data",category="ts",subset="ts",level=2)
public class TsData implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsTsData<AhtUtilsLang,AhtUtilsDescription,TsCategory,TsUnit,TimeSeries,TsEntity,TsInterval,TsData>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private TimeSeries timeSeries;
	@Override public TimeSeries getTimeSeries() {return timeSeries;}
	@Override public void setTimeSeries(TimeSeries timeSeries) {this.timeSeries = timeSeries;}

	private Date record;
	@Override public Date getRecord() {return record;}
	@Override public void setRecord(java.util.Date record) {this.record=record;}
	
	private Double value;
	@Override public Double getValue() {return value;}
	@Override public void setValue(Double value) {this.value = value;}
}