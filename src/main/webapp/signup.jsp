<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("signin") != null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign up</title>

    <jsp:include page="header.jsp"/>
</head>
<body style="background-color: #eeeeee">
<nav class="navbar navbar-inverse">
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
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Best Seller<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="bestsellerSales.jsp">By numbef of sales</a></li>
                        <li><a href="bestsellerTime.jsp">Between time</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form" action="search.jsp" method="get">
                        <div class="form-group">
                            <input class="form-control" type="text" name="itemname" placeholder="search..">
                        </div>
                        <button class="btn btn-default" type="submit">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </form>
                </li>
                <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Sign in</a></li>
                <li class="active"><a href="#"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="col-md-offset-4 col-md-4">
        <form method="post" action="signupProcess.jsp">
            <div class="form-group">
                <label for="email">email</label>
                <input id="email" class="form-control" type="email" name="email" placeholder="Enter email" required/>
            </div>
            <div class="form-group">
                <label for="password">password</label>
                <input id="password" class="form-control" type="password" name="password" placeholder="Enter password"
                       required/>
            </div>
            <div class="form-group">
                <label for="name">name</label>
                <div id="name" class="row">
                    <div class="col-md-7">
                        <input id="firstname" class="form-control" type="text" name="firstname"
                               placeholder="firstname" required/>
                    </div>
                    <div class="col-md-5">
                        <input id="lastname" class="form-control" type="text" name="lastname"
                               placeholder="lastname" required/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="birthday">birthday</label>
                <input id="birthday" class="form-control" type="date" name="birthday" required/>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Sign up</button>
        </form>
    </div>
</div>
</body>
</html>
