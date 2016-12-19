<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="kr.ac.pusan.pnuips.bean.SigninBean" %>
<%@ page import="kr.ac.pusan.pnuips.model.coupon.CouponType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("signin") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    if (request.getParameter("itemcode") == null || request.getParameter("sellercode") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    SigninBean signinBean = (SigninBean) session.getAttribute("signin");
    int itemcode = Integer.parseInt(request.getParameter("itemcode").toString());
    int sellercode = Integer.parseInt(request.getParameter("sellercode").toString());
%>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<jsp:useBean id="couponProcessor" class="kr.ac.pusan.pnuips.processor.CouponProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Purchase</title>

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
                <li><a href="mypage.jsp"><span class="glyphicon glyphicon-user"></span> My Page</a></li>
                <li><a href="cart.jsp"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                <li><a href="signout.jsp"><span class="glyphicon glyphicon-log-out"></span> Sign out</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <%
        SellBean sellBean = sellProcessor.searchSellBean(itemcode, sellercode);

        if (sellBean == null) {
    %>
    <div class="alert alert-danger">
        <strong>Error</strong>
        <p>Item is not exist</p>
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Purchase</h1>
    <br>
    <ul class="list-group">
        <li class="list-group-item">
            <h2 class="text-center">
                <%=sellBean.getItem().getItemname()%>
            </h2>
        </li>
        <li class="list-group-item">
            <label for="brand">brand</label>
            <h4 id="brand" class="text-center">
                <%=sellBean.getItem().getBrand()%>
            </h4>
        </li>
        <li class="list-group-item">
            <label for="price">price</label>
            <h4 id="price" class="text-center">
                <%=sellBean.getSell().getPrice()%>
            </h4>
        </li>
        <li class="list-group-item">
            <label for="sellerName">sellerName</label>
            <h4 id="sellerName" class="text-center">
                <%=sellBean.getSeller().getSellername()%>
            </h4>
        </li>
        <li class="list-group-item">
            <label for="numberOfStock">numberOfStock</label>
            <h4 id="numberOfStock" class="text-center">
                <%=sellBean.getSell().getNumberOfStock()%>
            </h4>
        </li>
        <li class="list-group-item">
            <label for="numberOfSales">numberOfSales</label>
            <h4 id="numberOfSales" class="text-center">
                <%=sellBean.getSell().getNumberOfSales()%>
            </h4>
        </li>
    </ul>
    <br>
    <form action="purchaseProcess.jsp" method="post">
        <div class="form-group">
            <label for="purchase-count">count</label>
            <input id="purchase-count" class="form-control" type="number" name="purchase-count"
                   value="1" min="1" max="<%=sellBean.getSell().getNumberOfStock()%>">
        </div>
        <div class="form-group">
            <label for="purchase-coupon">coupon</label>
            <ul id="purchase-coupon" class="list-group">
                <%
                    List<CouponType> couponTypeList = couponProcessor.searchCouponListByOwener(signinBean.getEmail());

                    for (CouponType couponType : couponTypeList) {
                %>
                <li class="list-group-item">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox">
                            <h5>
                                <%=couponType.getName()%>
                            </h5>
                            <%=couponType.getDiscount()%>%
                        </label>
                    </div>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
        <button type="submit" class="btn btn-default">purchase</button>
        <button type="button" class="btn btn-default">close</button>
    </form>
    <%
        }
    %>
</div>
</body>
</html>
