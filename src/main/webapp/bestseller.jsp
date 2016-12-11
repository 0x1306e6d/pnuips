<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bestseller</title>

    <jsp:include page="header.jsp"/>
</head>
<body>
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
                <li><a href="index.jsp">Home</a></li>
                <li class="active"><a href="#">Best Seller</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Sign in</a></li>
                <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">

</div>
</body>
</html>
