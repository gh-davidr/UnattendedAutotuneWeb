package org.DavidRichardson.UnattendedAutotuneWeb.entity;

public class AppVersion 
{
	private String m_VersionString = "V0.1";
	
	public AppVersion() 
	{
		;
	}

	/**
	 * @return the m_VersionString
	 */
	public synchronized String getM_VersionString() {
		return m_VersionString;
	}

}
