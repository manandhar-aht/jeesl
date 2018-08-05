package org.jeesl.model.pojo.map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Multi3Key <K1 extends EjbWithId, K2 extends EjbWithId, K3 extends EjbWithId> extends Multi2Key<K1,K2>
{
    final static Logger logger = LoggerFactory.getLogger(Multi3Key.class);

    private final K3 k3; public K3 getK3() {return k3;}
    
    public Multi3Key(final K1 k1, final K2 k2, final K3 k3)
    {
		super(k1,k2);
		this.k3=k3;
    }
    
	@SuppressWarnings("unchecked")
	@Override public boolean equals(Object object)
	{
		if (object == null) { return false; }
		if (object == this) { return true; }
		if (object.getClass() != this.getClass()) {return false;}
		Multi3Key<K1,K2,K3> other = (Multi3Key<K1,K2,K3>)object;
		
		return new EqualsBuilder().appendSuper(super.equals(object)).append(this.hashCode(), other.hashCode()).isEquals();
	}
	@Override public int hashCode()
	{
		return new HashCodeBuilder(17, 43).append(k1.hashCode()).append(k2.hashCode()).append(k3.hashCode()).toHashCode();
	}
}