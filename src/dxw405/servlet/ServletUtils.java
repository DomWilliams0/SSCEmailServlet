package dxw405.servlet;

import dxw405.util.Logging;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServletUtils
{
	public static final String MAILBOX_ATTRIBUTE = "mailbox";
	public static final String MAILBOX_URL = "/mailbox";

	private ServletUtils()
	{
	}

	/**
	 * Shows an error message with the given header after redirecting to the given page
	 *
	 * @param message      The error message
	 * @param returnToRoot True to return to the root page, otherwise to the current page
	 * @param req          The request
	 * @param resp         The response
	 * @param servlet      The servlet
	 */
	public static void showError(String message, boolean returnToRoot, HttpServletRequest req, HttpServletResponse resp, HttpServlet servlet)
			throws ServletException, IOException
	{
		showPopup("Error", message, returnToRoot, req, resp, servlet);
	}

	/**
	 * Shows a popup message with the given header after redirecting to the given page
	 *
	 * @param header       The popup header
	 * @param message      The popup message
	 * @param returnToRoot True to return to the root page, otherwise to the current page
	 * @param req          The request
	 * @param resp         The response
	 * @param servlet      The servlet
	 */
	public static void showPopup(String header, String message, boolean returnToRoot, HttpServletRequest req, HttpServletResponse resp, HttpServlet servlet)
			throws ServletException, IOException
	{
		req.setAttribute("popup-header", header);
		req.setAttribute("popup-message", message);

		if (!returnToRoot)
		{

			try
			{
				Method doGet = servlet.getClass().getMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
				doGet.setAccessible(true);
				doGet.invoke(servlet, req, resp);
				return;
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
			{
				Logging.severe("Could not redirect to self", e);
			}
		}

		req.getRequestDispatcher("/").forward(req, resp);
	}

}
