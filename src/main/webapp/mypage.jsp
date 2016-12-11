<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("signin") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Page</title>

    <jsp:include page="header.jsp"/>
</head>
<body>
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
                <li><a href="bestseller.jsp">Best Seller</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="#"><span class="glyphicon glyphicon-user"></span> My Page</a></li>
                <li><a href="signout.jsp"><span class="glyphicon glyphicon-log-out"></span> Sign out</a></li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
