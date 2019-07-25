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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.DavidRichardson.UnattendedAutotuneWeb.entity.AppLogger;

public class AppMainUnattendedAutotuneWeb 
{
	private static final Logger m_Logger = Logger.getLogger(AppLogger.class.getName());
	
	private AppControl          m_AppControl = null;
	
	public AppMainUnattendedAutotuneWeb(String[] args) 
	{
		m_AppControl = AppControl.getInstance();
		m_AppControl.initialize(args);
	}

	public static void main(String[] args) 
	{
		AppMainUnattendedAutotuneWeb main = new AppMainUnattendedAutotuneWeb(args);

		if (main.m_AppControl.getM_ValidOptions() == true)
		{
			main.runTests();
		}
		else
		{
			m_Logger.log(Level.SEVERE, main.m_AppControl.getM_AppOptionManager().summariseOptions());	
		}
	}

	public void runTests()
	{
		m_AppControl.createPages();
		m_AppControl.performTests();
		m_AppControl.summariseResults();
	}
}
