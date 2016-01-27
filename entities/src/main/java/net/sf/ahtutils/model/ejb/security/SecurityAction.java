package net.sf.ahtutils.model.ejb.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.ejb.user.AhtUtilsUser;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"code"}))
@EjbErNode(name="Action",category="security",subset="security")

public class SecurityAction implements EjbWithCode,Serializable,EjbRemoveable,EjbPersistable,
	UtilsSecurityAction<AhtUtilsLang,AhtUtilsDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,AhtUtilsUser>
{
	public static final long serialVersionUID=1;

	public static enum Code {login}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private SecurityView view;
	public SecurityView getView() {return view;}
	public void setView(SecurityView view) {this.view = view;}
		
	@NotNull
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	@Override public String resolveParentAttribute() {return "category";}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	
	private Boolean documentation;
	@Override public Boolean getDocumentation() {return documentation;}
	@Override public void setDocumentation(Boolean documentation) {this.documentation = documentation;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, AhtUtilsLang> name;
	@Override public Map<String, AhtUtilsLang> getName() {return name;}
	@Override public void setName(Map<String, AhtUtilsLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, AhtUtilsDescription> description;
	@Override public Map<String, AhtUtilsDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, AhtUtilsDescription> description) {this.description = description;}
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<SecurityRole> roles;
	@Override public List<SecurityRole> getRoles() {if(roles==null){roles = new ArrayList<SecurityRole>();}return roles;}
	@Override public void setRoles(List<SecurityRole> roles) {this.roles = roles;}
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<SecurityUsecase> usecases;
	@Override public List<SecurityUsecase> getUsecases() {if(usecases==null){usecases = new ArrayList<SecurityUsecase>();}return usecases;}
	@Override public void setUsecases(List<SecurityUsecase> usecases) {this.usecases = usecases;}

	
	public boolean equals(Object object)
	{
        return (object instanceof SecurityAction)
             ? id == ((SecurityAction) object).getId()
             : (object == this);
    }
}