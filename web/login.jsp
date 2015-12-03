<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <jsp:include page="bootstrap.html"/>

    <style>
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }

        .form-signin {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }

        .form-signin .form-control {
            position: relative;
            height: auto;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            padding: 10px;
            font-size: 16px;
        }

        .form-signin .form-control:focus {
            z-index: 2;
        }

        .form-signin input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }

        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }

        .fill {
            width: 100%;
        }


    </style>

</head>
<body>

<jsp:include page="popup.jsp"/>

<%!
    /**
     * Creates an input field with the given input value
     *
     * @param parameter The input's name
     * @param password If this is a password field
     * @param initValue The initial value of the input field
     * @param placeholder Placeholder text
     * @param addLabel If a label should be added for this field
     */
    String createField(String parameter, boolean password, String initValue, String placeholder, boolean addLabel)
    {
        String extra = password ? "type=\"password\"" : "";
        String label = !addLabel ? "" : "<label for=\"" + parameter + "\" class=\"control-label\">" + placeholder + "</label>\n";
        return label + String.format("<input class=\"form-control\" value=\"%s\" name=\"%s\" placeholder=\"%s\" id=\"%s\", %srequired>",
                initValue, parameter, placeholder, parameter, extra);
    }

    /**
     * Helper for creating a field from the request's current value
     */
    String createField(String parameter, String label, HttpServletRequest req)
    {
        return createField(parameter, false, getValue(parameter, req), label, false);
    }

    /**
     * Creates a field with the given first time default, which is overwritten if the request has a value
     */
    String createField(String parameter, String label, String firstTimeDefault, HttpServletRequest req)
    {
        return createField(parameter, false, req.getParameter(parameter) == null ? firstTimeDefault : getValue(parameter, req), label, true);
    }

    String getValue(String parameter, HttpServletRequest req)
    {
        String p = req.getParameter(parameter);
        return p == null ? "" : p;
    }
%>

<div class="container">

    <form class="form-group form-signin" action="login" method="post">
        <h2 class="form-signin-heading">Login</h2>
        <%= createField("username", "Email address", request) %>
        <%= createField("password", true, "", "Password", false) %>

        <a class="btn btn-default fill form-control" data-toggle="collapse" href="#serverSettings">
            Server Settings
        </a>

        <div class="collapse well" id="serverSettings">
            <%= createField("incoming-server", "IMAP server", "imap.gmail.com", request) %>
            <%= createField("incoming-port", "IMAP port", Integer.toString(993), request) %>

            <%= createField("outgoing-server", "SMTP server", "smtp.gmail.com", request) %>
            <%= createField("outgoing-port", "SMTP port", Integer.toString(587), request) %>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form>

</div>

</body>
</html>