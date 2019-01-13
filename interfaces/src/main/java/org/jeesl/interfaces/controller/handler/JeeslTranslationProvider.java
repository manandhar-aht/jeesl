package org.jeesl.interfaces.controller.handler;

import java.util.List;

import org.openfuxml.interfaces.configuration.OfxTranslationProvider;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslTranslationProvider <LOC extends UtilsStatus<LOC,?,?>> extends OfxTranslationProvider
{
	void setLanguages(List<LOC> locales);
}