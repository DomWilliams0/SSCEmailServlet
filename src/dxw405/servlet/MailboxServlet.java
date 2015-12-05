package dxw405.servlet;

import dxw405.email.Mailbox;
import dxw405.email.PreparedEmail;
import dxw405.email.Recipient;

import javax.mail.MessagingException;
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
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession(false);
		if (!hasSession(session, req, resp))
			return;

		Mailbox mailbox = (Mailbox) session.getAttribute(ServletUtils.MAILBOX_ATTRIBUTE);
		req.setAttribute("email-address", mailbox.getEmailAddress());

		// logout
		if (req.getParameter("logout") != null)
		{
			mailbox.close();
			session.invalidate();

			ServletUtils.showPopup("Logged out", "You were successfully logged out. Come again!", true, req, resp, this);
			return;
		}

		// send to send page
		req.getRequestDispatcher("mailbox.jsp").include(req, resp);
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
			ServletUtils.showError("Could not send email", email.getErrors(), false, req, resp, this);
			return;
		}

		// check mailbox is still valid
		Mailbox mailbox = (Mailbox) session.getAttribute(ServletUtils.MAILBOX_ATTRIBUTE);
		if (!mailbox.isConnected())
		{
			ServletUtils.showError("Connection to mailbox lost", false, req, resp, this);
			return;
		}

		// attempt to send email
		try
		{
			mailbox.sendEmail(email);
		} catch (MessagingException e)
		{
			ServletUtils.showError("Could not send email: " + e.getMessage(), false, req, resp, this);
			return;
		}

		// success
		ServletUtils.showPopup("Email sent", "Your email was sent", false, req, resp, this);

	}

	private boolean hasSession(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if (session == null || session.getAttribute(ServletUtils.MAILBOX_ATTRIBUTE) == null)
		{
			ServletUtils.showError("You must login first", true, req, resp, this);
			return false;
		}
		return true;
	}
}
