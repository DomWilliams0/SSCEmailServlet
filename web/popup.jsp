<%@ page import="java.util.List" %><%
    Object header = request.getAttribute("popup-header");
    Object msg = request.getAttribute("popup-message");
    if (header != null && msg != null)
    {
        String alertType = ((String) header).toLowerCase().contains("error") ? "danger" : "info";

        Object listParam = request.getAttribute("popup-list");
        String list = "";
        if (listParam != null)
        {
            List<String> listlist = (List<String>) listParam;

            StringBuilder sb = new StringBuilder("<ul>");
            listlist.forEach(s -> sb.append("<li>").append(s).append("</li>\n"));
            sb.append("</ul>");

            list = sb.toString();
        }


        String alert =
                "<div class=\"alert alert-" + alertType + " center-block\" role=\"alert\">\n" +
                "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "        <span aria-hidden=\"true\">&times;</span>\n" +
                "    </button>\n" +
                "    <strong>" + header + "</strong> " + msg + "\n" + list +
                "</div>";

        out.println(alert);
    }
%>