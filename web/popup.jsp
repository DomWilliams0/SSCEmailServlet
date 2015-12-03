<%
    Object header = request.getAttribute("popup-header");
    Object msg = request.getAttribute("popup-message");
    if (header != null && msg != null)
    {

        String alertType = ((String) header).toLowerCase().contains("error") ? "danger" : "info";

        String alert = "<div class=\"alert alert-" + alertType + " center-block\" role=\"alert\">\n" +
                "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "        <span aria-hidden=\"true\">&times;</span>\n" +
                "    </button>\n" +
                "    <strong>" + header + "</strong> " + msg + "\n" +
                "</div>";

        out.println(alert);
    }
%>