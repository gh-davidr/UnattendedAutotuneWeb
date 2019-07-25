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

public class AppOptionConstraintInteger extends AppOptionConstraint 
{
	private Integer m_MinValue = null;
	private Integer m_MaxValue = null;

	public AppOptionConstraintInteger() 
	{
		;
	}

	public AppOptionConstraintInteger(Integer m_MinValue, Integer m_MaxValue) 
	{
		super();
		this.m_MinValue = m_MinValue;
		this.m_MaxValue = m_MaxValue;
	}

	@Override
	public Boolean isValid(AppOption opt) 
	{
		Boolean result = false;
		
		Integer val = opt.getIntegerValue();
		result = (val != null && val >= m_MinValue && val <= m_MaxValue) ? true : false;
		
		return result;
	}
	
	@Override
	public String defineConstraint() 
	{
		String result = "Integer between " + m_MinValue + " and " + m_MaxValue;
		return result;
	}

	/**
	 * @return the m_MinValue
	 */
	public synchronized Integer getM_MinValue() {
		return m_MinValue;
	}

	/**
	 * @param m_MinValue the m_MinValue to set
	 */
	public synchronized AppOptionConstraintInteger setM_MinValue(Integer m_MinValue) {
		this.m_MinValue = m_MinValue;
		return this;
	}

	/**
	 * @return the m_MaxValue
	 */
	public synchronized Integer getM_MaxValue() {
		return m_MaxValue;
	}

	/**
	 * @param m_MaxValue the m_MaxValue to set
	 */
	public synchronized AppOptionConstraintInteger setM_MaxValue(Integer m_MaxValue) {
		this.m_MaxValue = m_MaxValue;
		return this;
	}

}
