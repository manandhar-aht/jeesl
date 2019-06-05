package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.workflow.process.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalStage;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslApprovalWorkflow <AP extends JeeslApprovalProcess<?,?,?>,
										AS extends JeeslApprovalStage<?,?,AP,?>,
										AY extends JeeslApprovalActivity<?,?,?>
									
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithParentAttributeResolver
{
	public static enum Attributes{process}
	
	AP getProcess();
	void setProcess(AP process);
	
	AS getCurrentStage();
	void setCurrentStage(AS currentStage);
	
	List<AY> getActivities();
	void setActivities(List<AY> activities);
}