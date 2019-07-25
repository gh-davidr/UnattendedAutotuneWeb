package org.DavidRichardson.UnattendedAutotuneWeb.entity;

/* 
 * ------------------------------------------------
 * Unattended Autotune Web
 * 
 * Developed by David Richardson
 * 24 July 2019
 * 
 * ------------------------------------------------
 */

public abstract class AppOptionConstraint 
{

	public AppOptionConstraint() 
	{
		;
	}
	
	public abstract Boolean isValid(AppOption opt);
	public abstract String  defineConstraint();

}
