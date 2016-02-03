package net.sf.ahtutils.model.ejb.ts;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.system.security.UtilsStaff;
import net.sf.ahtutils.model.ejb.security.SecurityAction;
import net.sf.ahtutils.model.ejb.security.SecurityCategory;
import net.sf.ahtutils.model.ejb.security.SecurityRole;
import net.sf.ahtutils.model.ejb.security.SecurityUsecase;
import net.sf.ahtutils.model.ejb.security.SecurityView;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.ejb.user.AhtUtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(name = "StaffWorkspace",uniqueConstraints = @UniqueConstraint(columnNames = {"domain_id","role_id","user_id"}))
@EjbErNode(name="Staff",subset="ts",category="ts",level=2)
public class TsStaffScope implements Serializable,EjbWithId,EjbPersistable,EjbRemoveable,EjbSaveable,
					UtilsStaff<Lang,Description,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,AhtUtilsUser,TsScope>
{
	public static final long serialVersionUID=1;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id){this.id = id;}
	
	@NotNull @ManyToOne
	private TsScope domain;
	public TsScope getDomain() {return domain;}
	public void setDomain(TsScope domain) {this.domain = domain;}
		
	@NotNull @ManyToOne
	private SecurityRole role;
	@Override public SecurityRole getRole() {return role;}
	@Override public void setRole(SecurityRole role) {this.role = role;}

	@NotNull @ManyToOne
	private AhtUtilsUser user;
	@Override public AhtUtilsUser getUser() {return user;}
	@Override  public void setUser(AhtUtilsUser user) {this.user = user;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(" user:");
		if(user!=null){sb.append(user.toString());}else{sb.append("null");}
		sb.append(" district:");
		if(domain!=null){sb.append(domain.toString());}else{sb.append("null");}
		sb.append(" role");
		if(role!=null){sb.append(role.toString());}else{sb.append("null");}
		return sb.toString();
	}
	
	@Override public boolean equals(Object object) {return (object instanceof TsStaffScope) ? id == ((TsStaffScope) object).getId() : (object == this);}
}