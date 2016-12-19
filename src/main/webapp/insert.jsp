<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Setting</title>

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
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Best Seller<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="bestsellerSales.jsp">By numbef of sales</a></li>
                        <li><a href="bestsellerTime.jsp">Between time</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Manage<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="manageAccount.jsp">Account</a></li>
                        <li><a href="manageSeller.jsp">Seller</a></li>
                        <li><a href="manageStock.jsp">Stock</a></li>
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
                <%
                    if (session.getAttribute("signin") == null) {
                %>
                <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Sign in</a></li>
                <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
                <%
                } else {
                %>
                <li><a href="mypage.jsp"><span class="glyphicon glyphicon-user"></span> My Page</a></li>
                <li><a href="cart.jsp"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                <li><a href="signout.jsp"><span class="glyphicon glyphicon-log-out"></span> Sign out</a></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <form action="insertProcess.jsp">
        <div class="form-group">
            <label for="user-csv">User.csv</label>
            <textarea id="user-csv" class="form-control" name="userData" rows="10"></textarea>
        </div>
        <div class="form-group">
            <label for="item-csv">Item.csv</label>
            <textarea id="item-csv" class="form-control" name="itemData" rows="10"></textarea>
        </div>
        <button class="btn btn-default btn-block" type="submit">submit</button>
    </form>
</div>
</body>
</html>
