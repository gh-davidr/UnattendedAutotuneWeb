package org.DavidRichardson.UnattendedAutotuneWeb.model;

/* 
 * ------------------------------------------------
 * Unattended Autotune Web
 * 
 * Developed by David Richardson
 * 24 July 2019
 * 
 * ------------------------------------------------
 */

import java.util.ArrayList;
import java.util.logging.Logger;

import org.DavidRichardson.UnattendedAutotuneWeb.entity.AppLogger;
import org.DavidRichardson.UnattendedAutotuneWeb.entity.AppOption;
import org.DavidRichardson.UnattendedAutotuneWeb.entity.AppOptionConstraintInteger;
import org.DavidRichardson.UnattendedAutotuneWeb.selenium.PageAutotune;
import org.DavidRichardson.UnattendedAutotuneWeb.selenium.PageAutotuneConfig;
import org.DavidRichardson.UnattendedAutotuneWeb.selenium.PageBase;
import org.DavidRichardson.UnattendedAutotuneWeb.selenium.PageFinal;
import org.DavidRichardson.UnattendedAutotuneWeb.selenium.PageHome;

public class AppControl 
{
	private static final Logger m_Logger = Logger.getLogger(AppLogger.class.getName());

	private static AppControl   m_Instance = null;
	
	public static AppControl    getInstance()
	{
		if (m_Instance == null)
		{
			m_Instance = new AppControl();
		}
		return m_Instance;
	}
	
	
	ArrayList<PageBase>      m_PagesSequence = new ArrayList<PageBase>();

	private AppOptionManager m_AppOptionManager = new AppOptionManager();
	private Boolean          m_ValidOptions = true;

	private AppControl()
	{
		;
	}
	
	public void runTests()
	{
		createPages();
		performTests();
	}
	
	public Boolean initialize(String[] args)
	{
		setSupportedArguments();
		m_ValidOptions = processArguments(args);
		return m_ValidOptions;
	}

	public void createPages()
	{
		m_PagesSequence.add(new PageHome());
		m_PagesSequence.add(new PageAutotune());
		m_PagesSequence.add(new PageAutotuneConfig());
		m_PagesSequence.add(new PageFinal());
	}

	private void setSupportedArguments()
	{
		// Set the supported arguments up
		// Thes are all the possible options and what (if any) dependencies there
		// are between options.
		// 
		
		
		// Mandatory parameter sets the URL to user's NightScout
		m_AppOptionManager.addOption(
				new AppOption("NIGHTSCOUTURL", "-nightscout-url")
				.setM_Mandatory(true));

		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_EMAIL", "-autotune-email")
				.setM_Mandatory(true));
		
		// Key component for automation is Selenium
		m_AppOptionManager.addOption(
				new AppOption("SELENIUM_DRIVER_PATH", "-selenium-driver-path")
				.setM_ExpectPath(true)
				.setM_Mandatory(true));
		
		// Key component for automation is Selenium
		m_AppOptionManager.addOption(
				new AppOption("SELENIUM_DRIVER", "-selenium-driver")
				.setM_DefaultOptionValue("webdriver.chrome.driver"));
		
		// Optional parameter sets the URL to AutotuneWeb
		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNEWEBURL", "-autotuneweb-url")
				.setM_DefaultOptionValue("https://autotuneweb.azurewebsites.net/"));
			
		
		// Options to configure AT run
		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_MIN_CARB_IMPACT", "-autotune-min-carb-impact")
				.setM_DefaultOptionValue("8")
				.setM_ExpectInteger(true));
		
		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_PUMP_BASAL_INCREMENT", "-autotune-pump-basal-increment")
				.setM_DefaultOptionValue("0.01")
				.setM_ExpectDouble(true));

		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_CATEGORIZE_UAM_AS_BASAL", "-autotune-categorize-uam-as-basal")
				.setM_DefaultOptionValue("false")
				.setM_ExpectBoolean(true));
	
		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_INSULIN_TYPE", "-autotune-insulin-type")
				.setM_DefaultOptionValue("Rapid")
				.addPermittedValue("Rapid")
				.addPermittedValue("Ultra"));
	
		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_DAYS_DATA", "-autotune-days-data")
				.setM_DefaultOptionValue("7")
				.setM_ExpectInteger(true)
				.setM_AppOptionConstraint(new AppOptionConstraintInteger(1, 30)));

	}

	private void runRegular()
	{
		for (PageBase p : m_PagesSequence)
		{
			p.performNaturalSequenceOfTests();
		}
	}

	private Boolean processArguments(String[] args)
	{
		return m_AppOptionManager.processArgs(args);
	}

	public void performTests()
	{
		runRegular();
	}

	public void summariseResults()
	{
		for (PageBase p : m_PagesSequence)
		{
			summariseResults(p);
		}
	}

	private void summariseResults(PageBase p)
	{
		ArrayList<String> passedTests = p.getM_PassedTests();
		ArrayList<String> failedTests = p.getM_FailedTests();
		int totalTests = passedTests.size() + failedTests.size();

		System.out.println("Summary for " + p.getPageName());
		System.out.println("   Total  Tests: " + totalTests);
		System.out.println("   Passed Tests: " + passedTests.size());
		System.out.println("   Failed Tests: " + failedTests.size());
		if (failedTests.size() > 0)
		{
			for (String str : failedTests)
			{
				System.out.println("     ==> " + str);
			}
		}
	}

	/**
	 * @return the mLogger
	 */
	public static synchronized Logger getmLogger() {
		return m_Logger;
	}

	/**
	 * @return the m_PagesSequence
	 */
	public synchronized ArrayList<PageBase> getM_PagesSequence() {
		return m_PagesSequence;
	}

	/**
	 * @return the m_AppOptionManager
	 */
	public synchronized AppOptionManager getM_AppOptionManager() {
		return m_AppOptionManager;
	}

	/**
	 * @return the m_ValidOptions
	 */
	public synchronized Boolean getM_ValidOptions() {
		return m_ValidOptions;
	}

}
