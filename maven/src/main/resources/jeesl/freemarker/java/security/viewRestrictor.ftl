package ${packageName};

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import ${abstractImport};

<#list codes as x>
import ${x.import};
</#list> 

public class ${className} extends ${abstract}
{
<#list codes as x>
	public @Secures @${x.class} boolean is${x.class}(Identity identity) {return super.secureView(identity, "${x.code}");}
</#list> 	
}