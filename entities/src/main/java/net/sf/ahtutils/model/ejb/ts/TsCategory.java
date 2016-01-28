package net.sf.ahtutils.model.ejb.ts;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsCategory;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Category",category="ts",subset="ts")
public class TsCategory implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsTsCategory<AhtUtilsLang,Description,TsCategory,TsUnit,TimeSeries,TsEntity,TsInterval,TsData>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private TsUnit unit;
	@Override public TsUnit getUnit() {return unit;}
	@Override public void setUnit(TsUnit unit) {this.unit = unit;}
}