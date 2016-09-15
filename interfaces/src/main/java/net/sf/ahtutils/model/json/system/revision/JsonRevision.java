package net.sf.ahtutils.model.json.system.revision;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonRevision implements Serializable
{
	public static final long serialVersionUID=1;

	public JsonRevision(){}
	
	@JsonProperty("Revision")
	private long revision;
	public long getRevision() {return revision;}
	public void setRevision(long revision) {this.revision = revision;}

	@JsonProperty("Id")
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	@JsonProperty("Parent")
	private long parent;
	public long getParent() {return parent;}
	public void setParent(long parent) {this.parent = parent;}
	
	@JsonProperty("Type")
	private int type;
	public int getType() {return type;}
	public void setType(int type) {this.type = type;}
	
	@JsonProperty("UserId")
	private long userId;
	public long getUserId() {return userId;}
	public void setUserId(long userId) {this.userId = userId;}
}