<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<%!
    String createField(String parameter, boolean password, String initValue)
    {
        String extra = password ? "type=\"password\"" : "";
        return "<input name=\"" + parameter + "\" " + extra +
                "value=\"" + initValue + "\">";
    }

    String createField(String parameter, HttpServletRequest req)
    {
        return createField(parameter, false, getValue(parameter, req));
    }

    String createPort(String parameter, Integer firstTimeDefault, HttpServletRequest req)
    {
        return createField(parameter, false, req.getParameter(parameter) == null ? firstTimeDefault.toString() : getValue(parameter, req));
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

    Incoming Server: <%= createField("incoming-server", request) %>
    <br>
    Incoming Port: <%= createPort("incoming-port", 993, request) %>
    <br>

    Outgoing Server: <%= createField("outgoing-server", request) %>
    <br>
    Outgoing Port: <%= createPort("outgoing-port", 587, request) %>
    <br>

    <input type="submit">
</form>

</body>
</html>