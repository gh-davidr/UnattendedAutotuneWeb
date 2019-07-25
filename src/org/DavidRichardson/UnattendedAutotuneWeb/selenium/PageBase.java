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

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageBase 
{
	private int m_TimeOutSec = 45;
	private int m_TestNum    = 1;
	
	protected ArrayList<String>  m_PassedTests = new ArrayList<String>();
	protected ArrayList<String>  m_FailedTests = new ArrayList<String>();
	
	protected WebDriver   m_WebDriver = null;   

	
	public PageBase() 
	{
		super();
		m_WebDriver = AppSingleWebDriver.getInstance().getM_WebDriver();
	}
	
	public PageBase(int m_TimeOutSec) 
	{
		super();
		this.m_TimeOutSec = m_TimeOutSec;
	}

	public abstract void   performNaturalSequenceOfTests();
	public abstract String getPageName();
	
	protected WebElement getWebElement(String xPath)
	{
		WebElement result = null;
		
		try
		{
		result = m_WebDriver.findElement(By.xpath(xPath));
		}
		catch (NoSuchElementException e)
		{
			result = null;
		}
		
		return result;
	}
	
	protected void driverWait(WebDriver webDriver, By by) 
	{
		WebDriverWait myWait = new WebDriverWait(webDriver, m_TimeOutSec);
		ExpectedCondition<Boolean> conditionToCheck = new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply(WebDriver input) {
				return (input.findElements(by).size() > 0);
			}
		};
		myWait.until(conditionToCheck);
	}
	
	protected void recordTestResult(WebDriver webDriver, String test, By by)
	{
		if (this.assertCanBeFound(webDriver, by))
		{
			m_PassedTests.add(getPageName() + ":(" + m_TestNum + "): " + test);
		}
		else
		{
			m_FailedTests.add(getPageName() + ":(" + m_TestNum + "): " + test);
		}
		m_TestNum++;
	}
	
	protected void recordTestResult(Boolean result, String test)
	{
		if (result == true)
		{
			m_PassedTests.add(test);
		}
		else
		{
			m_FailedTests.add(test);
		}
	}
	
	protected Boolean assertCanBeFound(WebDriver webDriver, By by) 
	{
		Boolean result = webDriver.findElements(by).size() > 0 ? true : false;
		return result;
	}

	/**
	 * @return the m_TimeOutSec
	 */
	public synchronized int getM_TimeOutSec() {
		return m_TimeOutSec;
	}

	/**
	 * @return the m_PassedTests
	 */
	public synchronized ArrayList<String> getM_PassedTests() {
		return m_PassedTests;
	}

	/**
	 * @return the m_FailedTests
	 */
	public synchronized ArrayList<String> getM_FailedTests() {
		return m_FailedTests;
	}
	
	
}