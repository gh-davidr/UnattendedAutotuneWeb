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

import org.DavidRichardson.UnattendedAutotuneWeb.model.AppControl;
import org.DavidRichardson.UnattendedAutotuneWeb.model.AppOptionManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class PageHome extends PageBase 
{
	public PageHome() 
	{
		;
	}

	public PageHome(int m_TimeOutSec)
	{
		super(m_TimeOutSec);
	}

	@Override
	public void performNaturalSequenceOfTests()
	{
		AppOptionManager appOptionManager = AppControl.getInstance().getM_AppOptionManager();
		
		String nightscoutURL = appOptionManager.getOptionValByName("NIGHTSCOUTURL");
		
		// Locate and populate the URL field.
		m_WebDriver.findElement(By.xpath("//*[@id=\"NSUrl\"]")).sendKeys(Keys.chord(Keys.CONTROL, "a"), nightscoutURL);
		
		// Press the Start button
		m_WebDriver.findElement(By.xpath("/html/body/div[2]/div[1]/form/div/div/span[2]/button")).click();
	}

	@Override
	public String getPageName() 
	{
		return "1 - Home Page";
	}

}
