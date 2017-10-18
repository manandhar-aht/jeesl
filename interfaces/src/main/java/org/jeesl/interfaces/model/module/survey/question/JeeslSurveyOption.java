package org.jeesl.interfaces.model.module.survey.question;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyOption<L extends UtilsLang, D extends UtilsDescription>
			extends EjbWithId,EjbWithNonUniqueCode,EjbWithPosition,//EjbSaveable,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Units{yn,number,txt};
	public static enum Status{open};
	
	double getScore();
	void setScore(double score);
	
	boolean getRow();
	void setRow(boolean row);
	
	boolean getCol();
	void setCol(boolean col);
	
	boolean getCell();
	void setCell(boolean cell);
}