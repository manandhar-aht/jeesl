package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslApprovalWorkflow <WP extends JeeslWorkflowProcess<?,?,?>,
										WS extends JeeslWorkflowStage<?,?,WP,?>,
										WY extends JeeslApprovalActivity<?,?,?>
									
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithParentAttributeResolver
{
	public static enum Attributes{process}
	
	WP getProcess();
	void setProcess(WP process);
	
	WS getCurrentStage();
	void setCurrentStage(WS currentStage);
	
	List<WY> getActivities();
	void setActivities(List<WY> activities);
}