<%--
  Created by IntelliJ IDEA.
  User: 0x130
  Date: 2016-12-11
  Time: 오후 4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="signinBean" class="kr.ac.pusan.pnuips.bean.SigninBean"/>
<jsp:setProperty name="signinBean" property="email"/>
<jsp:setProperty name="signinBean" property="password"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign in...</title>
</head>
<body>
sign in...
<p>
    email =
    <jsp:getProperty name="signinBean" property="email"/>
</p>
<p>
    password =
    <jsp:getProperty name="signinBean" property="password"/>
</p>
</body>
</html>
