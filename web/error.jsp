<%
    Object error = request.getAttribute("error-message");
    if (error != null)
    {

        out.print("<div> " +
                "<h3>ERROR</h3>" +
                error +
                "</div>");
    }
%>