package org.jeesl.api.rest.system.security;

import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityViewImport;

public interface JeeslSecurityRestImport extends
											UtilsSecurityViewImport,
											JeeslSecurityRestViewImport,
											JeeslSecurityRestRoleImport,
											JeeslSecurityRestUsecaseImport,
											JeeslSecurityRestTemplateImport
{

}