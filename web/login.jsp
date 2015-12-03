<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<jsp:include page="popup.jsp"/>

<%!
    /**
     * Creates an input field with the given input value
     * @param parameter The input's name
     * @param password If this is a password field
     * @param initValue The initial value of the input field
     */
    String createField(String parameter, boolean password, String initValue)
    {
        String extra = password ? "type=\"password\"" : "";
        return "<input name=\"" + parameter + "\" " + extra +
                "value=\"" + initValue + "\">";
    }

    /**
     * Helper for creating a field from the request's current value
     */
    String createField(String parameter, HttpServletRequest req)
    {
        return createField(parameter, false, getValue(parameter, req));
    }

    /**
     * Creates a field with the given first time default, which is overwritten if the request has a value
     */
    String createField(String parameter, String firstTimeDefault, HttpServletRequest req)
    {
        return createField(parameter, false, req.getParameter(parameter) == null ? firstTimeDefault : getValue(parameter, req));
    }

    String getValue(String parameter, HttpServletRequest req)
    {
        String p = req.getParameter(parameter);
        return p == null ? "" : p;
    }
%>

<h2>Login</h2>
<form action="login" method="post">
    Username: <%= createField("username", request) %>
    <br>
    Password: <%= createField("password", true, "") %>
    <br>

    Incoming Server: <%= createField("incoming-server", "imap.gmail.com", request) %>
    <br>
    Incoming Port: <%= createField("incoming-port", Integer.toString(993), request) %>
    <br>

    Outgoing Server: <%= createField("outgoing-server", "smtp.gmail.com", request) %>
    <br>
    Outgoing Port: <%= createField("outgoing-port", Integer.toString(587), request) %>
    <br>

    <input type="submit" value="Login">
</form>

</body>
</html>