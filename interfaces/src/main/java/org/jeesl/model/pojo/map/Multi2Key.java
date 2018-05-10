package org.jeesl.model.pojo.map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Multi2Key <K1 extends EjbWithId, K2 extends EjbWithId>
{
    final static Logger logger = LoggerFactory.getLogger(Multi2Key.class);

    private final K1 k1;
    public K1 getK1() {
		return k1;
	}

	public K2 getK2() {
		return k2;
	}
	private final K2 k2;
    
    public Multi2Key(final K1 k1, final K2 k2)
    {
		this.k1=k1;
		this.k2=k2;
    }
    
	@SuppressWarnings("unchecked")
	@Override public boolean equals(Object object)
	{
		if (object == null) { return false; }
		if (object == this) { return true; }
		if (object.getClass() != this.getClass()) {return false;}
		Multi2Key<K1,K2> other = (Multi2Key<K1,K2>)object;
		
		return new EqualsBuilder().appendSuper(super.equals(object)).append(this.hashCode(), other.hashCode()).isEquals();
	}
	@Override public int hashCode()
	{
		return new HashCodeBuilder(17, 43).append(k1.hashCode()).append(k2.hashCode()).toHashCode();
	}
}