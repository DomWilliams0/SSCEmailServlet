package dxw405.servlet;

import dxw405.email.Mailbox;
import dxw405.email.PreparedEmail;
import dxw405.email.Recipient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/mailbox")
public class MailboxServlet extends HttpServlet
{
	public static final String MAILBOX_ATTRIBUTE = "mailbox";
	public static final String MAILBOX_URL = "/mailbox";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession(false);
		if (!hasSession(session, req, resp))
			return;

		Mailbox mailbox = (Mailbox) session.getAttribute(MAILBOX_ATTRIBUTE);
		req.setAttribute("email-address", mailbox.getEmailAddress());

		// send to send page
		req.getRequestDispatcher("mailbox.jsp").include(req, resp);
	}

	private boolean hasSession(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if (session == null || session.getAttribute(MAILBOX_ATTRIBUTE) == null)
		{
			error("You must login first", true, req, resp);
			return false;
		}
		return true;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession(false);
		if (!hasSession(session, req, resp))
			return;

		// populate email with parameters
		PreparedEmail email = new PreparedEmail();
		email.setRecipients(Recipient.TO, req.getParameter("to"));
		email.setRecipients(Recipient.CC, req.getParameter("cc"));
		email.setRecipients(Recipient.BCC, req.getParameter("bcc"));
		email.setSubject(req.getParameter("subject"));
		email.setBody(req.getParameter("body"));

		// invalid email
		if (!email.validate())
		{
			String errorMessage = "Could not send email!<br> -" + String.join("<br> -", email.getErrors());
			error(errorMessage, false, req, resp);
			return;
		}

		// check mailbox is still valid
		Mailbox mailbox = (Mailbox) session.getAttribute(MAILBOX_ATTRIBUTE);
		if (!mailbox.isConnected())
		{
			error("Connection to mailbox lost", false, req, resp);
			return;
		}


	}

	/**
	 * Shows an error message with the given message after redirecting to the given page
	 *
	 * @param message      The error message
	 * @param returnToRoot True to return to the root page, otherwise to the current page
	 * @param req          The request
	 * @param resp         The response
	 */
	private void error(String message, boolean returnToRoot, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("error-message", message);
		if (returnToRoot)
			req.getRequestDispatcher("/").forward(req, resp);
		else
			doGet(req, resp);
	}
}
