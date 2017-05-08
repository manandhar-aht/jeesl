package org.jeesl.model.ejb.module.ts.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.jeesl.model.ejb.module.ts.core.TimeSeries;
import org.jeesl.model.ejb.module.ts.core.TsBridge;
import org.jeesl.model.ejb.module.ts.core.TsCategory;
import org.jeesl.model.ejb.module.ts.core.TsEntityClass;
import org.jeesl.model.ejb.module.ts.core.TsInterval;
import org.jeesl.model.ejb.module.ts.core.TsScope;
import org.jeesl.model.ejb.module.ts.core.TsUnit;
import org.jeesl.model.ejb.module.ts.core.TsWorkspace;
import org.jeesl.model.ejb.module.ts.qa.TsQaFlag;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;
import org.jeesl.model.ejb.user.JeeslUser;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Transaction",category="ts",subset="ts",level=2)
public class TsTransaction implements Serializable,EjbRemoveable,EjbPersistable,
								JeeslTsTransaction<Lang,Description,TsCategory,TsScope,TsUnit,TimeSeries,TsTransaction,TsBridge,TsEntityClass,TsInterval,TsData,TsSample,JeeslUser,TsWorkspace,TsQaFlag>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private Date record;
	@Override public Date getRecord() {return record;}
	@Override public void setRecord(java.util.Date record) {this.record=record;}
	
	private JeeslUser user;
	@Override public JeeslUser getUser() {return user;}
	@Override public void setUser(JeeslUser user) {this.user = user;}
	
	private String remark;
	@Override public String getRemark() {return remark;}
	@Override public void setRemark(String remark) {this.remark = remark;}
	
	@ManyToOne
	private TsQaFlag flag;
	public TsQaFlag getFlag() {return flag;}
	public void setFlag(TsQaFlag flag) {this.flag = flag;}
}