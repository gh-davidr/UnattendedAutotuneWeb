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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppSingleWebDriver 
{
	private static AppSingleWebDriver m_Instance = null;

	private WebDriver              m_WebDriver = null;   

	public static AppSingleWebDriver getInstance()
	{
		if (AppSingleWebDriver.m_Instance == null)
		{
			AppSingleWebDriver.m_Instance = new AppSingleWebDriver(); 
		}
		return AppSingleWebDriver.m_Instance;
	}

	private AppSingleWebDriver() 
	{
		AppOptionManager appOptionManager = AppControl.getInstance().getM_AppOptionManager();
		
		String driver = appOptionManager.getOptionValByName("SELENIUM_DRIVER");
		String driverPath = appOptionManager.getOptionValByName("SELENIUM_DRIVER_PATH");
		String pageURL = appOptionManager.getOptionValByName("AUTOTUNEWEBURL");
		
		System.setProperty(driver, driverPath);

		m_WebDriver = new ChromeDriver();
		m_WebDriver.get(pageURL);
		m_WebDriver.manage().window().maximize();
	}

	/**
	 * @return the m_WebDriver
	 */
	public synchronized WebDriver getM_WebDriver() {
		return m_WebDriver;
	}

	
}
