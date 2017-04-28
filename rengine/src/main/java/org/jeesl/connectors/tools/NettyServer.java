package org.jeesl.connectors.tools;


import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class NettyServer 
{
	
	public NettyServer() throws Exception
	{
		ResteasyDeployment deployment = new ResteasyDeployment();
    	deployment.getActualResourceClasses().add(WeapRequestService.class);
    	
    	NettyJaxrsServer netty = new NettyJaxrsServer();
    	netty.setDeployment(deployment);
    	netty.setPort(8090);
    	netty.setRootResourcePath("");
    	netty.setSecurityDomain(null);
    	netty.start();
	}
	
	public static void main(String args[]) throws Exception
	{		
		new NettyServer();
	}
}