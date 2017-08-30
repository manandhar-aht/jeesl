package org.jeesl.controller.handler.ui.helper;

import java.io.Serializable;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeConfirmationHandler implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(CodeConfirmationHandler.class);
	private static final long serialVersionUID = 1L;

	private int codeToConfirm; public int getCodeToConfirm() {return codeToConfirm;} public void setCodeToConfirm(int i) {this.codeToConfirm = i;}
	private int removeCode; public int getRemoveCode() {return removeCode;}
	
	private boolean allowRemove; public boolean isAllowRemove() {return allowRemove;} public void setAllowRemove(boolean allowRemove) {this.allowRemove = allowRemove;}
	private boolean showDialog; public boolean isShowDialog() {return showDialog;}
	public CodeConfirmationHandler()
	{
		allowRemove = true;
		showDialog = false;
	}
	
	public void generateCode()
	{
		removeCode = new Random().nextInt(100000-10000) + 10000;
		showDialog = true;
	}

	public boolean isCodeConfirmed()
	{
		return codeToConfirm==removeCode;
	}
}