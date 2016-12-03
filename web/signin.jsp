<%--
  Created by IntelliJ IDEA.
  User: 0x130
  Date: 2016-12-03
  Time: 오후 3:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    String password = request.getParameter("password");
%>
<p>아이디: <%=id%>
</p>
<p>비밀번호: <%=password%>
</p>
</body>
</html>
