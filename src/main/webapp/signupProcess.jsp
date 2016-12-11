<%--
  Created by IntelliJ IDEA.
  User: 0x130
  Date: 2016-12-11
  Time: 오후 4:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="signupBean" class="kr.ac.pusan.pnuips.bean.SignupBean"/>
<jsp:setProperty name="signupBean" property="email"/>
<jsp:setProperty name="signupBean" property="password"/>
<jsp:setProperty name="signupBean" property="firstname"/>
<jsp:setProperty name="signupBean" property="lastname"/>
<jsp:setProperty name="signupBean" property="birthday"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign up...</title>
</head>
<body>
sign up...
<p>
    email =
    <jsp:getProperty name="signupBean" property="email"/>
</p>
<p>
    password =
    <jsp:getProperty name="signupBean" property="password"/>
</p>
<p>
    firstname =
    <jsp:getProperty name="signupBean" property="firstname"/>
</p>
<p>
    lastname =
    <jsp:getProperty name="signupBean" property="lastname"/>
</p>
<p>
    birthday =
    <jsp:getProperty name="signupBean" property="birthday"/>
</p>
</body>
</html>
