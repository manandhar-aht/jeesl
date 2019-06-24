package org.jeesl.model.json.db.tuple.replication;

import java.io.Serializable;

public class JsonPostgresConnection implements Serializable
{
	public static final long serialVersionUID=1;
	
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	private String state;
	public String getState() {return state;}
	public void setState(String state) {this.state = state;}	
}