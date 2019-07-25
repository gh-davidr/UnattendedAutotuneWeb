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
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.DavidRichardson.UnattendedAutotuneWeb.entity.AppLogger;
import org.DavidRichardson.UnattendedAutotuneWeb.entity.AppOption;

public class AppOptionManager 
{
	private static final Logger m_Logger = Logger.getLogger(AppLogger.class.getName());

	HashMap<String, AppOption> m_Options = new HashMap<String, AppOption>();
	HashMap<String, AppOption> m_OptionByNames = new HashMap<String, AppOption>();

	public AppOptionManager() 
	{
		;
	}

	public Boolean processArgs(String[] argv)
	{
		Boolean result = readArgs(argv) && determineValidity();

		return result;
	}

	public void addOption(AppOption opt)
	{
		m_Options.put(opt.getM_OptionFlag().toUpperCase(), opt);
		m_OptionByNames.put(opt.getM_OptionName().toUpperCase(), opt);
	}

	public AppOption getOptionByName(String optName)
	{
		AppOption result = m_OptionByNames.get(optName.toUpperCase());
		return result;
	}

	public AppOption getOption(String optName)
	{
		AppOption result = m_Options.get(optName.toUpperCase());
		return result;

	}

	public String getOptionValByName(String optName)
	{
		String result = null;
		AppOption opt = m_OptionByNames.get(optName.toUpperCase());
		if (opt != null)
		{
			result = opt.getM_OptionValue();
		}
		return result;
	}

	public String getOptionVal(String optName)
	{
		String result = null;
		AppOption opt = m_Options.get(optName.toUpperCase());
		if (opt != null)
		{
			result = opt.getM_OptionValue();
		}
		return result;
	}

	public String summariseOptions()
	{
		String result = null;
		HashSet<String> explainedOptions = new HashSet<String>();
		StringBuilder sb = new StringBuilder();

		sb.append("All possible options are as follows:\n");

		for (String key : m_Options.keySet()) 
		{
			if (!explainedOptions.contains(key))
			{
				summariseOption(key, explainedOptions, sb);
			}
		}

		result = sb.toString();

		return result;
	}

	private void summariseOption(String key, HashSet<String> explainedOptions, StringBuilder sb)
	{
		AppOption opt = m_Options.get(key);
		if (opt != null)
		{
			String indent = "  ";
			String subIndent = "    ";
			String cr     = "\n";
			HashSet<String> permVals = opt.getM_PermittedValues();
			ArrayList<String> dependentOpts = new ArrayList<String>();

			// Search for all dependent options
			for (String key2 : m_Options.keySet()) 
			{
				AppOption opt2 = m_Options.get(key2);
				String mandIfSetOpt = opt2.getM_MandatoryIfSet() == null ? "" : opt2.getM_MandatoryIfSet();
				String mandIfSetVal = opt2.getM_MandatoryIfVal() == null ? "" : opt2.getM_MandatoryIfVal();

				if (mandIfSetOpt.equals(opt.getM_OptionName()))
				{
					dependentOpts.add(opt2.getM_OptionFlag() + 
							(mandIfSetVal != null ? " (Only if " + opt.getM_OptionFlag() + " value is: " + mandIfSetVal + ")" : ""));
				}
			}

			sb.append(indent + opt.getM_OptionFlag() + "\n");

			sb.append(isOptSet(opt.getM_Mandatory()) ? subIndent + "*Mandatory*" + cr: "");
			sb.append(isOptSet(opt.getM_ExpectInteger()) ? subIndent + "Followed by ... *Integer Numeric Value*" + cr: "");
			sb.append(isOptSet(opt.getM_ExpectDouble()) ? subIndent + "Followed by ... *Double Numeric Value*" + cr: "");
			sb.append(isOptSet(opt.getM_ExpectBoolean()) ? subIndent + "Followed by ... *Boolean Numeric Value*" + cr: "");
			sb.append(isOptSet(opt.getM_ExpectPath()) ? subIndent + "Followed by ... *Valid File Path*" + cr: "");
			sb.append(isOptSet(opt.getM_ExpectURI()) ? subIndent + "Followed by ... *Valid URL*" + cr: "");

			if (permVals.size() > 0)
			{
				sb.append(subIndent + "Followed by one of Permitted Values:" + cr);
				for (String s : permVals)
				{
					sb.append(subIndent + subIndent + s + cr);
				}
			}
			String def = opt.getM_DefaultOptionValue();
			sb.append(isOptSet(def != null) ? subIndent + "Defaulted to: '" + def + "'" + cr: "");

			if (dependentOpts.size() > 0)
			{
				sb.append(subIndent + "Dependent Options that must also be set:" + cr);
				for (String s : dependentOpts)
				{
					sb.append(subIndent + subIndent + s + cr);
				}
			}

		}
	}

