package net.sf.ahtutils.model.pojo;

public class UtilsCredential
{		
	public UtilsCredential(String username, String password)
	{
		this.username=username;
		this.password=password;
	}	
	
	private String username;
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	private String password;
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("username: ").append(username);
		sb.append(" ");
		sb.append("password: ").append(password);
		return sb.toString();
	}
}