package dxw405;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EmailLogin")
public class EmailLogin extends HttpServlet
{

//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
//	{
//		HttpSession session = req.getSession(true);
//
//		resp.setContentType("text/html");
//		resp.getWriter().println(session.isNew() ? "Hello!" : "Hello again!");
//	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.getWriter().println("Hello " + req.getParameter("username"));
	}
}
