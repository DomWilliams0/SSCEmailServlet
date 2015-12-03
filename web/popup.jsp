<%
    Object header = request.getAttribute("popup-header");
    Object msg = request.getAttribute("popup-message");
    if (header != null && msg != null)
    {

        out.println("<div> " +
                "<h3>" + header + "</h3>" +
                msg +
                "</div>");
    }
%>