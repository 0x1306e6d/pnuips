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
<body style="padding-bottom: 15px;">
<nav class="navbar navbar-inverse" style="margin-bottom: 0; border-radius: 0">
    <div class="container-fluid">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation bar</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">PNUIPS</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="bestseller.jsp">Best Seller</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="signinForm.jsp"><span class="glyphicon glyphicon-log-in"></span> Sign in</a></li>
                <li><a href="signupForm.jsp"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="jumbotron text-center" style="margin-top: 0;">
    <h1>PNUIPS</h1>
    <p>PNU Item Purchase System</p>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading text-center">후드패딩</div>
                <div class="panel-body">
                    <img src="images/img2.gif" alt="img2" style="width: 100%;">
                </div>
                <div class="panel-footer">
                    <button class="btn btn-default btn-block" type="button">Buy</button>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading text-center">폴라니트</div>
                <div class="panel-body">
                    <img src="images/img0.gif" alt="img0" style="width: 100%;">
                </div>
                <div class="panel-footer">
                    <button class="btn btn-default btn-block" type="button">Buy</button>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">라쿤롱패딩</div>
                <div class="panel-body">
                    <img src="images/img1.gif" alt="img1" style="width: 100%;">
                </div>
                <div class="panel-footer">
                    <button class="btn btn-default btn-block" type="button">Buy</button>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading text-center">기모후드</div>
                <div class="panel-body">
                    <img src="images/img3.gif" alt="img3" style="width: 100%;">
                </div>
                <div class="panel-footer">
                    <button class="btn btn-default btn-block" type="button">Buy</button>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading text-center">라쿤야상점퍼</div>
                <div class="panel-body">
                    <img src="images/img4.jpg" alt="img4" style="width: 100%;">
                </div>
                <div class="panel-footer">
                    <button class="btn btn-default btn-block" type="button">Buy</button>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">롱티</div>
                <div class="panel-body">
                    <img src="images/img5.gif" alt="img5" style="width: 100%;">
                </div>
                <div class="panel-footer">
                    <button class="btn btn-default btn-block" type="button">Buy</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>