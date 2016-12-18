<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="kr.ac.pusan.pnuips.model.account.Account" %>
<%@ page import="kr.ac.pusan.pnuips.model.cart.Cart" %>
<%@ page import="kr.ac.pusan.pnuips.model.coupon.CouponType" %>
<%@ page import="kr.ac.pusan.pnuips.model.item.Item" %>
<%@ page import="kr.ac.pusan.pnuips.model.order.Order" %>
<%@ page import="kr.ac.pusan.pnuips.model.sell.Seller" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String email = StringUtils.EMPTY;
    if (request.getParameter("email") != null) {
        email = (String) request.getParameter("email");
    }
%>
<jsp:useBean id="orderProcessor" class="kr.ac.pusan.pnuips.processor.OrderProcessor"/>
<jsp:useBean id="itemProcessor" class="kr.ac.pusan.pnuips.processor.ItemProcessor"/>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<jsp:useBean id="sellerProcessor" class="kr.ac.pusan.pnuips.processor.SellerProcessor"/>
<jsp:useBean id="cartProcessor" class="kr.ac.pusan.pnuips.processor.CartProcessor"/>
<jsp:useBean id="couponProcessor" class="kr.ac.pusan.pnuips.processor.CouponProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Account</title>

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
                        <li class="active"><a href="manageAccount.jsp">Account</a></li>
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
            <form action="manageAccount.jsp" method="get">
                <div class="form-group">
                    <label for="email">email</label>
                    <input id="email" class="form-control" type="email" name="email" value="<%=email%>">
                </div>
                <button class="btn btn-default btn-block" type="submit">search</button>
            </form>
        </div>
    </div>
    <%
        try {
            Account account = new Account(email);
            if (StringUtils.isNotEmpty(email) && account.isExist()) {
                try {
                    account.load();
    %>
    <div class="row">
        <div class="col-md-6">
            <div class="jumbotron">
                <h1 class="text-center">
                    <%=account.getEmail()%>
                </h1>
            </div>
        </div>

        <div class="col-md-6">
            <ul class="list-group">
                <li class="list-group-item">
                    <label for="name">name</label>
                    <h4 id="name" class="text-center">
                        <%=account.getFirstname()%> <%=account.getLastname()%>
                    </h4>
                </li>
                <li class="list-group-item">
                    <label for="birthday">birthday</label>
                    <h4 id="birthday" class="text-center">
                        <%=account.getBirthday()%>
                    </h4>
                </li>
                <li class="list-group-item">
                    <label for="grade">grade</label>
                    <h4 id="grade" class="text-center">
                        <%=account.getGrade()%>
                    </h4>
                </li>
            </ul>
        </div>
    </div>
    <%
    } catch (SQLException e) {
        LoggerFactory.getLogger(page.getClass()).error("Failed to load Account.", e);
    %>
    <div class="alert alert-error">
        <strong>Error</strong>
        <p>Failed to find account data</p>
    </div>
    <%
        }
    %>
    <br>
    <%
        List<Order> orderList = orderProcessor.searchOrderList(email);

        if (orderList.size() == 0) {
    %>
    <div class="alert alert-info">
        There is no order.
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Purchase List</h1>
    <ul class="list-group">
        <li class="list-group-item">
            <div class="row">
                <div class="col-md-3">
                    <h4 class="text-center">
                        Itemname
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        Sellername
                    </h4>
                </div>
                <div class="col-md-1">
                    <h4 class="text-center">
                        Price
                    </h4>
                </div>
                <div class="col-md-1">
                    <h4 class="text-center">
                        Count
                    </h4>
                </div>
                <div class="col-md-1">
                    <h4 class="text-center">
                        Discount
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        Time
                    </h4>
                </div>
            </div>
        </li>
        <%
            for (Order order : orderList) {
                SellBean sellBean = sellProcessor.searchSellBean(order.getItemcode(), order.getSellercode());

        %>
        <li class="list-group-item"
            onclick="location.href='sell.jsp?itemcode=<%=order.getItemcode()%>&sellercode=<%=order.getSellercode()%>'"
            style="cursor: hand;">
            <div class="row">
                <div class="col-md-3">
                    <h4 class="text-center">
                        <%
                            if (sellBean == null) {
                        %>
                        Unknown
                        <%
                        } else {
                        %>
                        <%=sellBean.getItem().getItemname()%>
                        <%
                            }
                        %>
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        <%
                            if (sellBean == null) {
                        %>
                        Unknown
                        <%
                        } else {
                        %>
                        <%=sellBean.getSeller().getSellername()%>
                        <%
                            }
                        %>
                    </h4>
                </div>
                <div class="col-md-1">
                    <h4 class="text-center">
                        <%=sellBean.getSell().getPrice()%>
                    </h4>
                </div>
                <div class="col-md-1">
                    <h4 class="text-center">
                        <%=order.getCount()%>
                    </h4>
                </div>
                <div class="col-md-1">
                    <h4 class="text-center">
                        <%=order.getDiscount()%>%
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        <%=order.getTime()%>
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
    <br>
    <%
        List<Cart> cartList = cartProcessor.searchCartListByOwener(email);

        if (cartList.size() == 0) {
    %>
    <div class="alert alert-info">
        There is no cart.
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Cart List</h1>
    <ul class="list-group">
        <li class="list-group-item">
            <div class="row">
                <div class="col-md-6">
                    <h4 class="text-center">
                        Itemname
                    </h4>
                </div>
                <div class="col-md-4">
                    <h4 class="text-center">
                        Sellername
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        Count
                    </h4>
                </div>
            </div>
        </li>
        <%
            for (Cart cart : cartList) {
                Item item = itemProcessor.searchItem(cart.getItemcode());
                Seller seller = sellerProcessor.searchSeller(cart.getSellercode());

        %>
        <li class="list-group-item"
            onclick="location.href='sell.jsp?itemcode=<%=cart.getItemcode()%>&sellercode=<%=cart.getSellercode()%>'"
            style="cursor: hand;">
            <div class="row">
                <div class="col-md-6">
                    <h4 class="text-center">
                        <%
                            if (item == null) {
                        %>
                        Unknown
                        <%
                        } else {
                        %>
                        <%=item.getItemname()%>
                        <%
                            }
                        %>
                    </h4>
                </div>
                <div class="col-md-4">
                    <h4 class="text-center">
                        <%
                            if (seller == null) {
                        %>
                        Unknown
                        <%
                        } else {
                        %>
                        <%=seller.getSellername()%>
                        <%
                            }
                        %>
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=cart.getCount()%>
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
    <br>
    <%
        List<CouponType> couponTypeList = couponProcessor.searchCouponList(email);

        if (couponTypeList.size() == 0) {
    %>
    <div class="alert alert-info">
        There is no coupon.
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Coupon List</h1>
    <ul class="list-group">
        <li class="list-group-item">
            <div class="row">
                <div class="col-md-8">
                    <h4 class="text-center">
                        Name
                    </h4>
                </div>
                <div class="col-md-4">
                    <h4 class="text-center">
                        Discount
                    </h4>
                </div>
            </div>
        </li>
        <%
            for (CouponType couponType : couponTypeList) {
        %>
        <li class="list-group-item">
            <div class="row">
                <div class="col-md-8">
                    <h4 class="text-center">
                        <%=couponType.getName()%>
                    </h4>
                </div>
                <div class="col-md-4">
                    <h4 class="text-center">
                        <%=couponType.getDiscount()%>%
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
    } else {
        if (StringUtils.isNotEmpty(email)) {
    %>
    <div class="alert alert-info">
        <p>Account <strong><%=email%>
        </strong> is not exist</p>
    </div>
    <%
            }
        }
    } catch (SQLException e) {
        LoggerFactory.getLogger(page.getClass()).error("Failed to load Account.", e);
    %>
    <div class="alert alert-error">
        <strong>Error</strong>
        <p>Failed to find account data</p>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
