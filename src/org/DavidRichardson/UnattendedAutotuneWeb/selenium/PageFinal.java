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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.DavidRichardson.UnattendedAutotuneWeb.entity.AppLogger;
import org.DavidRichardson.UnattendedAutotuneWeb.model.AppOptionManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class PageFinal extends PageBase 
{
	private static final Logger m_Logger = Logger.getLogger(AppLogger.class.getName());

	public PageFinal() 
	{
		// TODO Auto-generated constructor stub
	}

	public PageFinal(int m_TimeOutSec)
	{		
		super(m_TimeOutSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performNaturalSequenceOfTests()
	{
		
		// Regular completion message page 
		//
		// /html/body/div[2]/h1 (Autotune Running)
		// /html/body/div[2]/p/span (Number)

		// We've run it another time before the first one finished
		//
		// /html/body/div[2]/h2 (Already Queued)
		// /html/body/div[2]/p[1]/span (Number)
		

		WebElement autotuneRunning = getWebElement("/html/body/div[2]/h1");
		WebElement alreadyQueued = getWebElement("/html/body/div[2]/h2");
		
		if (autotuneRunning != null)
		{
			WebElement el = getWebElement("/html/body/div[2]/p/span");
			String qp = el == null ? ""  : el.getText();
			m_Logger.log(Level.INFO, "Completed with message " + 
					autotuneRunning.getText() + " and queue position " + qp);		
		}
		else if (alreadyQueued != null)
		{
			WebElement el = getWebElement("/html/body/div[2]/p[1]/span");
			String qp = el == null ? ""  : el.getText();
			m_Logger.log(Level.INFO, "Completed with message " + 
					alreadyQueued.getText() + " and queue position " + qp);
		}

		// Close down the web driver
		m_WebDriver.quit();
	}

	@Override
	public String getPageName() 
	{
		return "4 - Final Page";
	}

}
