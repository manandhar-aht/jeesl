package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;

public interface JeeslRevisionAttributeType extends Serializable,EjbPersistable,EjbWithCode,JeeslOptionRestDownload
{

}