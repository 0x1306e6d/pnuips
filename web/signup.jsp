<%--
  Created by IntelliJ IDEA.
  User: 0x130
  Date: 2016-12-03
  Time: 오후 3:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign up</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    String birtyday = request.getParameter("birthday");
%>
<p>아이디: <%=id%>
</p>
<p>비밀번호: <%=password%>
</p>
<p>이메일: <%=email%>
</p>
<p>생년월일: <%=birtyday%>
</p>
</body>
</html>
