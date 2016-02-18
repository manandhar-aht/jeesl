package net.sf.ahtutils.report.revert.excel;

import java.io.Serializable;
import java.util.Date;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class DummyEntity implements EjbWithId, Serializable {
		
	private static final long serialVersionUID = 1L;
	private String valueString;
	private Double valueDouble;
	private Date   valueDate;
	private String code;
	private long   id;

	public String getValueString() {return valueString;}
	public void setValueString(String value) {this.valueString = value;}

	public Double getValueDouble() {return valueDouble;}
	public void setValueDouble(Double valueDouble) {this.valueDouble = valueDouble;}

	public Date getValueDate() {return valueDate;}
	public void setValueDate(Date valueDate) {this.valueDate = valueDate;}

	@Override
	public long getId() {return id;}
	@Override
	public void setId(long id) {this.id = id;}

	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
}