<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Send Mail - <%= request.getAttribute("email-address") %>
    </title>
    <jsp:include page="bootstrap.html"/>

    <style>
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }

        .compose-panel {
            max-width: 800px;
            padding: 15px;
            margin: 0 auto;
        }

        .compose-panel .form-control {
            position: relative;
            height: auto;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            padding: 10px;
            font-size: 16px;
        }

        .compose-panel .form-control:focus {
            z-index: 2;
        }

        label {
            margin-top: 13px;
        }

    </style>
</head>
<body>


<%!
    String createField(String label, String name, String placeholder, String initValue)
    {
        return String.format("<div class=\"form-group\">\n" +
                "    <label class=\"col-sm-1\">%s</label>\n" +
                "    <div class=\"col-sm-11\"><input class=\"form-control\" placeholder=\"%s\" type=\"text\" name=\"%s\" value=\"%s\"></div>\n" +
                "</div>", label, placeholder, name, initValue);
    }

    private String randomEmail()
    {
        char c = (char) ('a' + (Math.random() * 26));
        return c + "@" + c + ".com";
    }

    String createEmailField(String label)
    {
        return createField(label, label.toLowerCase(), randomEmail() + "; " + randomEmail(), "");
    }
%>


<div class="container">

    <jsp:include page="popup.jsp"/>

    <div class="panel panel-default compose-panel">
        <div class="panel-body">
            <form role="form" class="form-horizontal" action="mailbox" method="post" id="send-mail">

                <%= createEmailField("To") %>
                <%= createEmailField("Cc") %>
                <%= createEmailField("Bcc") %>

                <%= createField("Subject", "subject", "", "Re: ") %>

                <div class="form-group">
                    <label class="col-sm-12">Message</label>

                    <div class="col-sm-12"><textarea class="form-control" name="body" rows="10"></textarea></div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="submit" form="send-mail" class="btn btn-success pull-left">Send</button>

            <form action="mailbox" method="get" id="logout">
                <button type="submit" name = "logout" form="logout" value="yespls" class="btn btn-default">Logout</button>
            </form>

        </div>
    </div>

</div>

<%--<form action="mailbox" method="post" id="send-mail">--%>

<%--To: <input type="text" name="to" placeholder="example@example.com">--%>
<%--<br>--%>
<%--Cc: <input type="text" name="cc" placeholder="example@example.com">--%>
<%--<br>--%>
<%--Bcc: <input type="text" name="bcc" placeholder="example@example.com">--%>
<%--<br>--%>
<%--<br>--%>
<%--Subject: <input type="text" name="subject" value="Re: ">--%>
<%--<br>--%>

<%--<textarea name="body" form="send-mail" placeholder="Dear Sir/Madam,\n...">--%>

<%--</textarea>--%>

<%--<input type="submit" form="send-mail" value="Send">--%>
<%--</form>--%>

<%--<form action="mailbox" method="get" id="logout">--%>
<%--<button name="logout" value="yespls" type="submit">Logout</button>--%>
<%--</form>--%>

</body>
</html>
