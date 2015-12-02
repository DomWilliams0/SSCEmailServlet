package dxw405.servlet;

import dxw405.email.LoginDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class EmailLogin extends HttpServlet
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

		writer.println("Success!");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		LoginDetails login = new LoginDetails(req.getParameter("username"), req.getParameter("password"),
				req.getParameter("incoming-server"), req.getParameter("outgoing-server"),
				req.getParameter("incoming-port"), req.getParameter("outgoing-port"));

		resp.getWriter().println(login);

	}
}
