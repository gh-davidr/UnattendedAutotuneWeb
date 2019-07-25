package org.DavidRichardson.UnattendedAutotuneWeb.selenium;

/* 
 * ------------------------------------------------
 * Unattended Autotune Web
 * 
 * Developed by David Richardson
 * 24 July 2019
 * 
 * ------------------------------------------------
 */

import org.openqa.selenium.By;

public class PageAutotune extends PageBase 
{

	public PageAutotune() 
	{
		// TODO Auto-generated constructor stub
	}

	public PageAutotune(int m_TimeOutSec)
	{
		super(m_TimeOutSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performNaturalSequenceOfTests()
	{
		// No options to set, just click through :-)
		
		// Press the Start button
		m_WebDriver.findElement(By.xpath("/html/body/div[2]/form/p[5]/input")).click();
	}

	@Override
	public String getPageName() 
	{
		return "2 - Autotune Page";
	}

}
