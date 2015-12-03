package dxw405.email;

import dxw405.util.Logging;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An email that is prepared to be sent
 */
public class PreparedEmail
{
	private Map<Recipient, List<Address>> recipients;
	private String subject, body;
	private List<String> errors;

	public PreparedEmail()
	{
		this(new HashMap<>(), "", "");
	}

	public PreparedEmail(Map<Recipient, List<Address>> recipients, String subject, String body)
	{
		this.recipients = recipients;
		this.subject = subject;
		this.body = body;
		this.errors = new ArrayList<>();
	}

	public List<Address> getRecipients(Recipient type)
	{
		List<Address> addresses = recipients.get(type);
		if (addresses == null)
		{
			addresses = new ArrayList<>();
			recipients.put(type, addresses);
		}
		return addresses;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	public void setRecipients(Recipient recipient, String addresses)
	{
		recipients.put(recipient, parseEmails(addresses));
	}

	private List<Address> parseEmails(String input)
	{
		List<Address> addresses = new ArrayList<>();

		// remove blanks
		String[] splitUnprocessed = input.split(";");
		List<String> split = new ArrayList<>();
		for (String s : splitUnprocessed)
			if (!s.isEmpty())
				split.add(s);

		if (split.isEmpty())
			return addresses;

		for (String s : split)
		{
			String email = s.trim();
			if (email.isEmpty())
				continue;

			try
			{
				InternetAddress e = new InternetAddress(email);
				e.validate();
				addresses.add(e);
			} catch (AddressException e)
			{
				Logging.warning("Invalid address", e);
				errors.add("Invalid address: " + email);
			}
		}

		return addresses;
	}

	/**
	 * @return If this email is valid
	 */
	public boolean validate()
	{
		// at least 1 recipient
		if (recipients.values().stream().map(List::size).reduce(0, Integer::sum) == 0)
			errors.add("At least 1 recipient must be given");

		// replace nulls
		if (subject == null)
			subject = "";
		if (body == null)
			body = null;

		// no errors
		return errors.isEmpty();
	}

	@Override
	public String toString()
	{
		return "PreparedEmail{" +
				"recipients=" + recipients +
				", subject='" + subject + '\'' +
				", body='" + body + '\'' +
				'}';
	}
}
