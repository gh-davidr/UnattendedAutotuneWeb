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

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

public class AppOption 
{

	private String   m_OptionName         = null;
	private String   m_OptionFlag         = null;  // Actual text expected on command line
	private String   m_OptionValue        = null;  // Actual value if presented on command line

	private String   m_DefaultOptionValue = null;

	private AppOptionConstraint m_AppOptionConstraint = null;

	private HashSet<String>  m_PermittedValues = new HashSet<String>();
	private Boolean          m_Mandatory       = false;
	private String           m_MandatoryIfSet  = null;  // This option becomes mandatory if another with this name is set
	private String           m_MandatoryIfVal  = null;  // In addition, this option becomes mandatory if other option has this val

	private Boolean          m_ExpectInteger   = false;
	private Boolean          m_ExpectDouble    = false;
	private Boolean          m_ExpectBoolean   = false;
	private Boolean          m_ExpectPath      = false;
	private Boolean          m_ExpectURI       = false;

	private Validity         m_Validity        = Validity.Unknown;

	public enum Validity
	{
		Unknown,
		Valid,
		MandatoryNoValue,
		NotAPermittedValue,
		ExpectButNotInteger,
		ExpectButNotDouble,
		ExpectButNotBoolean,
		ExpectButNotPath,
		ExpectButNotURI,
		ViolatesConstraint,
	};

	public AppOption(String m_OptionName, String m_OptionFlag)
	{
		super();
		this.m_OptionName = m_OptionName;
		this.m_OptionFlag = m_OptionFlag;
	}

	public Boolean isOptionValid()
	{
		Boolean result = true;

		if (m_Validity == Validity.Unknown)
		{
			m_Validity = Validity.Valid;
			
			// Check what we can internally once the options have been parsed

			// Mandatory option but none presented.
			if (m_Mandatory == true && getM_OptionValue() == null)
			{
				result = false;
				m_Validity = Validity.MandatoryNoValue;
			}

			else if (getM_OptionValue() != null)
			{
				result = checkValueSetValidity(result, m_PermittedValues.size() > 0, m_PermittedValues.contains(getM_OptionValue().toUpperCase()), Validity.NotAPermittedValue);
				result = checkValueSetValidity(result, m_ExpectInteger, isInteger(getM_OptionValue()), Validity.ExpectButNotInteger);
				result = checkValueSetValidity(result, m_ExpectDouble, isDouble(getM_OptionValue()), Validity.ExpectButNotDouble);
				result = checkValueSetValidity(result, m_ExpectBoolean, isBoolean(getM_OptionValue()), Validity.ExpectButNotBoolean);
				result = checkValueSetValidity(result, m_ExpectPath, isPath(getM_OptionValue()), Validity.ExpectButNotPath);
				result = checkValueSetValidity(result, m_ExpectURI, isURI(getM_OptionValue()), Validity.ExpectButNotURI);

				if (m_AppOptionConstraint != null)
				{
					result = checkValueSetValidity(result, true, m_AppOptionConstraint.isValid(this), Validity.ViolatesConstraint);
				}
			}
		}
		else
		{
			result = m_Validity == Validity.Valid ? true : false;
		}

		return result;
	}

	public Integer getIntegerValue()
	{
		Integer result = null;

		if (getM_ExpectInteger() && isOptionValid())
		{
			result = Integer.parseInt(getM_OptionValue());
		}

		return result;
	}

	public Double getDoubleValue()
	{
		Double result = null;

		if (getM_ExpectDouble() && isOptionValid())
		{
			result = Double.parseDouble(getM_OptionValue());
		}

		return result;
	}

	public Boolean getBooleanValue()
	{
		Boolean result = null;

		if (getM_ExpectBoolean() && isOptionValid())
		{
			result = Boolean.parseBoolean(getM_OptionValue());
		}

		return result;
	}

	/**
	 * @return the m_OptionName
	 */
	public synchronized String getM_OptionName() {
		return m_OptionName;
	}

	/**
	 * @return the m_OptionFlag
	 */
	public synchronized String getM_OptionFlag() {
		return m_OptionFlag;
	}

	/**
	 * @return the m_OptionValue
	 */
	public synchronized String getM_OptionValue() 
	{
		return m_OptionValue == null ? m_DefaultOptionValue : m_OptionValue;
	}

	/**
	 * @param m_OptionValue the m_OptionValue to set
	 */
	public synchronized void setM_OptionValue(String m_OptionValue) {
		this.m_OptionValue = m_OptionValue;
	}




	/**
	 * @return the m_AppOptionConstraint
	 */
	public synchronized AppOptionConstraint getM_AppOptionConstraint() {
		return m_AppOptionConstraint;
	}

	/**
	 * @param m_AppOptionConstraint the m_AppOptionConstraint to set
	 */
	public synchronized AppOption setM_AppOptionConstraint(AppOptionConstraint m_AppOptionConstraint) {
		this.m_AppOptionConstraint = m_AppOptionConstraint;
		return this;
	}

	/**
	 * @return the m_DefaultOptionValue
	 */
	public synchronized String getM_DefaultOptionValue() {
		return m_DefaultOptionValue;
	}

	/**
	 * @param m_DefaultOptionValue the m_DefaultOptionValue to set
	 */
	public synchronized AppOption setM_DefaultOptionValue(String m_DefaultOptionValue) {
		this.m_DefaultOptionValue = m_DefaultOptionValue;
		return this;
	}

	public AppOption addPermittedValue(String val)
	{
		m_PermittedValues.add(val.toUpperCase());
		return this;
	}

