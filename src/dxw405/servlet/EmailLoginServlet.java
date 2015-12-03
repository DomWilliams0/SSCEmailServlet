package dxw405.servlet;

import dxw405.email.LoginDetails;
import dxw405.email.Mailbox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class EmailLoginServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.sendRedirect("/");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// collect details
		LoginDetails login = new LoginDetails(req.getParameter("username"), req.getParameter("password"),
				req.getParameter("incoming-server"), req.getParameter("outgoing-server"),
				req.getParameter("incoming-port"), req.getParameter("outgoing-port"));

		// invalid: include fail popup
		if (!login.isValid())
		{
			ServletUtils.showError("Invalid input", true, req, resp, this);
			return;
		}

		// todo show connecting icon

		// connect to mailbox
		Mailbox mailbox = new Mailbox(0);
		if (!mailbox.connect(login))
		{
			ServletUtils.showError("Could not connect: bad login?", true, req, resp, this);
			return;
		}

		// assign mailbox to session
		HttpSession session = req.getSession(true);
		session.setAttribute(ServletUtils.MAILBOX_ATTRIBUTE, mailbox);

		// send to mailbox
		resp.sendRedirect(ServletUtils.MAILBOX_URL);
	}
}
