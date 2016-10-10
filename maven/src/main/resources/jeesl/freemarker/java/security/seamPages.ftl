package ${packageName};

import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
<#list classImports as i>
${i}
</#list>

<#list views as v>
<#if !(v.public || v.onlyLoginRequired)>
import ${v.import};
</#if>
</#list>

@ViewConfig
public interface SeamPages
{
<#list views as v>

	static enum Pages${v_index}
    {
    	@ViewPattern("${v.viewPattern}")
        @UrlMapping(pattern="${v.urlMapping}")
<#if !v.public>
        @LoginView("${loginView}")
        @AccessDeniedView("${accessDeniedView}")
        @RestrictAtPhase(PhaseIdType.RESTORE_VIEW)
</#if>
<#if !v.public>
	<#if v.onlyLoginRequired>
		@${loginIdentifier}
	<#else>
		@${v.identifier}     
     </#if>
</#if>
        ${v.enum};
    }
</#list> 
}