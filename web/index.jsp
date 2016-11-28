<%--
  Created by IntelliJ IDEA.
  User: 0x130
  Date: 2016-11-28
  Time: 오전 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>201424411</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
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
        <button id="sign-in" type="button" class="btn btn-primary btn-block">Sign in</button>
    </div>
</div>
</body>
</html>
