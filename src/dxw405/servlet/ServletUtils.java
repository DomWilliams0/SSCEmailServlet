package dxw405.servlet;

import dxw405.util.Logging;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ServletUtils
{
	public static final String MAILBOX_ATTRIBUTE = "mailbox";
	public static final String MAILBOX_URL = "/mailbox";

	public static final long TIMEOUT_SECONDS = 5;
	public static final String TIMEOUT_ATTRIBUTE = "last-activity";

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
	{showError(message, null, returnToRoot, req, resp, servlet);}

	/**
	 * Shows an error message with the given header after redirecting to the given page
	 *  @param message      The error message
	 * @param list An optional list of strings to display after the message
	 * @param returnToRoot True to return to the root page, otherwise to the current page
	 * @param req          The request
	 * @param resp         The response
	 * @param servlet      The servlet
	 */
	public static void showError(String message, List<String> list, boolean returnToRoot, HttpServletRequest req, HttpServletResponse resp, HttpServlet servlet)
			throws ServletException, IOException
	{
		showPopup("Error", message, list, returnToRoot, req, resp, servlet);
	}

	/**
	 * Shows a popup message with the given header after redirecting to the given page
	 * @param header       The popup header
	 * @param message      The popup message
	 * @param list          An optional list of strings to display after the message
	 * @param returnToRoot True to return to the root page, otherwise to the current page
	 * @param req          The request
	 * @param resp         The response
	 * @param servlet      The servlet
	 */
	public static void showPopup(String header, String message, List<String> list, boolean returnToRoot, HttpServletRequest req, HttpServletResponse resp, HttpServlet servlet)
			throws ServletException, IOException
	{
		req.setAttribute("popup-header", header);
		req.setAttribute("popup-message", message);

		if (list != null)
			req.setAttribute("popup-list", list);

		if (!returnToRoot)
		{

			try
			{
				Method doGet = servlet.getClass().getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
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
	{showPopup(header, message, null, returnToRoot, req, resp, servlet);}

	/**
	 * Updates the last activity time of the session, and checks if it has expired
	 *
	 * @param session The session
	 * @return True if the session has timed out, otherwise false
	 */
	public static boolean checkTimeout(HttpSession session)
	{
		Object attr = session.getAttribute(TIMEOUT_ATTRIBUTE);

		// checking
		if (attr != null)
		{
			long diff = System.currentTimeMillis() - (Long) attr;
			if (diff >= TIMEOUT_SECONDS * 1000)
				return true;

		}

		// update
		session.setAttribute(TIMEOUT_ATTRIBUTE, System.currentTimeMillis());
		return false;
	}
}
