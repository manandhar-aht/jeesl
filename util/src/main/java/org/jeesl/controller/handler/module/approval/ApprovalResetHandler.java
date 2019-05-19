package org.jeesl.controller.handler.module.approval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApprovalResetHandler
{
	final static Logger logger = LoggerFactory.getLogger(ApprovalResetHandler.class);
	
	private boolean stage; public boolean isStage() {return stage;} public ApprovalResetHandler stage(boolean stage) {this.stage = stage;return this;}

	private boolean permissions; public boolean isPermissions() {return permissions;} public ApprovalResetHandler permissions(boolean permissions) {this.permissions = permissions;return this;}
	private boolean permission; public boolean isPermission() {return permission;} public ApprovalResetHandler permission(boolean permission) {this.permission = permission;return this;}
	
	private boolean transistions; public boolean isTransistions() {return transistions;} public ApprovalResetHandler transistions(boolean transistions) {this.transistions = transistions; return this;}
	private boolean transistion; public boolean isTransistion() {return transistion;} public ApprovalResetHandler transistion(boolean transistion) {this.transistion = transistion; return this;}
	
	private boolean communications; public boolean isCommunications() {return communications;} public ApprovalResetHandler communications(boolean communications) {this.communications = communications;return this;}
	private boolean communication; public boolean isCommunication() {return communication;} public ApprovalResetHandler communication(boolean communication) {this.communication = communication;return this;}
	
	private boolean actions; public boolean isActions() {return actions;} public ApprovalResetHandler actions(boolean actions) {this.actions = actions;return this;}
	private boolean action; public boolean isAction() {return action;} public ApprovalResetHandler action(boolean action) {this.action = action; return this;}
	
	private ApprovalResetHandler()
	{
		
	}
	
	public static ApprovalResetHandler build() {return new ApprovalResetHandler();}
	
	public ApprovalResetHandler all()
	{
		stage = true;
		permissions = true;
		permission = true;
		transistions = true;
		transistion = true;
		communications = true;
		communication = true;
		actions = true;
		action = true;
		return this;
	}
	
	public ApprovalResetHandler none()
	{
		stage = false;
		permissions = false;
		permission = false;
		transistions = false;
		transistion = false;
		communications = false;
		communication = false;
		actions = false;
		action = false;
		return this;
	}
}