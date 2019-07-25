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
import org.openqa.selenium.support.ui.Select;

public class PageAutotuneConfig extends PageBase 
{

	public PageAutotuneConfig() 
	{
		// TODO Auto-generated constructor stub
	}

	public PageAutotuneConfig(int m_TimeOutSec)
	{
		super(m_TimeOutSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performNaturalSequenceOfTests()
	{
		AppOptionManager appOptionManager = AppControl.getInstance().getM_AppOptionManager();
		/*
		 *
		// Options to configure AT run
		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_MIN_CARB_IMPACT", "-autotune-min-carb-impact")
				.setM_DefaultOptionValue("8")
				.setM_Mandatory(false));

		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_PUMP_BASAL_INCREMENT", "-autotune-pump-basal-increment")
				.setM_DefaultOptionValue("0.01")
				.setM_Mandatory(false));

		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_CATEGORIZE_UAM_AS_BASAL", "-autotune-categorize-uam-as-basal")
				.setM_DefaultOptionValue("false")
				.setM_ExpectBoolean(true)
				.setM_Mandatory(false));

		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_INSULIN_TYPE", "-autotune-insulin-type")
				.setM_DefaultOptionValue("Rapid")
				.addPermittedValue("Rapid")
				.addPermittedValue("Ultra")
				.setM_Mandatory(false));

		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_DAYS_DATA", "-autotune-days-data")
				.setM_DefaultOptionValue("7")
				.setM_ExpectInteger(true)
				.setM_AppOptionConstraint(new AppOptionConstraintInteger(1, 30))
				.setM_Mandatory(false));

		m_AppOptionManager.addOption(
				new AppOption("AUTOTUNE_EMAIL", "-autotune-email")
				.setM_Mandatory(true));
		 * 
		 */
		// Despite being an integer, we just get the string.  Options take care of ensuring it's an int
		String minCarbImpact = appOptionManager.getOptionValByName("AUTOTUNE_MIN_CARB_IMPACT");
		// Similarly, despite being a double, we just get the string.  Options take care of ensuring it's an double
		String pumpBasalIncr = appOptionManager.getOptionValByName("AUTOTUNE_PUMP_BASAL_INCREMENT");

		Boolean catUAMBasal = appOptionManager.getOptionByName("AUTOTUNE_CATEGORIZE_UAM_AS_BASAL").getBooleanValue();
		String insType = appOptionManager.getOptionValByName("AUTOTUNE_INSULIN_TYPE");
		// Despite being an integer, we just get the string.  Options take care of ensuring it's an int
		String daysData = appOptionManager.getOptionValByName("AUTOTUNE_DAYS_DATA");
		String email = appOptionManager.getOptionValByName("AUTOTUNE_EMAIL");



		// Locate and populate the Min 5 Mins Carb Impact field.
		m_WebDriver.findElement(By.xpath("/html/body/div[2]/form/div[1]/div[1]/div/div/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), minCarbImpact);

		// Locate and populate the Pump Basal Increment field.
		m_WebDriver.findElement(By.xpath("/html/body/div[2]/form/div[2]/div[1]/div/div/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), pumpBasalIncr);

		if (catUAMBasal == true)
		{
			m_WebDriver.findElement(By.xpath("/html/body/div[2]/form/div[3]/div[1]/div/label/input")).click();
		}
		
		// Locate the Insulin Type drop down and set based on option provided
		Select insTypeDropdown = new Select(m_WebDriver.findElement(By.xpath("/html/body/div[2]/form/div[4]/div[1]/select")));
		if (insType.equals("Rapid"))
		{
			insTypeDropdown.selectByIndex(0);
		}
		else if (insType.equals("Ultra"))
		{
			insTypeDropdown.selectByIndex(1);
		}
	
		// Locate and populate the Days of Data field.
		m_WebDriver.findElement(By.xpath("/html/body/div[2]/form/div[5]/div[1]/div/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), daysData);
		
		// Locate and populate the email field
		m_WebDriver.findElement(By.xpath("/html/body/div[2]/form/div[6]/div[1]/div/div/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), email);

		// Finally, press the Run Now button
		m_WebDriver.findElement(By.xpath("/html/body/div[2]/form/p[2]/input")).click();
	}

	@Override
	public String getPageName() 
	{
		return "3 - Autotune Config Page";
	}

}
