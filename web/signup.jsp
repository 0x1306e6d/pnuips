<%--
  Created by IntelliJ IDEA.
  User: 0x130
  Date: 2016-12-03
  Time: 오전 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Sign up</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="background-color: #eeeeee">
<nav class="navbar navbar-inverse" style="margin-bottom: 0; border-radius: 0">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">PNUIPS</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="index.jsp">Home</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Sign in</a></li>
            <li class="active"><a href="#"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
        </ul>
    </div>
</nav>
<div class="jumbotron text-center" style="margin-top: 0;">
    <h1>PNUIPS</h1>
    <p>PNU Item Purchase System</p>
</div>
<div class="container-fluid">
    <div class="col-md-offset-3 col-md-6">
        <div class="form-group">
            <label for="id">ID</label>
            <input id="id" type="text" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input id="password" type="password" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input id="email" type="email" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="birthday">Birthday</label>
            <input id="birthday" type="date" class="form-control"/>
        </div>
        <button id="sign-in" type="button" class="btn btn-primary btn-block">Sign up</button>
    </div>
</div>
</body>
</html>
