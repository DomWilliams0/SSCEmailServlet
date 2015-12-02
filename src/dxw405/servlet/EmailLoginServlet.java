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
import java.io.PrintWriter;

@WebServlet("/login")
public class EmailLoginServlet extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// collect details
		LoginDetails login = new LoginDetails(req.getParameter("username"), req.getParameter("password"),
				req.getParameter("incoming-server"), req.getParameter("outgoing-server"),
				req.getParameter("incoming-port"), req.getParameter("outgoing-port"));

		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");

		// invalid: include fail popup
		if (!login.isValid())
		{
			writer.println("Failure!");
			req.getRequestDispatcher("/").include(req, resp);
			return;
		}

		// todo show connecting icon

		// connect to mailbox
		Mailbox mailbox = new Mailbox(0);
		if (!mailbox.connect(login))
		{
			// todo show failure popup and go back to login
			writer.println("FAILED TO CONNECT");
			req.getRequestDispatcher("/").include(req, resp);
			return;
		}

		// assign mailbox to session
		HttpSession session = req.getSession(true);
		session.setAttribute("mailbox", mailbox);

		// send to mailbox
		resp.sendRedirect("/mailbox");
	}
}
