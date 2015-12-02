package dxw405.servlet;

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

		// no session
		if (session == null || session.getAttribute(MAILBOX_ATTRIBUTE) == null)
		{
			req.setAttribute("error-message", "You must login first");
			req.getRequestDispatcher("/").forward(req, resp);
			return;
		}

		resp.getWriter().println("Mail!");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// todo
	}
}
