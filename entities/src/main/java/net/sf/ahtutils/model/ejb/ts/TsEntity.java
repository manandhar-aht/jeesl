package net.sf.ahtutils.model.ejb.ts;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsEntity;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Entity",category="ts",subset="ts",level=2)
public class TsEntity implements Serializable,EjbRemoveable,EjbPersistable,
		UtilsTsEntity<Lang,Description,TsCategory,TsScope,TsUnit,TimeSeries,TsEntity,TsEntityClass,TsInterval,TsData,TsWorkspace,TsQaFlag>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private TsEntityClass entityClass;
	@Override public TsEntityClass getEntityClass() {return entityClass;}
	@Override public void setEntityClass(TsEntityClass entityClass) {this.entityClass = entityClass;}
}