	/**
	 * @return the m_PermittedValues
	 */
	public synchronized HashSet<String> getM_PermittedValues() {
		return m_PermittedValues;
	}

	/**
	 * @return the m_Mandatory
	 */
	public synchronized Boolean getM_Mandatory() {
		return m_Mandatory;
	}

	/**
	 * @param m_Mandatory the m_Mandatory to set
	 */
	public synchronized AppOption setM_Mandatory(Boolean m_Mandatory) {
		this.m_Mandatory = m_Mandatory;
		return this;
	}

	/**
	 * @return the m_MandatoryIfSet
	 */
	public synchronized String getM_MandatoryIfSet() {
		return m_MandatoryIfSet;
	}

	/**
	 * @param m_MandatoryIfSet the m_MandatoryIfSet to set
	 */
	public synchronized AppOption setM_MandatoryIfSet(String m_MandatoryIfSet) {
		this.m_MandatoryIfSet = m_MandatoryIfSet;
		return this;
	}



	/**
	 * @return the m_MandatoryIfVal
	 */
	public synchronized String getM_MandatoryIfVal() {
		return m_MandatoryIfVal;
	}

	/**
	 * @param m_MandatoryIfVal the m_MandatoryIfVal to set
	 */
	public synchronized AppOption setM_MandatoryIfVal(String m_MandatoryIfVal) {
		this.m_MandatoryIfVal = m_MandatoryIfVal;
		return this;
	}

	/**
	 * @return the m_ExpectInteger
	 */
	public synchronized Boolean getM_ExpectInteger() {
		return m_ExpectInteger;
	}

	/**
	 * @param m_ExpectInteger the m_ExpectInteger to set
	 */
	public synchronized AppOption setM_ExpectInteger(Boolean m_ExpectInteger) {
		this.m_ExpectInteger = m_ExpectInteger;
		return this;
	}

	/**
	 * @return the m_ExpectDouble
	 */
	public synchronized Boolean getM_ExpectDouble() {
		return m_ExpectDouble;
	}

	/**
	 * @param m_ExpectDouble the m_ExpectDouble to set
	 */
	public synchronized AppOption setM_ExpectDouble(Boolean m_ExpectDouble) {
		this.m_ExpectDouble = m_ExpectDouble;
		return this;
	}

	/**
	 * @return the m_ExpectBoolean
	 */
	public synchronized Boolean getM_ExpectBoolean() {
		return m_ExpectBoolean;
	}

	/**
	 * @param m_ExpectBoolean the m_ExpectBoolean to set
	 */
	public synchronized AppOption setM_ExpectBoolean(Boolean m_ExpectBoolean) {
		this.m_ExpectBoolean = m_ExpectBoolean;
		return this;
	}

	/**
	 * @return the m_ExpectPath
	 */
	public synchronized Boolean getM_ExpectPath() {
		return m_ExpectPath;
	}

	/**
	 * @param m_ExpectPath the m_ExpectPath to set
	 */
	public synchronized AppOption setM_ExpectPath(Boolean m_ExpectPath) {
		this.m_ExpectPath = m_ExpectPath;
		return this;
	}

	/**
	 * @return the m_ExpectURI
	 */
	public synchronized Boolean getM_ExpectURI() {
		return m_ExpectURI;
	}

	/**
	 * @param m_ExpectURI the m_ExpectURI to set
	 */
	public synchronized AppOption setM_ExpectURI(Boolean m_ExpectURI) {
		this.m_ExpectURI = m_ExpectURI;
		return this;
	}

	/**
	 * @return the m_Validity
	 */
	public synchronized Validity getM_Validity() {
		return m_Validity;
	}

	/**
	 * @param m_Validity the m_Validity to set
	 */
	public synchronized void setM_Validity(Validity m_Validity) {
		this.m_Validity = m_Validity;
	}

	private static Boolean isURI(String s)
	{
		Boolean result = true;

		try 
		{
			new URI(s);
		}
		catch (URISyntaxException e) 
		{
			result = false;
		}

		return result;
	}


	private static Boolean isPath(String s)
	{
		Boolean result = false;

		File file = new File(s);
		result = !file.isDirectory() && file.exists();		

		return result;
	}

	private static Boolean isBoolean(String s) 
	{
		Boolean result = false;
		String upS = s.toUpperCase();
		if (upS.equals("TRUE") || upS.equals("FALSE"))
		{
			result = true;
		}

		return result;
	}

	private static Boolean isDouble(String s)
	{
		Boolean result = true;
		try
		{
			Double.parseDouble(s);
		}
		catch(NumberFormatException e)
		{
			result = false;
		}
		return result;
	}


	private static Boolean isInteger(String s) 
	{
		Boolean result = isInteger(s,10);
		return result;
	}

	private static Boolean isInteger(String s, int radix) 
	{
		Boolean result = true;
		if(s.isEmpty()) return false;
		for(int i = 0; i < s.length() && result == true; i++) 
		{
			if (i == 0 && s.charAt(i) == '-') 
			{
				if(s.length() == 1) 
				{
					result = false;
				}
			}
			else if(Character.digit(s.charAt(i),radix) < 0) 
			{
				result = false;
			}
		}
		return result;
	}

	private Boolean checkValueSetValidity(Boolean currResult, Boolean expectType, Boolean isType, Validity validity)
	{
		// Set result to return as previous value by default
		Boolean result = currResult;

		// If type check is false, only then take action
		if (currResult == true && // Preserve the first validity result found
				expectType == true && isType == false)
		{
			// Set validity as invalid option provided
			m_Validity = validity;
			// Ensure result gets set as false going back
			result = false;
		}

		return result;
	}
}
