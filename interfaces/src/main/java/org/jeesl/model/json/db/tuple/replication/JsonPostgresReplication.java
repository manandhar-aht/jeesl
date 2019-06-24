package org.jeesl.model.json.db.tuple.replication;

import java.io.Serializable;

public class JsonPostgresReplication implements Serializable
{
	public static final long serialVersionUID=1;
	
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	private String state;
	public String getState() {return state;}
	public void setState(String state) {this.state = state;}
	
	private long write_lag;
	public long getWrite_lag() {return write_lag;}
	public void setWrite_lag(long write_lag) {this.write_lag = write_lag;}
	
	private long flush_lag;
	public long getFlush_lag() {return flush_lag;}
	public void setFlush_lag(long flush_lag) {this.flush_lag = flush_lag;}
	
	private long replay_lag;
	public long getReplay_lag() {return replay_lag;}
	public void setReplay_lag(long replay_lag) {this.replay_lag = replay_lag;}
	
	private String client_addr;
	public String getClient_addr() {return client_addr;}
	public void setClient_addr(String client_addr) {this.client_addr = client_addr;}	
	
	private String sync_state;
	public String getSync_state() {return sync_state;}
	public void setSync_state(String sync_state) {this.sync_state = sync_state;}
	
}