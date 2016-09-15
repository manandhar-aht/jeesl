package org.jeesl.factory.json.system.revision;

import java.math.BigInteger;

import net.sf.ahtutils.model.json.system.revision.JsonRevision;

public class JsonRevisionFactory
{
	public static JsonRevision build(Object[] array)
	{
		long id = ((BigInteger)array[0]).longValue();
        long revision = ((BigInteger)array[1]).longValue();
        int type = ((Short)array[1]).intValue();
        long user = ((BigInteger)array[3]).longValue();
        
		return build(revision,id,type,user);
	}
	
	public static JsonRevision build(long revision, long id)
	{
		JsonRevision json = new JsonRevision();
		json.setRevision(revision);
		json.setId(id);
		return json;
	}
	
	public static JsonRevision build(long revision, long id, int type, long userId)
	{
		JsonRevision json = build(revision,id);
		json.setType(type);
		json.setUserId(userId);
		return json;
	}
}