package dxw405.email;

import dxw405.util.Utils;

/**
 * Simple holder and validator of login details
 */
public class LoginDetails
{
	private String user, pass, incomingHost, outgoingHost;
	private Integer incomingPort, outgoingPort;

	public LoginDetails(String user, String pass, String incomingHost, String outgoingHost, String incomingPort, String outgoingPort)
	{
		this.user = user;
		this.pass = pass;
		this.incomingHost = incomingHost;
		this.outgoingHost = outgoingHost;
		this.incomingPort = Utils.stringToInt(incomingPort);
		this.outgoingPort = Utils.stringToInt(outgoingPort);
	}

	public boolean isValid()
	{
		return validString(user) &&
				validString(pass) &&
				validString(incomingHost) &&
				validString(outgoingHost) &&
				incomingPort != null &&
				outgoingPort != null;
	}

	private boolean validString(String s)
	{
		return s != null && !s.isEmpty();
	}

	public String getUser()
	{
		return user;
	}

	public String getPass()
	{
		return pass;
	}

	public String getIncomingHost()
	{
		return incomingHost;
	}

	public String getOutgoingHost()
	{
		return outgoingHost;
	}

	public Integer getIncomingPort()
	{
		return incomingPort;
	}

	public Integer getOutgoingPort()
	{
		return outgoingPort;
	}
}
