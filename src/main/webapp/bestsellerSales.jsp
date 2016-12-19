<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int limit = 10;
    if (request.getParameter("limit") != null) {
        limit = Integer.parseInt(request.getParameter("limit").toString());
    }
%>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bestseller</title>

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
                <li class="dropdown active">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Best Seller<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="active"><a href="bestsellerSales.jsp">By numbef of sales</a></li>
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
                <li><a href="signout.jsp"><span class="glyphicon glyphicon-log-out"></span> Sign out</a></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <form action="bestsellerSales.jsp" method="get">
                <div class="form-group">
                    <label for="limit">limit</label>
                    <select id="limit" class="form-control" name="limit">
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="30">30</option>
                    </select>
                </div>
                <button class="btn btn-default btn-block" type="submit">search</button>
            </form>
        </div>
    </div>
    <%
        List<SellBean> sellBeanList = sellProcessor.searchBestSellBeanList(limit);

        if (sellBeanList.size() == 0) {
    %>
    <div class="alert alert-info">
        There is no item.
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Item List</h1>
    <ul class="list-group">
        <li class="list-group-item list-header">
            <div class="row">
                <div class="col-md-5">
                    <h4 class="text-center">
                        Item name
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        Seller name
                    </h4>
                </div>
                <div class="col-md-4 row">
                    <div class="col-md-5">
                        <h4 class="text-center">
                            Brand
                        </h4>
                    </div>
                    <div class="col-md-5">
                        <h4 class="text-center">
                            Price
                        </h4>
                    </div>
                    <div class="col-md-1">
                        <h4 class="text-center">
                            Sales
                        </h4>
                    </div>
                </div>
        </li>
        <%
            for (SellBean sellBean : sellBeanList) {
        %>
        <li class="list-group-item"
            onclick="location.href='sell.jsp?itemcode=<%=sellBean.getItem().getItemcode()%>&sellercode=<%=sellBean.getSeller().getSellercode()%>'"
            style="cursor: hand;">
            <div class="row">
                <div class="col-md-5">
                    <h4 class="text-center">
                        <%=sellBean.getItem().getItemname()%>
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        <%=sellBean.getSeller().getSellername()%>
                    </h4>
                </div>
                <div class="col-md-4 row">
                    <div class="col-md-5">
                        <h4 class="text-center">
                            <%=sellBean.getItem().getBrand()%>
                        </h4>
                    </div>
                    <div class="col-md-5">
                        <h4 class="text-center">
                            <%=sellBean.getSell().getPrice()%>
                        </h4>
                    </div>
                    <div class="col-md-1">
                        <h4 class="text-center">
                            <span class="badge"><%=sellBean.getSell().getNumberOfSales()%></span>
                        </h4>
                    </div>
                </div>
            </div>
        </li>
        <%
            }
        %>
    </ul>
    <%
        }
    %>
</div>
</body>
</html>
