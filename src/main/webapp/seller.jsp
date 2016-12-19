<%@ page import="kr.ac.pusan.pnuips.bean.SellBean" %>
<%@ page import="kr.ac.pusan.pnuips.model.item.Item" %>
<%@ page import="kr.ac.pusan.pnuips.model.order.Order" %>
<%@ page import="kr.ac.pusan.pnuips.model.sell.Seller" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getParameter("sellercode") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    int sellercode = Integer.parseInt(request.getParameter("sellercode").toString());
%>
<jsp:useBean id="sellerProcessor" class="kr.ac.pusan.pnuips.processor.SellerProcessor"/>
<jsp:useBean id="sellProcessor" class="kr.ac.pusan.pnuips.processor.SellProcessor"/>
<jsp:useBean id="orderProcessor" class="kr.ac.pusan.pnuips.processor.OrderProcessor"/>
<jsp:useBean id="itemProcessor" class="kr.ac.pusan.pnuips.processor.ItemProcessor"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seller detail</title>

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
    <%
        Seller seller = sellerProcessor.searchSellerBySellercode(sellercode);

        if (seller == null) {
    %>
    <div class="alert alert-danger">
        <strong>Error</strong>
        <p>Seller is not exist</p>
    </div>
    <%
    } else {
    %>
    <div class="row">
        <div class="col-md-6">
            <div class="jumbotron">
                <h1 class="text-center">
                    <%=seller.getSellername()%>
                </h1>
            </div>
        </div>

        <div class="col-md-6">
            <ul class="list-group">
                <li class="list-group-item">
                    <label for="sellercode">sellercode</label>
                    <h4 id="sellercode" class="text-center">
                        <%=seller.getSellercode()%>
                    </h4>
                </li>
                <li class="list-group-item">
                    <label for="total-count">total count</label>
                    <h4 id="total-count" class="text-center">
                        <%=sellerProcessor.getSalesCount(sellercode)%>
                    </h4>
                </li>
            </ul>
        </div>
    </div>
    <br>
    <%
        List<SellBean> sellBeanList = sellProcessor.searchSellBeanListBySeller(sellercode);

        if (sellBeanList.size() == 0) {
    %>
    <div class="alert alert-info">
        Seller does not sell anything.
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Item List</h1>
    <ul class="list-group">
        <li class="list-group-item list-header">
            <div class="row">
                <div class="col-md-7">
                    <h4 class="text-center">
                        Item name
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        Brand
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        Price
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
                <div class="col-md-7">
                    <h4 class="text-center">
                        <%=sellBean.getItem().getItemname()%>
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=sellBean.getItem().getBrand()%>
                    </h4>
                </div>
                <div class="col-md-2">
                    <h4 class="text-center">
                        <%=sellBean.getSell().getPrice()%>
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
        }
    %>
    <br>
    <%
        List<Order> orderList = orderProcessor.searchOrderListBySellercode(seller.getSellercode());

        if (orderList.size() == 0) {
    %>
    <div class="alert alert-info">
        Seller have not sell anything.
    </div>
    <%
    } else {
    %>
    <h1 class="text-center">Order List</h1>
    <ul class="list-group">
        <li class="list-group-item">
            <div class="row">
                <div class="col-md-4">
                    <h4 class="text-center">
                        Itemname
                    </h4>
                </div>
                <div class="col-md-3">
                    <h4 class="text-center">
                        Purchaser
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
                Item item = itemProcessor.searchItem(order.getItemcode());
        %>
        <li class="list-group-item">
            <div class="row">
                <div class="col-md-4">
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
                <div class="col-md-3">
                    <h4 class="text-center">
                        <%=order.getPurchaser()%>
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
</div>
</body>
</html>
