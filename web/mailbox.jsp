<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Send Mail - <%= request.getAttribute("email-address") %>
    </title>
</head>
<body>

<jsp:include page="popup.jsp"/>

<form action="mailbox" method="post" id="send-mail">

    To: <input type="text" name="to" placeholder="example@example.com">
    <br>
    Cc: <input type="text" name="cc" placeholder="example@example.com">
    <br>
    Bcc: <input type="text" name="bcc" placeholder="example@example.com">
    <br>
    <br>
    Subject: <input type="text" name="subject" value="Re: ">
    <br>

    <textarea name="body" form="send-mail" placeholder="Dear Sir/Madam,\n...">

    </textarea>

    <input type="submit" form="send-mail" value="Send">
</form>

<form action="mailbox" method="get" id="logout">
    <button name="logout" value="yespls" type="submit">Logout</button>
</form>

</body>
</html>
