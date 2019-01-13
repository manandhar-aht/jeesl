package org.jeesl.interfaces.controller.processor;

import java.util.List;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public interface SsiMappingProcessor <DATA extends JeeslIoSsiData<?,?>>
{
	void initMappings() throws UtilsNotFoundException;
	void linkData(List<DATA> datas);
}
