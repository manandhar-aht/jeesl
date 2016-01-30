package net.sf.ahtutils.model.ejb.ts;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Time Series",category="ts",subset="ts")
public class TimeSeries implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsTimeSeries<Lang,Description,TsScope,TsUnit,TimeSeries,TsEntity,TsInterval,TsData>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private TsScope scope;
	@Override public TsScope getScope() {return scope;}
	@Override public void setScope(TsScope scope) {this.scope = scope;}
	
	@NotNull @ManyToOne
	private TsInterval interval;
	@Override public TsInterval getInterval() {return interval;}
	@Override public void setInterval(TsInterval interval) {this.interval = interval;}
	
	@NotNull @ManyToOne
	private TsEntity entity;
	@Override public TsEntity getEntity() {return entity;}
	@Override public void setEntity(TsEntity entity) {this.entity = entity;}
}