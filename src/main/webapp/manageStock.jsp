<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<jsp:useBean id="cartProcessor" class="kr.ac.pusan.pnuips.processor.CartProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Stock</title>

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
                <li class="dropdown active">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Manage<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="manageAccount.jsp">Account</a></li>
                        <li><a href="manageSeller.jsp">Seller</a></li>
                        <li class="active"><a href="#">Stock</a></li>
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
    <%
        List<SellBean> sellBeanList = sellProcessor.searchSellBeanWithSoldOutRisk();

        if (sellBeanList.size() == 0) {
    %>
    <div class="alert alert-info">
        There is no sell with sold-out risk.
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Item List</h1>
    <h4 class="text-center">카트에 담긴 수가 재고보다 더 많은 상품 목록입니다.</h4>
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
                <div class="col-md-2">
                    <h4 class="text-center">
                        Number of Stock
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        Number of Cart
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
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=sellBean.getSell().getNumberOfStock()%>
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=cartProcessor.getTotalCartCount(sellBean.getItem().getItemcode(), sellBean.getSeller().getSellercode())%>
                    </h4>
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