	private Boolean isOptSet(Boolean opt)
	{
		Boolean result = false;
		result = opt != null ? opt : result;
		return result;
	}

	private Boolean readArgs(String[] argv)
	{
		Boolean result = true;

		Boolean seenOpt = false;
		AppOption cliOpt = null;

		for (String s : argv)
		{
			if (result == true)
			{
				if (seenOpt == false)
				{
					seenOpt = true;
					cliOpt = m_Options.get(s.toUpperCase());
					if (cliOpt == null)
					{
						m_Logger.log(Level.INFO, "Error - Option: " + s + " is not recognized");
						result = false;
					}
				}
				else if (cliOpt != null)
				{
					cliOpt.setM_OptionValue(s);
					seenOpt = false;
				}
			}

		}

		return result;

	}


	private Boolean determineValidity()
	{
		Boolean result = true;

		for (String key : m_Options.keySet()) 
		{
			if (result == true)
			{
				AppOption cliOpt = m_Options.get(key);

				if (!cliOpt.isOptionValid())
				{
					AppOption.Validity validity = cliOpt.getM_Validity();

					switch (validity)
					{
					case Unknown:
						break;
					case Valid:
						m_Logger.log(Level.INFO, "Internal Inconsistency - Option: " + cliOpt.getM_OptionFlag() + " is actually valid.");
						result = false;
						break;
					case MandatoryNoValue:
						m_Logger.log(Level.INFO, "Error - Option: " + cliOpt.getM_OptionFlag() + " is mandatory but no option set.");
						result = false;
						break;
					case NotAPermittedValue:
						m_Logger.log(Level.INFO, "Error - Option: " + cliOpt.getM_OptionFlag() + " has certain permitted values but none were specified (" + cliOpt.getM_OptionValue() + ")");
						result = false;				
						break;
					case ExpectButNotInteger:
						m_Logger.log(Level.INFO, "Error - Option: " + cliOpt.getM_OptionFlag() + " should be an integer (" + cliOpt.getM_OptionValue() + ")");
						result = false;				
						break;
					case ExpectButNotDouble:
						m_Logger.log(Level.INFO, "Error - Option: " + cliOpt.getM_OptionFlag() + " should be a double (" + cliOpt.getM_OptionValue() + ")");
						result = false;				
						break;
					case ExpectButNotBoolean:
						m_Logger.log(Level.INFO, "Error - Option: " + cliOpt.getM_OptionFlag() + " should be a boolean (" + cliOpt.getM_OptionValue() + ")");
						result = false;				
						break;
					case ExpectButNotPath:
						m_Logger.log(Level.INFO, "Error - Option: " + cliOpt.getM_OptionFlag() + " should be a path to an existing file (" + cliOpt.getM_OptionValue() + ")");
						result = false;				
						break;
					case ExpectButNotURI:
						m_Logger.log(Level.INFO, "Error - Option: " + cliOpt.getM_OptionFlag() + " should be a valid URL (" + cliOpt.getM_OptionValue() + ")");
						result = false;				
						break;
					case ViolatesConstraint:
						m_Logger.log(Level.INFO, "Error - Option: " + cliOpt.getM_OptionFlag() +
								" violates the constraint "  +  cliOpt.getM_AppOptionConstraint().defineConstraint() + 
								" (" + cliOpt.getM_OptionValue() + ")");
						result = false;				
						break;
					default:
						break;

					}
				}
			}
		}

		return result;
	}

}
